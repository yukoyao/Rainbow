package org.springframework.springboot.lab.kafka.concurrency.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.springboot.lab.kafka.concurrency.message.Demo06Message;
import org.springframework.stereotype.Component;

/**
 * @author K
 */
@Slf4j
@Component
public class Demo06Consumer {

    @KafkaListener(topics = Demo06Message.TOPIC,
            groupId = "demo06-consumer-group-" + Demo06Message.TOPIC,
            concurrency = "2")
    public void onMessage(Demo06Message message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }
}
