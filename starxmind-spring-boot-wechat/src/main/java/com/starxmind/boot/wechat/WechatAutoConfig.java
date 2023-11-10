package com.starxmind.boot.wechat;

import com.starxmind.bass.http.StarxHttp;
import com.starxmind.piano.token.memory.MemoryAccessTokenManager;
import com.starxmind.piano.wechat.client.WechatClient;
import com.starxmind.piano.wechat.token.core.AccessTokenManager;
import com.starxmind.piano.wechat.token.core.WeChatInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Constructor;

@Configuration
public class WechatAutoConfig {

    @Bean
    public WechatClient wechatClient(@Value("${starxmind.wechat.appid}") String appId,
                                     @Value("${starxmind.wechat.secret}") String secret,
                                     @Value("${starxmind.wechat.access-token-manager.classname:}") String accessTokenManagerClassname,
                                     StarxHttp starxHttp) throws Exception {
        // 传递雪花ID池的实现类名来指定雪花ID池
        if (StringUtils.isBlank(accessTokenManagerClassname)) {
            accessTokenManagerClassname = MemoryAccessTokenManager.class.getName();
        }
        Class<?> childClass = Class.forName(accessTokenManagerClassname);
        Constructor<?> constructor = childClass.getConstructor(StarxHttp.class, WeChatInfo.class);
        WeChatInfo weChatInfo = WeChatInfo.builder()
                .appId(appId)
                .secret(secret)
                .build();
        AccessTokenManager accessTokenManager = (AccessTokenManager) constructor.newInstance(starxHttp, weChatInfo);
        return new WechatClient(weChatInfo, starxHttp, accessTokenManager);
    }
}
