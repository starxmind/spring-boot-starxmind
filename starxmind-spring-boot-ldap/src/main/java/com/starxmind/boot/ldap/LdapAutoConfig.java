package com.starxmind.boot.ldap;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Ldap自动配置
 *
 * @author pizzalord
 * @since 1.0
 */
@Slf4j
@Configuration
public class LdapAutoConfig {
    @Bean
    public LdapClient ldapClient() {
        return new LdapClient();
    }
}
