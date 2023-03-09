package org.springframework.springboot.lab.jedis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.springboot.lab.jedis.listener.TestChannelTopicMessageListener;
import org.springframework.springboot.lab.jedis.listener.TestPatternTopicMessageListener;

/**
 * @author K
 */
@Configuration
public class RedisConfiguration {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        // 创建 RedisTemplate 对象
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        // 设置开启事务支持
        template.setEnableTransactionSupport(true);

        // 设置 RedisConnection 工厂
        template.setConnectionFactory(factory);

        // 使用 String 序列化方式, 序列化 KEY
        template.setKeySerializer(RedisSerializer.string());

        // 使用 JSON 序列化方式, 序列化 VALUE
        template.setValueSerializer(RedisSerializer.json());
        return template;
    }


    @Bean // PUB/SUB 使用的 Bean
    public RedisMessageListenerContainer listenerContainer(RedisConnectionFactory factory) {
        // 创建 RedisMessageListenerContainer 对象
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();

        // 设置 RedisConnection 工厂
        container.setConnectionFactory(factory);

        // 添加监听器
        container.addMessageListener(new TestChannelTopicMessageListener(), new ChannelTopic("TEST"));
        container.addMessageListener(new TestPatternTopicMessageListener(), new PatternTopic("TEST"));
        return container;
    }
}
