package com.zjl.study.bootredis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan
@EnableCaching
@EnableAspectJAutoProxy
public class BootRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootRedisApplication.class, args);
    }

}
