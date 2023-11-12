package com.starxmind.boot.wechat;

import com.starxmind.bass.http.StarxHttp;
import com.starxmind.piano.token.memory.MemoryAccessTokenManager;
import com.starxmind.piano.wechat.client.WechatClient;
import com.starxmind.piano.wechat.token.core.AccessTokenManager;
import com.starxmind.piano.wechat.token.core.WeChatInfo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Constructor;

@Configuration
@RequiredArgsConstructor
public class WechatAutoConfig {
    private final AutowireCapableBeanFactory beanFactory;

    @Bean
    public WechatClient wechatClient(@Value("${starxmind.wechat.access-token-manager.classname:}") String classname,
                                     @Value("${starxmind.wechat.appid}") String appId,
                                     @Value("${starxmind.wechat.secret}") String secret,
                                     StarxHttp starxHttp) throws Exception {
        WeChatInfo weChatInfo = WeChatInfo.builder()
                .appId(appId)
                .secret(secret)
                .build();

        // 传递Token管理器的实现类名来指定Token管理器
        if (StringUtils.isBlank(classname)) {
            classname = MemoryAccessTokenManager.class.getName();
        }
        Class<?> childClass = Class.forName(classname);
        for (Constructor<?> constructor : childClass.getConstructors()) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            if ( parameterTypes[0] .equals(WeChatInfo.class) &&
                    parameterTypes[1] .equals(StarxHttp.class)) {
                // 动态创建参数实例
                Object[] parameters = new Object[parameterTypes.length];
                parameters[0] = starxHttp;
                parameters[1] = weChatInfo;
                for (int i = 2; i < parameterTypes.length; i++) {
                    parameters[i] = beanFactory.createBean(parameterTypes[i]);
                }
                AccessTokenManager accessTokenManager = (AccessTokenManager) constructor.newInstance(parameters);
                return new WechatClient(weChatInfo, starxHttp, accessTokenManager);
            }
        }
        throw new IllegalArgumentException("No suitable constructor found for " + childClass.getName());
    }
}
