package org.springframework.springboot.lab.redisson.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author K
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "redisson")
public class RedissonConfig {

    private String address = "redis://122.10.110.197:6388";
    private int database = 1;

    private String password = "";
    private int connectionMinimumIdleSize=32;
    private int connectionPoolSize=64;
    private int connectTimeout=10000;
    private int pingConnectionInterval=500;
    private int pingTimeout=10000;
    private int timeout=10000;

    private short isCluster=0; //是否开启集群 0 关闭 1开启
}
