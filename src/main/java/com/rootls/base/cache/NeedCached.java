package com.rootls.base.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @className:NeedCached
 * @classDescription:
 * @author:Administrator
 * @createTime:12-6-12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface NeedCached {
    int MINUTE = 60 * 1000;
    int HOUR = 60 * MINUTE;
    int DAY = 24 * HOUR;


    int timeToLive() default 60 * 1000;
}
