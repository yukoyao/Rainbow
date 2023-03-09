package org.springframework.springboot.lab.kafkademo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.springboot.lab.kafkademo.message.Demo01Message;
import org.springframework.stereotype.Component;

/**
 * @author K
 */
@Slf4j
@Component
public class Demo01AConsumer {

    @KafkaListener(topics = Demo01Message.TOPIC, groupId = "demo01-A-consumer-group-" + Demo01Message.TOPIC)
    public void omMessage(ConsumerRecord<Integer, String> record) {
        log.info("[onMessage][线程编号:{} 消息内容:{}]", Thread.currentThread().getId(), record);
    }
}
