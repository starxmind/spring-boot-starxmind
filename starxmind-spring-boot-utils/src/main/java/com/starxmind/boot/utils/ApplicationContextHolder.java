package com.starxmind.boot.utils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Application context holder
 *
 * @author pizzalord
 * @since 1.0
 */
@Getter
@Slf4j
@Component
public class ApplicationContextHolder implements ApplicationContextAware {
    /**
     * -- GETTER --
     *  获取Spring上下文
     *
     * @return
     */
    private ApplicationContext applicationContext;

    /**
     * 在Spring启动时hold住上下文
     *
     * @param applicationContext Spring上下文
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        log.info("Application context is hold by ApplicationContextHolder...");
    }

    /**
     * 根据Bean的名称获取Bean实例
     *
     * @param beanName
     * @param <T>
     * @return
     */
    public <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    /**
     * 根据Bean的类型获取Bean实例
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getBean(Class<T> clazz) {
        return (T) applicationContext.getBean(clazz);
    }

    public <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return applicationContext.getBeansOfType(clazz);
    }
}
