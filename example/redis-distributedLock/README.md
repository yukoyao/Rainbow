
### 分布式锁解决商品抢购问题

#### 一个成熟的分布式锁需要具备的条件

* 独占性。任何时刻只能有且仅有一个线程持有。
* 高可用。在 Redis 集群环境下，不能因为某一个节点挂了而出现获取锁和释放锁失败的情况。
* 防死锁。必须有超时控制机制或撤销操作，有个兜底终止跳出方案。
* 不乱抢。不能私下 unlock 别人的锁，必须只能自己加锁自己解锁。
* 重入性。同一个节点的同一个线程如果获得锁之后，它也可以再次获取这个锁。

#### redis 实现分布式锁命令

```
// 这种方式最不常用 因为与设置超时时间间不是原子性操作
setnex key value

// 常用
set key value [EX seconds] [PX milliseconds] [NX|XX] 
```

#### 使用场景：

多个服务间保证同一时间段内只能有一个请求(防止关键业务出现并发攻击)

#### 商品抢购用上分布式锁的演化过程

> 1. 不上锁会造成超卖 (选择 synchronized 和 ReentrantLock)

> 2. 不同 JVM 的锁也还是各不相同，还是会造成超卖

在单机环境中，可以使用 synchronized 或 Lock 来解决。但是在分布式系统中，因为竞争的线程可能不在同一个节点上(同一个 jvm 中)，所以需要一个让所有
进程都能访问的锁来实现(比如 redis 或 zookeeper 来构建)，每次执行先获取锁，未获得锁则阻塞当前线程。

> 3. 使用 setnx 判断是否存在来当锁，可能在出现程序异常或者宕机的情况不能释放锁。

通过给锁设置超时时间来解决。

> 4. 但是设置锁和锁的超时时间之间并不是原子性 所以也会出错

```
// 通过一行代码设置锁和超时时间，使其具备原子性
boolean locked = stringRedisTemplate.opsForValue().setIfAbsent(key, value, 10L, TimeUnit.SECONDS);
```

> 5. 但是没有在 finally 块中判断是否为自己的锁。有可能程序因为网络波动导致执行时间变长 而删除了别人的锁.

> 6. 通过 lua 脚本判断是否为自己上的锁才进行删除

```
try {

} finally {            
    Jedis jedis = RedisUtils.getJedis();           
    String script = "if redis.call('get', KEYS[1]) == ARGV[1] " +                    
        "then " +                    
            "return redis.call('del', KEYS[1]) " +                    
        "else " +                    
            "   return 0 " +                    
        "end";            
    try {                
        Object result = jedis.eval(script, Collections.singletonList(REDIS_LOCK_KEY), Collections.singletonList(value));                
        if ("1".equals(result.toString())) {                    
            System.out.println("------del REDIS_LOCK_KEY success");                
        } else {                    
            System.out.println("------del REDIS_LOCK_KEY error");                
        }            
    } finally {                
        if(null != jedis) {                    
            jedis.close();                
        }            
    }        
}
```

> 7. 使用 lua 脚本保证了是否自己的锁和删除所的操作的原子性，这时候会出现一个隐藏问题 就是权衡锁的超时时间和业务的执行时间(分布式锁续约问题)。
> 使用 redisson 解决。

