package org.springframework.springboot.lab.redisson.config;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author K
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(RedissonConfig.class)
@AutoConfigureAfter(RedissonConfig.class)
public class RedissonConfiguration {

    @Autowired
    private RedissonConfig redissonConfig;


    @Bean(destroyMethod = "shutdown")
    public RedissonClient initRedissonClient() {

        log.info(" initRedissonClient address {} database {}", redissonConfig.getAddress(), redissonConfig.getDatabase());
        RedissonClient redissonClient = null;
        try {
            Config config = new Config();
            config.setCodec(new JsonJacksonCodec());
            config.setNettyThreads(64);

            if (redissonConfig.getIsCluster() == 1) {
                log.info("redisson Cluster start ");
                String[] nodes = redissonConfig.getAddress().split(",");
                ClusterServersConfig serverConfig = config.useClusterServers();
                serverConfig.addNodeAddress(nodes);
                serverConfig.setKeepAlive(true);
                serverConfig.setPingConnectionInterval(redissonConfig.getPingConnectionInterval());
                serverConfig.setPingTimeout(redissonConfig.getPingTimeout());
                serverConfig.setTimeout(redissonConfig.getTimeout());
                serverConfig.setConnectTimeout(redissonConfig.getConnectTimeout());

                if (!StrUtil.isEmpty(redissonConfig.getPassword())) {
                    serverConfig.setPassword(redissonConfig.getPassword());
                }
            } else {
                log.info("redisson Single start ");
                SingleServerConfig serverConfig = config.useSingleServer()
                        .setAddress(redissonConfig.getAddress())
                        .setDatabase(redissonConfig.getDatabase());
                serverConfig.setKeepAlive(true);
                serverConfig.setPingConnectionInterval(redissonConfig.getPingConnectionInterval());
                serverConfig.setPingTimeout(redissonConfig.getPingTimeout());
                serverConfig.setTimeout(redissonConfig.getTimeout());
                serverConfig.setConnectTimeout(redissonConfig.getConnectTimeout());
                serverConfig.setConnectionMinimumIdleSize(redissonConfig.getConnectionMinimumIdleSize());

                serverConfig.setConnectionPoolSize(redissonConfig.getConnectionPoolSize());

                if (!StrUtil.isEmpty(redissonConfig.getPassword())) {
                    serverConfig.setPassword(redissonConfig.getPassword());
                }
            }
            redissonClient = Redisson.create(config);

            log.info("redisson create end ");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return redissonClient;
    }


}
