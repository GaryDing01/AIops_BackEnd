package com.aiops_web;

import com.aiops_web.dao.sql.AiopsAlgMapper;
import com.aiops_web.service.AiopsAlgService;
import com.aiops_web.service.impl.AiopsAlgServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AIopsWebApplicationTests {

    @Autowired
    AiopsAlgServiceImpl aiopsAlgService;

    @Test
    void contextLoads() throws JsonProcessingException {
//        aiopsAlgService.getAlgByUserId(2053677);
    }

}
