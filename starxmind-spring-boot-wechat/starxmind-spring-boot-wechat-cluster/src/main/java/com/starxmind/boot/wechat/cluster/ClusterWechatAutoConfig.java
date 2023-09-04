package com.starxmind.boot.wechat.cluster;

import com.starxmind.bass.http.StarxHttp;
import com.starxmind.piano.wechat.client.WechatClient;
import com.starxmind.piano.wechat.token.core.WeChatInfo;
import com.starxmind.piano.wechat.token.redis.RedisAccessTokenManager;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
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
public class ClusterWechatAutoConfig {
    @Autowired
    private StarxHttp StarxHttp;

    @Autowired
    private RedissonClient redissonClient;

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
        RedisAccessTokenManager accessTokenManager = new RedisAccessTokenManager(StarxHttp, weChatInfo, redissonClient);
        return new WechatClient(weChatInfo, StarxHttp, accessTokenManager);
    }
}
