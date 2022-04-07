使用多主模式解决锁丢失问题
===

### 为什么 Redis 分布式锁基于故障转移还不够？
实现 Redis 分布式锁的最简单的方法就是在 Redis 中创建一个 key， 这个 key 有一个失效时间(TTL)，以保证锁最终会被释放掉。
当客户端释放资源的时候，会删掉这个 key.这里存在严重的单点失败问题，如果 Redis 挂掉了怎么办？可能会说通过增加 slave 节点来
解决这个问题。但这通常是行不通的，因为 Redis 的主从复制是异步的。

在这个场景中存在明显的竞态：
1. 客户端 A 从 master 中获取到锁。
2. 在 master 将锁同步到 slave 之前，master 宕掉了。
3. slave 节点晋升为 master。
4. 客户端 B 取得了已经被客户端A 获得过的锁。


### 什么是 RedLock 算法

在 Redis 的分布式环境中，假设有 N 个 Redis master，这些节点相互独立，不存在主从复制或者其他集群协调机制。为了取到锁，客户应该执行以下操作：
1. 获取当前时间，以毫秒为单位。
2. 依次尝试从5个实例，使用相同的 key 和随机值（例如 UUID）获取锁。当向Redis 请求获取锁时，客户端应该设置一个超时时间，这个超时时间应该小于锁的失效时间。例如你的锁自动失效时间为 10 秒，则超时时间应该在 5-50 毫秒之间。这样可以防止客户端在试图与一个宕机的 Redis 节点对话时长时间处于阻塞状态。如果一个实例不可用，客户端应该尽快尝试去另外一个 Redis 实例请求获取锁。
3. 客户端使用当前时间减去开始获取时间(步骤1记录的时间)就得到获取锁使用的时间。当且仅当从大多数(5台实例中起码有三台)的 Redis 节点都取到锁，并且使用的时间小于锁的超时时间，锁才算成功，
4. 如果取到了锁，其真正有效时间等于初始有效时间减去获取锁所使用的时间（步骤 3 计算的结果）。
5. 如果由于某些原因未能获得锁（无法在至少 N/2 + 1 个 Redis 实例获取锁、或获取锁的时间超过了有效时间），客户端应该在所有的 Redis 实例上进行解锁（即便某些Redis实例根本就没有加锁成功，防止某些节点获取到锁但是客户端没有得到响应而导致接下来的一段时间不能被重新获取锁）。

### 计算公式

N = 2X + 1 (N 为最终部署机器数，X 是容错机器数)。

### Redisson 加锁逻辑

![Redisson 加锁逻辑](http://images.yuko.top/images/2022/02/27/816762-20210425105548419-1714180337.jpg)

线程获取锁：
* 获取成功则执行 lua 脚本，保存数据到 redis 数据库中，
* 获取失败则一直 while 循环尝试获取锁(可自定义等待等待时间，超时后返回失败)，获取成功后，执行 lua 脚本，保存数据到 redis 数据库。

### Watch Dog 的自动延期机制

如果业务的执行时间大于锁的超时时间，就会导致问题。Redisson 为了解决这个问题，给出了 watch dog 自动延期机制。Redisson 提供了一个监控锁的看门狗，
它的作用是在 Redisson 实例被关闭前，不断延长锁的有效期，也就是说，如果一个拿到锁的线程一直没有完成逻辑，那么看门狗会帮助线程不断延长锁的超时时间，
锁不会因为超时而被释放，

默认情况下，看门狗的续期时间是 30s, 也可以通过修改 Config.lockWatchdogTimeout 来另行指定。另外 Redisson 还提供了可以指定 leaseTime 参数
的加锁方法来制定加锁的时间。超过这个时间就会自动释放锁，不会延长锁的有效期。

### Watch Dog 源码解读

> 需要注意的是：watchdog 只有在为显式指定加锁时间时才会生效，lockWatchdogTimeout 设定的时间不能太小。


```
private <T> RFuture<Long> tryAcquireAsync(long waitTime, long leaseTime, TimeUnit unit, long threadId) {
    RFuture<Long> ttlRemainingFuture;
    // 如果指定了加锁时间，会直接去加锁
    if (leaseTime != -1) {
        ttlRemainingFuture = tryLockInnerAsync(waitTime, leaseTime, unit, threadId, RedisCommands.EVAL_LONG);
    } else {
        // 没有指定加锁时间 会先进行加锁 并且默认时间就是 LockWatchdogTimeout 的时间
        ttlRemainingFuture = tryLockInnerAsync(waitTime, internalLockLeaseTime, TimeUnit.MILLISECONDS, threadId, RedisCommands.EVAL_LONG);
    }
    
    // 在 future 内容执行完成后执行的
    ttlRemainingFuture.onComplete((ttlRemaining, e) -> {
        if (e != null) {
            return;
        }
        
        // lock acquired
        if (ttlRemaining == null) {
            // leaseTime 不为 -1 时，不会自动续期
            if (leaseTime != -1) {
                internalLockLeaseTime = unit.toMillis(leaseTime);
            } else {
                // 定期执行
                scheduleExpirationRenewal(threadId);
            }
        }
    });
    return ttlRemainingFuture;
} }
```

scheduleExpirationRenewal 中会调用 renewExpiration.这里可以看到是启动了一个 timeout 定时，去执行延期动作。

```
private void renewExpiration() {
    ExpirationEntry ee = EXPIRATION_RENEWAL_MAP.get(getEntryName());
    if (ee == null) {
        return;
    }
    
    Timeout task = commandExecutor.getConnectionManager().newTimeout(new TimeTask(){
        @Override
        public void run(Timeout timeout) throws Exception {
            ExpirationEntry ent = EXPIRATION_RENEWAL_MAP.get(getEntryName());
            if (ent == null) {
                return;
            }
            
            Long threadId = ent.getFirstThreadId();
            future.onComplete((res, e) -> {
                if (e != null) {
                    log.error("Can't update lock " + getRawName() + " expiration", e);
                    EXPIRATION_RENEWAL_MAP.remove(getEntryName());
                    return;
                }
                
                if (res) {
                    //如果 没有报错，就再次定时延期
                    // reschedule itself
                    renewExpiration();
                } else {
                    cancelExpirationRenewal(null);
                }
            });
        }
    }, internalLockLeaseTime / 3, TimeUnit.MILLISECONDS);
    
    ee.setTimeout(task);
}
```

最终，scheduleExpirationRenewal 会调用 renewExpirationAsync，执行下面这段 lua 脚本，主要是判断 redis 锁是否存在，存在则进行 pexpire 延时。

```
protected RFuture<Boolean> renewExpirationAsync(long threadId) {
    return evalWriteAsync(getRawName(), LongCodec.INSTANCE, RedisCommands.EVAL_BOOLEAN, 
            "if (redis.call('hexists', KEYS[1], ARGV[2]) ==1) then " + 
                "redis.call('pexpire', KEYS[1], ARGV[1]); " +
                "return 1; " +
                "end; " + 
                "return 0;", Collections.singletonList(getRawName()), internalLockLeaseTime, getLockName(threadId));
}
```

