package org.springframework.springboot.lab.redisson.service;

import org.redisson.api.RLock;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.springboot.lab.redisson.util.LockCallBack;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author K
 */
@Service
public class UserService01 {


    @Resource
    private RedissonClient redissonClient;

    public RLock getLock(String key) {
        return redissonClient.getLock(key);
    }

    /**
     * 在redis分布式中执行回调函数
     *
     * @param lockKey  分布式锁Key
     * @param time     获取锁时间 超时失败  秒
     * @param timeout  默认超时释放锁时间 秒
     * @param callBack 回调函数
     * @return
     * @throws InterruptedException
     */
    public Object executeOnLock(String lockKey, long time, long timeout, LockCallBack callBack) throws InterruptedException {
        RLock lock = getLock(lockKey);
        try {
            if (lock.tryLock(time, timeout, TimeUnit.MICROSECONDS)) {
                /**
                 * 获取锁成功 执行回调
                 */
                return callBack.execute(true);
            } else {
                System.out.println("lock fail");
            }
        } catch (InterruptedException e) {
            throw e;
        } finally {
            lock.forceUnlock();
        }
        return null;
    }


}
