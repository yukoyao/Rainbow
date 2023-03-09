package org.springframework.springboot.lab.kafka.broadcast.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.springboot.lab.kafka.broadcast.message.Demo05Message;
import org.springframework.stereotype.Component;

/**
 * @author K
 */
@Slf4j
@Component
public class Demo05Consumer {

    @KafkaListener(topics = Demo05Message.TOPIC, groupId = "demo05-consumer-group-" + Demo05Message.TOPIC
            + "-" + "#{T(java.util.UUID).randomUUID()}")
    public void onMessage(Demo05Message message) {
        log.info("[onMessage][线程编号: {} 消息内容: {}]", Thread.currentThread().getId(), message);
    }
}
