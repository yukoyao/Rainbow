package org.springframework.springboot.lab.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author K
 */
public class ProducerMain {

    private static Producer<String, String> createProducer() {
        // 设置 Producer 的属性
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "192.168.110.100:9092"); // 设置 Broker 的地址
        properties.put("acks", "1"); // 0-不应答。1-leader 应答。all-所有 leader 和 follower 应答。
        properties.put("retires", 3); // 发送失败时，重试发送的次数
        properties.put("key.serializer", StringSerializer.class.getName()); // 消息的 Key 的序列化方式
        properties.put("value.serializer", StringSerializer.class.getName()); // 消息的 Value 的序列化方式

        // 创建 KafkaProducer 对象
        // 因为消息的 Key 和 Value 都使用 String 类型, 所以创建的 Producer 是 <String, String> 的泛型
        return new KafkaProducer<>(properties);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建 KafkaProducer 对象
        Producer<String, String> producer = createProducer();

        // 创建消息, 传入三个参数, 分别是 Topic、消息的 Key、消息的 Value
        ProducerRecord<String, String> message = new ProducerRecord<>("TestTopic", "key", "test-value");

        // 同步发送消息
        Future<RecordMetadata> sendResultFuture = producer.send(message);
        RecordMetadata result = sendResultFuture.get();
        System.out.println("message send to " + result.topic() + ", partition" + result.partition() + ", offset " + result.offset());
    }
}
