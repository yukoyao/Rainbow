package org.springframework.springboot.lab.kafka.batch.consume.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.springboot.lab.kafka.batch.consume.message.Demo02Message;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author K
 */
@Slf4j
@Component
public class Demo02Consumer {

    @KafkaListener(topics = Demo02Message.TOPIC, groupId = "demo02-consumer-group-" + Demo02Message.TOPIC)
    public void onMessage(List<Demo02Message> messages) {
        log.info("[onMessage][线程编号: {} 消息数量: {}]", Thread.currentThread().getId(), messages.size());
    }
}
