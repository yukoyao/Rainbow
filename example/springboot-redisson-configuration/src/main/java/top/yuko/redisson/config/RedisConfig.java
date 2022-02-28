package top.yuko.redisson.config;

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
import top.yuko.redisson.properties.RedissonProperties;
import top.yuko.redisson.properties.RedissonProperties.SingleServerConfig;
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

  private static final String REDIS_PROTOCOL_PREFIX = "redis://";

  private static final String REDISS_PROTOCOL_PREFIX = "rediss://";

  @Autowired
  private RedisProperties redisProperties;

  @Autowired
  private RedissonProperties redissonProperties;

  @Bean(destroyMethod = "shutdown")
  @ConditionalOnMissingBean(RedissonClient.class)
  public RedissonClient redisson() throws IOException {
    String prefix = REDIS_PROTOCOL_PREFIX;
    if (redisProperties.isSsl()) {
      prefix = REDISS_PROTOCOL_PREFIX;
    }
    Config config = new Config();
    config.setThreads(redissonProperties.getThreads())
        .setNettyThreads(redissonProperties.getNettyThreads())
        .setCodec(JsonJacksonCodec.INSTANCE)
        .setTransportMode(redissonProperties.getTransportMode());

    SingleServerConfig singleServerConfig = redissonProperties.getSingleServerConfig();

    // 使用单机模式
    config.useSingleServer()
        .setAddress(prefix + redisProperties.getHost() + ":" + redisProperties.getPort())
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
