package com.aiops_web.controller;

import com.aiops_web.service.neo4j.OriginalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/originalData")
public class OriginalDataController {
    @Resource
    private OriginalDataService originalDataService;

    @PostMapping("/createDoc")
    public void createDoc(Integer batchId, String content, Integer objId) throws IOException {
        originalDataService.createDocument(batchId, content, objId);
    }
}
