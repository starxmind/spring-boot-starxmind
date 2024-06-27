package com.starxmind.boot.http;

import com.starxmind.bass.http.XHttp;
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
public class XHttpAutoConfig {
    @Value("${starxmind.http.timeoutSeconds:60}")
    private long timeoutSeconds;

    @Value("${starxmind.http.timeoutSeconds:30}")
    private int maxIdleConnections;

    @Value("${starxmind.http.timeoutSeconds:10}")
    private long keepAliveDurationSeconds;

    @Bean
    public XHttp XHttp() {
        return new XHttp(timeoutSeconds, maxIdleConnections, keepAliveDurationSeconds);
    }
}
