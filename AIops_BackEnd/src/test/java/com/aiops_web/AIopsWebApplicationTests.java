package com.aiops_web;

import com.aiops_web.service.mysql.AnomalyInfoService;
import com.aiops_web.service.mysql.UserService;
import com.aiops_web.service.mysql.impl.AiopsAlgServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AIopsWebApplicationTests {

    @Autowired
    AiopsAlgServiceImpl aiopsAlgService;
    @Autowired
    UserService userService;
    @Autowired
    AnomalyInfoService anomalyInfoService;


    @Test
    void JWTTest() {


    }

}
