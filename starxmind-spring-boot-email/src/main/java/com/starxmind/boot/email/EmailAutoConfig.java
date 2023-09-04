package com.starxmind.boot.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Email configuration
 *
 * @author pizzalord
 * @since 1.0
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class EmailAutoConfig {
    @Value("${spring.mail.username:}")
    private String username;

    @Value("${spring.mail.nickname:Administrator}")
    private String nickname;

    /**
     * Java mail sender by spring
     */
    private final JavaMailSender mailSender;

    @Bean
    public EmailSender emailSender() {
        if (StringUtils.isEmpty(username)) {
            log.warn("Email sender is not registered, cause email info is not given...");
            return null;
        }
        EmailSender emailSender = new EmailSender(mailSender, String.format("%s<%s>", nickname, username));
        log.info("EmailSender is loaded successfully");
        return emailSender;
    }

    /**
     * 后加的防止题目过长并且进行全局定义
     */
    static {
        System.setProperty("mail.mime.splitlongparameters", "false");
        System.setProperty("mail.mime.charset", "UTF-8");
    }
}
