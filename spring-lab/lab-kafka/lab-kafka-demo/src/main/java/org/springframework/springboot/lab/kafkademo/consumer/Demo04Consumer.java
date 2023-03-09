package org.springframework.springboot.lab.kafkademo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.springboot.lab.kafkademo.message.Demo01Message;
import org.springframework.springboot.lab.kafkademo.message.Demo04Message;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author K
 */
@Slf4j
@Component
public class Demo04Consumer {

    private AtomicInteger count = new AtomicInteger(0);

    @KafkaListener(topics = Demo04Message.TOPIC, groupId = "demo04-consumer-group-" + Demo01Message.TOPIC)
    public void omMessage(Demo04Message message) {
        log.info("[onMessage][线程编号:{} 消息内容:{}]", Thread.currentThread().getId(), message);
        // 模拟消费失败
        throw new RuntimeException("我是故意抛出的一个异常");
    }
}
