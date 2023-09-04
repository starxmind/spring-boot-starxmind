package com.starxmind.boot.response;

import com.starxmind.boot.response.options.autoload.OptionsFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 返回自动配置
 *
 * @author pizzalord
 * @since 1.0
 */
@Slf4j
@Configuration
public class ResponseAutoConfig {
    @Bean
    public ResponseControllerAdvice responseAdvice() {
        log.info("starxmind spring-boot: ResponseControllerAdvice is registered...");
        return new ResponseControllerAdvice();
    }

    @Bean
    public OptionsFactory optionsFactory() {
        log.info("starxmind: OptionsFactory is registered...");
        return new OptionsFactory();
    }
}
