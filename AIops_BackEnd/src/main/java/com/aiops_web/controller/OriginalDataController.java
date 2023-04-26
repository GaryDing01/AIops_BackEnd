package com.aiops_web.controller;

import com.aiops_web.service.OriginalDataService;
import com.aiops_web.std.ResponseStd;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final OriginalDataService originalDataService;

    public OriginalDataController(OriginalDataService originalDataService) {
        this.originalDataService = originalDataService;
    }

    @GetMapping("/range")
    public ResponseStd getRange(int beginId, int endId) throws IOException {
        return new ResponseStd(originalDataService.getRange(beginId, endId));
    }

    @GetMapping("/relativeRange")
    public ResponseStd getRelativeRange(int batchId, int beginId, int endId) {
        return new ResponseStd(originalDataService.getRelativeRange(batchId, beginId, endId));
    }

    @DeleteMapping("/range")
    public ResponseStd deleteRange(int beginId, int endId) throws IOException {
        return new ResponseStd(originalDataService.deleteRange(beginId, endId));
    }
}
