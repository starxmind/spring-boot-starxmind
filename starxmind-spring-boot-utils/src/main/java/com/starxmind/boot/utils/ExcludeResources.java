package com.starxmind.boot.utils;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * 例外资源
 *
 * @author pizzalord
 * @since 1.0
 */
@Slf4j
public abstract class ExcludeResources {
    /**
     * 静态页面模式
     */
    private static final Set<String> EXCLUDE_PATTERNS = Sets.newHashSet(
            "", "/",
            "/favicon.ico",
            "/**/*.html", "/**/*.css", "/**/*.js", "/**/*.css", "/**/*.png", "/**/*.ttf", // 静态资源
            "/error", "/csrf", "/swagger-resources/**", "/v2/api-docs/**", "/**/springfox-swagger-ui/**", // swagger
            "/**/health" // 健康检查
    );

    /**
     * 对路径进行匹配,判断该接口是否为静态资源
     *
     * @param path
     * @return
     */
    public static boolean match(String path) {
        return PathUtils.match(path, EXCLUDE_PATTERNS);
    }

}
