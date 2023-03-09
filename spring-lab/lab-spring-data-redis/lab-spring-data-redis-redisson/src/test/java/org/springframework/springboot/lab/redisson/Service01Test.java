package org.springframework.springboot.lab.redisson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.springboot.lab.redisson.service.UserService01;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author K
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Service01Test {

    @Autowired
    private UserService01 userService01;
    @Autowired
    private RedissonClient redissonClient;

    private AtomicLong count = new AtomicLong(0);

    private CountDownLatch countDownLatch = new CountDownLatch(200);


    @Test
    public void testExecuteOnLock() throws InterruptedException {
        for (int i = 0; i < 200; i++) {
            new Thread(() -> {
                try {
                    userService01.executeOnLock("test-lock", 3,250, obj -> {
                        long curr = count.incrementAndGet();
                        System.out.println("执行回调" + curr);
                        Thread.sleep(200);
                        return true;
                    });
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                countDownLatch.countDown();
            }).start();
        }

        countDownLatch.await();
    }
}
