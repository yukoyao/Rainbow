package org.springframework.springboot.lab.kafka.ack.producer;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.SendResult;
import org.springframework.springboot.lab.kafka.ack.Application;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;

/**
 * @author K
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Demo08ProducerTest {

    @Autowired
    private Demo08Producer producer;

    @Test
    public void testSyncSend() throws ExecutionException, InterruptedException {
        for (int i = 1; i <= 2; i++) {
            SendResult result = producer.syncSend(i);
            log.info("[testSyncSend][发送编号: {} 发送结果: [{}]]", i, result);
        }
    }
}
