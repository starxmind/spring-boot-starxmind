package com.starxmind.boot.concurrent;


import com.starxmind.bass.concurrent.lock.memory.MemoryXLockFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Ldap自动配置
 *
 * @author pizzalord
 * @since 1.0
 */
@Slf4j
@ComponentScan(basePackages = {"com.starxmind.boot.concurrent"})
@Configuration
public class ConcurrentAutoConfig {
    @Bean
    public MemoryXLockFactory memoryXLockFactory() {
        return new MemoryXLockFactory();
    }
}
