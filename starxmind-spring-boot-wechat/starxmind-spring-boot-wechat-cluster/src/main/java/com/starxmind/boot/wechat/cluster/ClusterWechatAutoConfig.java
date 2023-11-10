package com.starxmind.boot.wechat.cluster;

import com.starxmind.bass.http.StarxHttp;
import com.starxmind.piano.wechat.client.WechatClient;
import com.starxmind.piano.wechat.token.core.WeChatInfo;
import com.starxmind.piano.wechat.token.redis.RedisAccessTokenManager;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
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
public class ClusterWechatAutoConfig {

    @Bean
    public WechatClient wechatClient(@Value("${starxmind.wechat.appid}") String appId,
                                     @Value("${starxmind.wechat.secret}") String secret,
                                     StarxHttp starxHttp,
                                     RedissonClient redissonClient) {
        WeChatInfo weChatInfo = WeChatInfo.builder()
                .appId(appId)
                .secret(secret)
                .build();
        RedisAccessTokenManager accessTokenManager = new RedisAccessTokenManager(starxHttp, weChatInfo, redissonClient);
        return new WechatClient(weChatInfo, starxHttp, accessTokenManager);
    }
}
