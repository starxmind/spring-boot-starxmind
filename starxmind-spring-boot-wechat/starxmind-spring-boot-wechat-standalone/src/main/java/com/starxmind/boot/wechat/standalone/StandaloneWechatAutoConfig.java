package com.starxmind.boot.wechat.standalone;

import com.starxmind.bass.http.StarxHttp;
import com.starxmind.piano.token.memory.MemoryAccessTokenManager;
import com.starxmind.piano.wechat.client.WechatClient;
import com.starxmind.piano.wechat.token.core.WeChatInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 *
 * @author pizzalord
 * @since 1.0
 */
@Slf4j
@Configuration
public class StandaloneWechatAutoConfig {
    @Autowired
    private StarxHttp StarxHttp;

    @Value("${starxmind.wechat.appid}")
    private String appId;

    @Value("${starxmind.wechat.secret}")
    private String secret;

    @Bean
    public WechatClient wechatClient() {
        WeChatInfo weChatInfo = WeChatInfo.builder()
                .appId(appId)
                .secret(secret)
                .build();
        MemoryAccessTokenManager accessTokenManager = new MemoryAccessTokenManager(StarxHttp, weChatInfo);
        return new WechatClient(weChatInfo, StarxHttp, accessTokenManager);
    }
}
