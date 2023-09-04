package com.starxmind.boot.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Lock {
    /**
     * 锁的名称
     * e.g.: lock_prefix_name_${province.city.county.name}_${key2}
     */
    String value();

    /**
     * 锁定时间
     */
    long leaseTime() default 30;

    /**
     * 锁定时间的单位
     */
    TimeUnit unit() default TimeUnit.SECONDS;
}
