package com.starxmind.boot.http;

import com.starxmind.bass.http.StarxHttp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Http client auto config
 *
 * @author pizzalord
 * @since 1.0
 */
@Configuration
public class StarxHttpAutoConfig {
    @Value("${starxmind.http.timeoutSeconds:60}")
    private long timeoutSeconds;

    @Value("${starxmind.http.timeoutSeconds:30}")
    private int maxIdleConnections;

    @Value("${starxmind.http.timeoutSeconds:10}")
    private long keepAliveDurationSeconds;

    @Bean
    public StarxHttp starxHttp() {
        return new StarxHttp(timeoutSeconds, maxIdleConnections, keepAliveDurationSeconds);
    }
}
