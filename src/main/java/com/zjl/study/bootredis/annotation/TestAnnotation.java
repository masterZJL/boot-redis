package com.zjl.study.bootredis.annotation;

import java.lang.annotation.*;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface TestAnnotation {
}
