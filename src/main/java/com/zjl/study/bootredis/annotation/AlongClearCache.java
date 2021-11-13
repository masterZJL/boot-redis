package com.zjl.study.bootredis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 清空注解指定的key对应redis中缓存的数据。
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AlongClearCache {

    String key();

    String value();

}
