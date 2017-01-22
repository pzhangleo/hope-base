package com.being.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhangpeng on 17/1/22.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Request {
    Method method() default Method.POST;

    String command();

    String superClass();

    Class<?> response();

    @SuppressWarnings("unused")
    public enum Method {
        POST,GET,PUT, DELETE,
    }
}
