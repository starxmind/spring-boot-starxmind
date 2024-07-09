package com.starxmind.boot.concurrent.lock;

import com.google.common.collect.Maps;
import com.starxmind.bass.concurrent.lock.XLockFactory;
import com.starxmind.boot.utils.ApplicationContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class XLockFactoryHolder implements InitializingBean {
    private static final Map<String, XLockFactory> CONTAINER = Maps.newHashMap();
    private final ApplicationContextHolder applicationContextHolder;

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, XLockFactory> map = applicationContextHolder.getApplicationContext().getBeansOfType(XLockFactory.class);
        map.forEach(
                (k, v) -> {
                    String className = v.getClass().getName();
                    CONTAINER.put(className, v);
                    log.info("<Starxmind XLockFactory> has found a bean, className: {}", className);
                }
        );
        log.info("<Starxmind XLockFactory> totally has found <{}> XLockFactories", CONTAINER.size());
    }

    public XLockFactory get(Class<? extends XLockFactory> clazz) {
        return CONTAINER.get(clazz.getName());
    }
}
