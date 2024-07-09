package com.starxmind.boot.concurrent.lock.annotation;

import com.starxmind.bass.concurrent.lock.XLockFactory;
import com.starxmind.bass.concurrent.lock.memory.MemoryXLockFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LeaseLock {
    /**
     * 锁的名称
     * e.g.: lock_prefix_name_${province.city.county.name}_${key2}
     */
    String value();

    /**
     * 锁租赁时间
     */
    long leaseTime() default 30;

    /**
     * 时间单位
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * Class of XLockFactory implements
     */
    Class<? extends XLockFactory> clazz() default MemoryXLockFactory.class;
}
