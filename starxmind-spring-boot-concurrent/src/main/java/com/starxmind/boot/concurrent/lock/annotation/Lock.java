package com.starxmind.boot.concurrent.lock.annotation;

import com.starxmind.bass.concurrent.lock.XLockFactory;
import com.starxmind.bass.concurrent.lock.memory.MemoryXLockFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Lock {
    /**
     * 锁的名称
     * e.g.: lock_prefix_name_${province.city.county.name}_${key2}
     */
    String value();

    /**
     * Class of XLockFactory implements
     */
    Class<? extends XLockFactory> clazz() default MemoryXLockFactory.class;
}
