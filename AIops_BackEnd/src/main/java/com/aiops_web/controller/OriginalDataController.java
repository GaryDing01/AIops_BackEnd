package com.aiops_web.controller;

import com.aiops_web.service.OriginalDataService;
import com.aiops_web.std.ResponseStd;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("")
    public ResponseStd getAll() {
        return new ResponseStd(originalDataService.getAll());
    }

    @GetMapping("/range")
    public ResponseStd getRange(int beginId, int endId) throws IOException {
        return new ResponseStd(originalDataService.getRange(beginId, endId));
    }

    @DeleteMapping("/range")
    public ResponseStd deleteRange(int beginId, int endId) throws IOException {
        return new ResponseStd(originalDataService.deleteRange(beginId, endId));
    }

    @PostMapping("/addBatch")
    public void addBatchDoc(int batchId, int objId, String filepath) {
        originalDataService.addBatchDoc(batchId, objId, filepath);
    }
}
