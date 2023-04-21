package com.aiops_web.controller;

import com.aiops_web.service.OriginalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 对 es 的 origin_data 进行操作
 *
 * @author DaiQL
 * @time 2023/4/17
 */
@RestController
@RequestMapping("/originalData")
public class OriginalDataController {
    @Autowired
    private OriginalDataService originalDataService;

    @PostMapping("/createDoc")
    public void createDoc(Integer batchId, String content, Integer objId) throws IOException {
        originalDataService.createDocument(batchId, content, objId);
    }
}
