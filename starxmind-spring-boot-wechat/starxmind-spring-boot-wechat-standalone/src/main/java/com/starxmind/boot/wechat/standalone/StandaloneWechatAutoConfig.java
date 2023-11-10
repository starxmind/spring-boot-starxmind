package com.starxmind.boot.wechat.standalone;

import com.starxmind.bass.http.StarxHttp;
import com.starxmind.piano.token.memory.MemoryAccessTokenManager;
import com.starxmind.piano.wechat.client.WechatClient;
import com.starxmind.piano.wechat.token.core.WeChatInfo;
import lombok.extern.slf4j.Slf4j;
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

    @Bean
    public WechatClient wechatClient(@Value("${starxmind.wechat.appid}") String appId,
                                     @Value("${starxmind.wechat.secret}") String secret,
                                     StarxHttp starxHttp) {
        WeChatInfo weChatInfo = WeChatInfo.builder()
                .appId(appId)
                .secret(secret)
                .build();
        MemoryAccessTokenManager accessTokenManager = new MemoryAccessTokenManager(starxHttp, weChatInfo);
        return new WechatClient(weChatInfo, starxHttp, accessTokenManager);
    }
}
