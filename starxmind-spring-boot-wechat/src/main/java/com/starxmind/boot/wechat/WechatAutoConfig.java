package com.starxmind.boot.wechat;

import com.google.common.collect.Lists;
import com.starxmind.bass.http.XHttp;
import com.starxmind.bass.sugar.ReflectionUtils;
import com.starxmind.boot.utils.ResourceUtils;
import com.starxmind.piano.token.memory.MemoryAccessTokenManager;
import com.starxmind.piano.wechat.client.WechatClient;
import com.starxmind.piano.wechat.pay.JsapiWechatPay;
import com.starxmind.piano.wechat.pay.NativeWechatPay;
import com.starxmind.piano.wechat.pay.PayConfig;
import com.starxmind.piano.wechat.pay.cipher.WechatAesCipher;
import com.starxmind.piano.wechat.pay.notify.WechatNotifyResolver;
import com.starxmind.piano.wechat.token.core.AccessTokenManager;
import com.starxmind.piano.wechat.token.core.WeChatInfo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.lang.reflect.Constructor;

@Configuration
@RequiredArgsConstructor
public class WechatAutoConfig {
    private final AutowireCapableBeanFactory beanFactory;

    @Bean
    public WechatClient wechatClient(@Value("${starxmind.wechat.access-token-manager.classname:}") String classname,
                                     @Value("${starxmind.wechat.appid}") String appId,
                                     @Value("${starxmind.wechat.secret}") String secret,
                                     XHttp XHttp) throws Exception {
        WeChatInfo weChatInfo = WeChatInfo.builder()
                .appId(appId)
                .secret(secret)
                .build();

        // 传递Token管理器的实现类名来指定Token管理器
        if (StringUtils.isBlank(classname)) {
            classname = MemoryAccessTokenManager.class.getName();
        }
        Class<?> clazz = Class.forName(classname);
        Constructor<?> constructor = ReflectionUtils.getSuitableConstructor(clazz,
                Lists.newArrayList(WeChatInfo.class));
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Object[] parameters = new Object[parameterTypes.length];
        parameters[0] = weChatInfo;
        for (int i = 1; i < parameterTypes.length; i++) {
            parameters[i] = beanFactory.createBean(parameterTypes[i]);
        }
        AccessTokenManager accessTokenManager = (AccessTokenManager) constructor.newInstance(parameters);
        return new WechatClient(weChatInfo, XHttp, accessTokenManager);
    }

    @Bean
    public PayConfig payConfig(@Value("${starxmind.wechat.pay.merchant-id}") String merchantId,
                               @Value("${starxmind.wechat.pay.private-key-path:}") String privateKeyPath,
                               @Value("${starxmind.wechat.pay.merchant-serial-number}") String merchantSerialNumber,
                               @Value("${starxmind.wechat.pay.api-v3-key}") String apiV3Key) throws IOException {
        String privateKey = null;
        if (StringUtils.isBlank(privateKeyPath)) {
            privateKey = ResourceUtils.read("wechatpay/cert");
        }
        return PayConfig.builder()
                .merchantId(merchantId)
                .privateKey(privateKey)
                .privateKeyPath(privateKeyPath)
                .merchantSerialNumber(merchantSerialNumber)
                .apiV3Key(apiV3Key)
                .build();
    }

    @Bean
    public NativeWechatPay nativeWechatPay(@Value("${starxmind.wechat.appid}") String appId,
                                           PayConfig payConfig) {
        return new NativeWechatPay(appId, payConfig);
    }

    @Bean
    public JsapiWechatPay jsapiWechatPay(@Value("${starxmind.wechat.appid}") String appId,
                                         PayConfig payConfig) {
        return new JsapiWechatPay(appId, payConfig);
    }

    @Bean
    public WechatNotifyResolver wechatNotifyResolver(PayConfig payConfig) {
        return new WechatNotifyResolver(new WechatAesCipher(payConfig.getApiV3Key()));
    }
}
