package top.yuko.distributed.config;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis 配置类
 *
 * @author: K
 * @date: 2022/02/22 10:21
 */
@Configuration
public class RedisConfig {

  @Bean
  public RedisTemplate redisTemplate(LettuceConnectionFactory connectionFactory) {
    RedisTemplate redisTemplate = new RedisTemplate<>();
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    redisTemplate.setConnectionFactory(connectionFactory);
    return redisTemplate;
  }

  @Bean
  public Redisson redisson() {
    Config config = new Config();
    config.useSingleServer().setAddress("redis://192.168.110.100:6379").setDatabase(0);
    return (Redisson) Redisson.create(config);
  }

}
