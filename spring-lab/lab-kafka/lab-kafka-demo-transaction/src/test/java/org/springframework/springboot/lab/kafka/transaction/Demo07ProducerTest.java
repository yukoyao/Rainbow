package org.springframework.springboot.lab.kafka.transaction;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.SendResult;
import org.springframework.springboot.lab.kafka.transaction.producer.Demo07Producer;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;

/**
 * @author K
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Demo07ProducerTest {

    @Autowired
    private Demo07Producer producer;

    @Test
    public void testSyncSend() throws ExecutionException, InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        SendResult result = producer.syncSend(id);
        log.info("[testSyncSend][发送编号: [{}] 发送结果: [{}]]", id, result);
    }

    @Test
    public void testSyncSendInTransaction() {
        int id = (int) ((System.currentTimeMillis()) / 1000);
        producer.syncSendInTransaction(id, new Runnable() {
            @Override
            public void run() {
                log.info("[run][我要开始睡觉了]");
                try {
                    Thread.sleep(10 * 1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("[run][我睡醒了]");
            }
        });
    }
}
