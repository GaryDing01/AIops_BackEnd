package com.aiops_web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.aiops_web.dao")
@EnableScheduling //定时器注解
public class AIopsWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(AIopsWebApplication.class, args);
    }

}
