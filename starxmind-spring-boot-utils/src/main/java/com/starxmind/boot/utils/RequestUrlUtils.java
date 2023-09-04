package com.starxmind.boot.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Arrays;
import java.util.List;

/**
 * Request url utils
 *
 * @author Xpizza
 * @since starxmind1.0
 */
@Slf4j
public class RequestUrlUtils {
    /**
     * 获取url域名后第index个'/'之前的字符串
     *
     * @param value
     * @param token
     * @param index
     * @return
     */
    public static String substring(String value, String token, int index) {
        int posStart = -1;
        int posEnd = value.length() - 1;
        int searchIndex = 0;
        for (int i = 1; i <= index + 1; i++) {
            searchIndex = value.indexOf(token, searchIndex);
            if (searchIndex > -1) {
                if (i >= index) {
                    if (posStart == -1) {
                        posStart = searchIndex;
                    } else {
                        posEnd = searchIndex;
                    }
                }
                searchIndex++;
            }
        }
        posStart++;
        if (posStart > value.length()) {
            posStart = value.length() - 1;
        }

        return value.substring(posStart, posEnd);
    }

    public static boolean isStaticPage(String url) {
        List<String> suffixList = Arrays.asList(".html", ".css", ".js", ".css", ".png", ".ttf");
        List<String> passList = Arrays.asList("swagger-resources/configuration/ui", "swagger-resources", "v2/api-docs", "swagger-resources/configuration/security", "health", "xplat/health", "error");
        if (StringUtils.isBlank(url)) {
            return false;
        }
        String path = url.trim();
        if (StringUtils.startsWith(path, "/")) {
            path = StringUtils.substring(path, 1);
        }
        boolean match = suffixList.stream().anyMatch(path::endsWith) || passList.stream().anyMatch(path::equals);
        return match;
    }

    public static PathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 判断此API是否开放
     *
     * @param url
     * @return
     */
    public static boolean isOpenApi(String url, List<String> excludeUrlPatterns) {
        if (CollectionUtils.isEmpty(excludeUrlPatterns)) {
            return false;
        }
        for (String pattern : excludeUrlPatterns) {
            if (pathMatcher.match(pattern, url)) {
                log.debug("例外路径,放行 - {}", url);
                return true;
            }
        }
        return false;
    }
}
