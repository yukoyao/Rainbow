package top.yuko.redisson.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.redisson.config.TransportMode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Redisson 属性类
 *
 * @author: K
 * @date: 2022/02/25 15:15
 */
@Data
@Component
@ConfigurationProperties(prefix = "redisson")
public class RedissonProperties {

  /**
   * 线程池数量 默认值为当前核数量 * 2
   */
  private int threads;

  /**
   * Netty 线程池数量 默认值为当前核数量 * 2
   */
  private int nettyThreads;

  /**
   * 传输模式
   */
  private TransportMode transportMode;

  /**
   * 单机服务配置
   */
  private SingleServerConfig singleServerConfig;

  @Data
  @NoArgsConstructor
  public static class SingleServerConfig {

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 最小空闲连接数
     */
    private int connectionMinimumIdleSize;

    /**
     * 连接池大小
     */
    private int connectionPoolSize;

    /**
     * 连接空闲超时，单位：毫秒
     */
    private int idleConnectionTimeout;

    /**
     * 命令等待超时，单位：毫秒
     */
    private int timeout;

    /**
     * 如果尝试在此限制之内发送成功，则开始启用 timeout 计时
     */
    private int retryAttempts;

    /**
     * 命令重试
     */
    private int retryInterval;

    /**
     * 发布和订阅连接的最小空闲连接数
     */
    private int subscriptionConnectionMinimumIdleSize;

    /**
     * 发布和订阅池连接池大小
     */
    private int subscriptionConnectionPoolSize;

    /**
     * 单个连接最大订阅数量
     */
    private int subscriptionsPerConnection;

    /**
     * DNS监测时间间隔，单位：毫秒
     */
    private int dnsMonitoringInterval;

  }
}
