package org.springframework.springboot.lab.kafka.ack.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.springboot.lab.kafka.ack.message.Demo08Message;
import org.springframework.stereotype.Component;

/**
 * @author K
 */
@Slf4j
@Component
public class Demo08Consumer {

    @KafkaListener(topics = Demo08Message.TOPIC, groupId = "demo08-consumer-group-" + Demo08Message.TOPIC)
    public void onMessage(Demo08Message message, Acknowledgment acknowledgment) {
        log.info("[onMessage][线程编号: {} 消息内容: {}]", Thread.currentThread().getId(), message);
        acknowledgment.acknowledge();
       /* if (message.getId() % 2 == 1) {
            acknowledgment.acknowledge();
        }*/
    }
}
