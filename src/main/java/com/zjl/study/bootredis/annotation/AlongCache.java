package com.zjl.study.bootredis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作用在方法上
 * 将方法的返回值缓存到redis缓存服务器中
 * 格式：key: value
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AlongCache {

    String key();

    String value();

}
