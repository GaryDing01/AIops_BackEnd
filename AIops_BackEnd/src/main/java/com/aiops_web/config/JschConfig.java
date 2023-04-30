package com.aiops_web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "jsch")
public class JschConfig {
    String username;
    String ip;
    Integer port;
    String password;
    String StrictHostKeyChecking;
}
