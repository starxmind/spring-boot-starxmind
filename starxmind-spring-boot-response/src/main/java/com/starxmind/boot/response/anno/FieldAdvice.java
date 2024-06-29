package com.starxmind.boot.response.anno;

import com.starxmind.boot.response.FieldHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldAdvice {
    Class<? extends FieldHandler> fieldHandler() default FieldHandler.class;
}
