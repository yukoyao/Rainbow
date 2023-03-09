package org.springframework.springboot.lab.kafka.transaction.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.springboot.lab.kafka.transaction.message.Demo07Message;
import org.springframework.stereotype.Component;

/**
 * @author K
 */
@Slf4j
@Component
public class Demo07Consumer {

    @KafkaListener(topics = Demo07Message.TOPIC, groupId = "demo07-consumer-group-" + Demo07Message.TOPIC)
    public void onMessage(Demo07Message message) {
        log.info("[onMessage][线程编号: {} 消息内容: {}]", Thread.currentThread().getId(), message);
    }
}
