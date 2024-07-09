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
public @interface TryLock {
    /**
     * 锁的名称
     * 比如为方法加锁:
     * e.g.: lock_prefix_name_${province.city.county.name}_${key2}
     */
    String value();

    /**
     * 获取锁的等待时间,超过这个时间,则拿不到锁
     *
     * @return
     */
    long waitTime() default 0;

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
