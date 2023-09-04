package com.starxmind.boot.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger配置属性
 *
 * @author pizzalord
 * @since 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "starxmind.swagger")
public class SwaggerProperties {
    /**
     * 扫描路径
     */
    private String basePackage = "com.starxmind";

    /**
     * 服务条款
     */
    private String termsOfServiceUrl = "https://starxmind.com";

    /**
     * swagger参数列表
     */
    private List<SwaggerParameter> parameters;
}
