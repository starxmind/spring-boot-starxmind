package com.starxmind.boot.redis;

import com.starxmind.piano.redis.DistributedXLockFactory;
import com.starxmind.piano.redis.RedissonClientCreator;
import com.starxmind.piano.redis.config.ClusterConfig;
import com.starxmind.piano.redis.config.Pool;
import com.starxmind.piano.redis.config.StandaloneConfig;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 集群Redis配置
 *
 * @author pizzalord
 * @since 1.0
 */
@ComponentScan(basePackages = {"com.starxmind.boot.redis"})
@Configuration
@RequiredArgsConstructor
public class RedisAutoConfig {
    private final RedisProperties redisProperties;

    /**
     * 注册redis客户端: redisson client
     * 支持模式: 单机模式,集群模式
     * 暂未支持: 哨兵模式,主从模式
     *
     * @return redis客户端 - RedissonClient
     */
    @Bean
    public RedissonClient redissonClient() {
        RedissonClientCreator creator = new RedissonClientCreator();
        // 连接池配置
        if (redisProperties.getPool() == null) {
            Pool pool = new Pool();
            pool.setMaxPoolSize(32);
            pool.setMinIdleSize(4);
            redisProperties.setPool(pool);
        }
        switch (redisProperties.getMode()) {
            case Standalone:
                StandaloneConfig standaloneConfig = new StandaloneConfig();
                BeanUtils.copyProperties(redisProperties, standaloneConfig);
                return creator.singleServerConfig(standaloneConfig);
            case Cluster:
                ClusterConfig clusterConfig = new ClusterConfig();
                BeanUtils.copyProperties(redisProperties, clusterConfig);
                return creator.clusterServerConfig(clusterConfig);
            default:
                throw new IllegalStateException("Unknown redis mode: " + redisProperties.getMode());
        }
    }

    /**
     * 注册DistributedLockFactory
     *
     * @return 分布式锁工厂
     */
    @Bean
    public DistributedXLockFactory distributedXLockFactory() {
        return new DistributedXLockFactory(redissonClient());
    }
}
