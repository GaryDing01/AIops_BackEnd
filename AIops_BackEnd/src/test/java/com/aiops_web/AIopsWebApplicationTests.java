package com.aiops_web;

import com.aiops_web.dao.sql.AiopsAlgMapper;
import com.aiops_web.entity.sql.AnomalyInfo;
import com.aiops_web.entity.sql.User;
import com.aiops_web.service.AiopsAlgService;
import com.aiops_web.service.AnomalyInfoService;
import com.aiops_web.service.UserService;
import com.aiops_web.service.impl.AiopsAlgServiceImpl;
import com.aiops_web.std.JWTUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

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
