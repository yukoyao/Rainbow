package org.springframework.springboot.lab.kafka.batch.consume;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.springboot.lab.kafka.batch.message.Demo02Message;
import org.springframework.stereotype.Component;

/**
 * @author K
 */
@Slf4j
@Component
public class Demo04Consumer {

    @KafkaListener(topics = Demo02Message.TOPIC, groupId = "demo02-consumer-group-" + Demo02Message.TOPIC)
    public void onMessage(Demo02Message message) {
        log.info("[onMessage][线程编号: {} 消息内容: {}]", Thread.currentThread().getId(), message);
    }
}
