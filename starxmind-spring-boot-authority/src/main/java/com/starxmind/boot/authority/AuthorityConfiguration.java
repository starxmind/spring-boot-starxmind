package com.starxmind.boot.authority;

import com.starxmind.boot.utils.ApplicationContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Authority configuration
 *
 * @author Xpizza
 * @since starxmind1.0
 */
@Slf4j
@Configuration
public class AuthorityConfiguration implements WebMvcConfigurer {
    @Autowired
    private ApplicationContextHolder applicationContextHolder;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor());
    }

    @Bean
    public AuthorityInterceptor authInterceptor() {
        AuthorityInterceptor authorityInterceptor = new AuthorityInterceptor(applicationContextHolder);
        log.info("AuthorityInterceptor is loaded successfully.");
        return authorityInterceptor;
    }
}
