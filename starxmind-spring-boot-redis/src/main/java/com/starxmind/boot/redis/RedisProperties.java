package com.starxmind.boot.redis;

import com.starxmind.piano.redis.DeploymentMode;
import com.starxmind.piano.redis.config.Cluster;
import com.starxmind.piano.redis.config.Pool;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Redis配置信息
 *
 * @author pizzalord
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "starxmind.redis")
@Builder(toBuilder = true)
@Data
public class RedisProperties {
    private DeploymentMode mode;
    private String host;
    private int port;
    private String password;
    private int database;
    @Builder.Default
    private int timeout = 3000;
    private Pool pool;
    private Cluster cluster;
}
