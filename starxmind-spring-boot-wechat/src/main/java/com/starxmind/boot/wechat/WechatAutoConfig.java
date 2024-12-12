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
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.List;

@Configuration
@ComponentScan("com.starxmind.boot.wechat")
@RequiredArgsConstructor
public class WechatAutoConfig {
    private final AutowireCapableBeanFactory beanFactory;
    private final WechatAppsConfig wechatAppsConfig;

    @Bean
    public WechatClient wechatClient(@Value("${starxmind.wechat.access-token-manager.classname:}") String classname,
                                     XHttp XHttp) throws Exception {
        // 传递Token管理器的实现类名来指定Token管理器
        if (StringUtils.isBlank(classname)) {
            classname = MemoryAccessTokenManager.class.getName();
        }
        Class<?> clazz = Class.forName(classname);
        Constructor<?> constructor = ReflectionUtils.getSuitableConstructor(clazz,
                Lists.newArrayList(List.class));
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Object[] parameters = new Object[parameterTypes.length];
        parameters[0] = wechatAppsConfig.getApps();
        for (int i = 1; i < parameterTypes.length; i++) {
            parameters[i] = beanFactory.getBean(parameterTypes[i]);
        }
        AccessTokenManager accessTokenManager = (AccessTokenManager) constructor.newInstance(parameters);
        return new WechatClient(XHttp, accessTokenManager);
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
    public NativeWechatPay nativeWechatPay(PayConfig payConfig) {
        return new NativeWechatPay(payConfig);
    }

    @Bean
    public JsapiWechatPay jsapiWechatPay(PayConfig payConfig) {
        return new JsapiWechatPay(payConfig);
    }

    @Bean
    public WechatNotifyResolver wechatNotifyResolver(PayConfig payConfig) {
        return new WechatNotifyResolver(new WechatAesCipher(payConfig.getApiV3Key()));
    }
}
