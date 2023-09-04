package com.starxmind.boot.utils;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;

import java.util.Set;

/**
 * 请求路径工具
 *
 * @author pizzalord
 * @since 1.0
 */
@Slf4j
public abstract class PathUtils {

    /**
     * Spring提供的路由匹配器
     */
    public static PathMatcher PATH_MATCHER = new AntPathMatcher();

    /**
     * 判断请求路径是否匹配规则数组中至少一个规则
     *
     * @param path 请求路径
     * @return 匹配结果
     */
    public static boolean match(String path, String[] patterns) {
        return match(path, Sets.newHashSet(patterns));
    }

    /**
     * 判断请求路径是否匹配规则集中至少一个规则
     *
     * @param path 请求路径
     * @return 匹配结果
     */
    public static boolean match(String path, Set<String> patterns) {
        if (CollectionUtils.isEmpty(patterns)) {
            return false;
        }
        return patterns.stream().anyMatch(pattern -> PATH_MATCHER.match(pattern, path));
    }

}
