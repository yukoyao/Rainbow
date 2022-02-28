package top.yuko.example.config;

import cn.hutool.core.util.StrUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.yuko.example.properties.RedissonProperties;
import top.yuko.example.properties.RedissonProperties.SingleServerConfig;

import java.io.IOException;

/**
 * redis 配置
 *
 * @author: K
 * @date: 2022/02/25 16:44
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {


  @Autowired
  private RedisProperties redisProperties;

  @Autowired
  private RedissonProperties redissonProperties;

  @Bean(name = "redissonClient1", destroyMethod = "shutdown")
  @ConditionalOnMissingBean(RedissonClient.class)
  public RedissonClient redisson1() throws IOException {
    Config config = new Config();
    config.setThreads(redissonProperties.getThreads())
        .setNettyThreads(redissonProperties.getNettyThreads())
        .setCodec(JsonJacksonCodec.INSTANCE)
        .setTransportMode(redissonProperties.getTransportMode());

    SingleServerConfig singleServerConfig = redissonProperties.getSingleServerConfig();

    // 使用单机模式
    config.useSingleServer()
        .setAddress(redissonProperties.getMasterAddresses().get(0))
        .setConnectTimeout(((Long) redisProperties.getTimeout().toMillis()).intValue())
        .setDatabase(redisProperties.getDatabase())
        .setPassword(StrUtil.isNotBlank(redisProperties.getPassword()) ? redisProperties.getPassword() : null)
        .setTimeout(singleServerConfig.getTimeout())
        .setRetryAttempts(singleServerConfig.getRetryAttempts())
        .setRetryInterval(singleServerConfig.getRetryInterval())
        .setSubscriptionsPerConnection(singleServerConfig.getSubscriptionsPerConnection())
        .setClientName(singleServerConfig.getClientName())
        .setIdleConnectionTimeout(singleServerConfig.getIdleConnectionTimeout())
        .setSubscriptionConnectionMinimumIdleSize(singleServerConfig.getSubscriptionConnectionMinimumIdleSize())
        .setSubscriptionConnectionPoolSize(singleServerConfig.getConnectionPoolSize())
        .setConnectionMinimumIdleSize(singleServerConfig.getConnectionMinimumIdleSize())
        .setConnectionPoolSize(singleServerConfig.getConnectionPoolSize())
        .setDnsMonitoringInterval(singleServerConfig.getDnsMonitoringInterval());

    return Redisson.create(config);
  }

  @Bean(name = "redissonClient2", destroyMethod = "shutdown")
  @ConditionalOnMissingBean(RedissonClient.class)
  public RedissonClient redisson2() throws IOException {
    Config config = new Config();
    config.setThreads(redissonProperties.getThreads())
        .setNettyThreads(redissonProperties.getNettyThreads())
        .setCodec(JsonJacksonCodec.INSTANCE)
        .setTransportMode(redissonProperties.getTransportMode());

    SingleServerConfig singleServerConfig = redissonProperties.getSingleServerConfig();

    // 使用单机模式
    config.useSingleServer()
        .setAddress(redissonProperties.getMasterAddresses().get(1))
        .setConnectTimeout(((Long) redisProperties.getTimeout().toMillis()).intValue())
        .setDatabase(redisProperties.getDatabase())
        .setPassword(StrUtil.isNotBlank(redisProperties.getPassword()) ? redisProperties.getPassword() : null)
        .setTimeout(singleServerConfig.getTimeout())
        .setRetryAttempts(singleServerConfig.getRetryAttempts())
        .setRetryInterval(singleServerConfig.getRetryInterval())
        .setSubscriptionsPerConnection(singleServerConfig.getSubscriptionsPerConnection())
        .setClientName(singleServerConfig.getClientName())
        .setIdleConnectionTimeout(singleServerConfig.getIdleConnectionTimeout())
        .setSubscriptionConnectionMinimumIdleSize(singleServerConfig.getSubscriptionConnectionMinimumIdleSize())
        .setSubscriptionConnectionPoolSize(singleServerConfig.getConnectionPoolSize())
        .setConnectionMinimumIdleSize(singleServerConfig.getConnectionMinimumIdleSize())
        .setConnectionPoolSize(singleServerConfig.getConnectionPoolSize())
        .setDnsMonitoringInterval(singleServerConfig.getDnsMonitoringInterval());

    return Redisson.create(config);
  }

  @Bean(name = "redissonClient3", destroyMethod = "shutdown")
  @ConditionalOnMissingBean(RedissonClient.class)
  public RedissonClient redisson3() throws IOException {
    Config config = new Config();
    config.setThreads(redissonProperties.getThreads())
        .setNettyThreads(redissonProperties.getNettyThreads())
        .setCodec(JsonJacksonCodec.INSTANCE)
        .setTransportMode(redissonProperties.getTransportMode());

    SingleServerConfig singleServerConfig = redissonProperties.getSingleServerConfig();

    // 使用单机模式
    config.useSingleServer()
        .setAddress(redissonProperties.getMasterAddresses().get(2))
        .setConnectTimeout(((Long) redisProperties.getTimeout().toMillis()).intValue())
        .setDatabase(redisProperties.getDatabase())
        .setPassword(StrUtil.isNotBlank(redisProperties.getPassword()) ? redisProperties.getPassword() : null)
        .setTimeout(singleServerConfig.getTimeout())
        .setRetryAttempts(singleServerConfig.getRetryAttempts())
        .setRetryInterval(singleServerConfig.getRetryInterval())
        .setSubscriptionsPerConnection(singleServerConfig.getSubscriptionsPerConnection())
        .setClientName(singleServerConfig.getClientName())
        .setIdleConnectionTimeout(singleServerConfig.getIdleConnectionTimeout())
        .setSubscriptionConnectionMinimumIdleSize(singleServerConfig.getSubscriptionConnectionMinimumIdleSize())
        .setSubscriptionConnectionPoolSize(singleServerConfig.getConnectionPoolSize())
        .setConnectionMinimumIdleSize(singleServerConfig.getConnectionMinimumIdleSize())
        .setConnectionPoolSize(singleServerConfig.getConnectionPoolSize())
        .setDnsMonitoringInterval(singleServerConfig.getDnsMonitoringInterval());

    return Redisson.create(config);
  }
}
