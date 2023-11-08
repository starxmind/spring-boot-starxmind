package com.starxmind.boot.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 返回自动配置
 *
 * @author pizzalord
 * @since 1.0
 */
@Slf4j
@ComponentScan(basePackages = "com.starxmind.boot.response")
@Configuration
public class ResponseAutoConfig {
//    @Bean
//    public ResponseControllerAdvice responseAdvice() {
//        log.info("starxmind spring-boot: ResponseControllerAdvice is registered...");
//        return new ResponseControllerAdvice();
//    }
//
//    @Bean
//    public OptionsFactory optionsFactory() {
//        log.info("starxmind: OptionsFactory is registered...");
//        return new OptionsFactory();
//    }
}
