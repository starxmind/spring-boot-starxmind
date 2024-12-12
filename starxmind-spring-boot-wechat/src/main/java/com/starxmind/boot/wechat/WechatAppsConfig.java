package com.starxmind.boot.wechat;

import com.starxmind.piano.wechat.token.core.WechatApp;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * TODO
 *
 * @author pizzalord
 * @since 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "starxmind.wechat")
public class WechatAppsConfig {
    private List<WechatApp> apps;

    public WechatApp getApp(String appKey) {
        return apps.stream().filter(app -> app.getAppKey().equals(appKey)).findFirst().orElse(null);
    }
}
