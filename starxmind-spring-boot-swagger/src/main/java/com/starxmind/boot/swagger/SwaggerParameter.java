package com.starxmind.boot.swagger;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * swagger定义参数
 *
 * @author pizzalord
 * @since 1.0
 */
@NoArgsConstructor
@Data
public class SwaggerParameter {
    /**
     * 参数名
     */
    private String name;

    /**
     * 参数描述
     */
    private String description;

    /**
     * 参数类型
     */
    private String type;

    /**
     * 参数作用域
     */
    private String scope;

    /**
     * 参数是否必填
     */
    private boolean required;
}
