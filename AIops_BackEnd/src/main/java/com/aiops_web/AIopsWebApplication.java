package com.aiops_web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.aiops_web.dao")
public class AIopsWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(AIopsWebApplication.class, args);
    }

}
