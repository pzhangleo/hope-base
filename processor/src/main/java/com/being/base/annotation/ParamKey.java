package com.being.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhangpeng on 17/1/22.
 */
@SuppressWarnings("unused")
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface ParamKey {
    String value();
}
