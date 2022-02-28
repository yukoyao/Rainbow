package top.yuko.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.TimeUnit;

/**
 * @author: K
 * @date: 2022/02/26 15:03
 */
@RestController
@Slf4j
public class RedLockController {


  public static final String CACHE_KEY_REDLOCK = "ZZYY_REDLOCK";

  @Autowired
  private RedissonClient redissonClient1;

  @Autowired
  private RedissonClient redissonClient2;

  @Autowired
  private RedissonClient redissonClient3;

  @GetMapping("redlock")
  public void getLock() {
    RLock lock1 = redissonClient1.getLock(CACHE_KEY_REDLOCK);
    RLock lock2 = redissonClient2.getLock(CACHE_KEY_REDLOCK);
    RLock lock3 = redissonClient3.getLock(CACHE_KEY_REDLOCK);
    RedissonRedLock redLock = new RedissonRedLock(lock1, lock2, lock3);
    boolean isLock;

    try {
      // waitTime 锁的等待时间
      // leaseTime redis key 的过期时间
      isLock = redLock.tryLock(5, 300, TimeUnit.SECONDS);

      if (isLock) {
        try {
          TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

    } catch (Exception e) {
      log.error("redlock exception", e);
    } finally {
      redLock.unlock();
      System.out.println(Thread.currentThread().getName() + "\tredLock.unlock()");
    }
  }


  @GetMapping("/watchDog")
  public void testWatchDog() {
    RLock lock = redissonClient1.getLock("watch_dog");
    RedissonRedLock redLock = new RedissonRedLock(lock);
    try {
      redLock.lock();
      // 休眠 20 s 模拟耗时的业务操作
      try {
        TimeUnit.SECONDS.sleep(20);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } catch (Exception e) {
      log.error("redlock exception", e);
    } finally {
      redLock.unlock();
      System.out.println(Thread.currentThread().getName() + "\tredLock.unlock()");
    }
  }
}
