package org.springframework.springboot.lab.kafkademo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.SendResult;
import org.springframework.springboot.lab.kafkademo.producer.Demo04Producer;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;

/**
 * @author K
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Demo04ProducerTest {

    @Autowired
    private Demo04Producer producer;

    @Test
    public void testSyncSend() throws ExecutionException, InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        SendResult result = producer.syncSend(id);
        log.info("[testSyncSend][发送编号: [{}] 发送结果: [{}]]", id, result);
    }

    @Test
    public void testSyncSendX() throws ExecutionException, InterruptedException {
        for (int i = 0; i < 100; i++) {
            int id = (int) (System.currentTimeMillis() / 1000);
            SendResult result = producer.syncSend(id);
            log.info("[testSyncSend][发送编号: [{}] 发送结果: [{}]]", id, result);
            Thread.sleep(10 * 1000L);
        }
    }
}
