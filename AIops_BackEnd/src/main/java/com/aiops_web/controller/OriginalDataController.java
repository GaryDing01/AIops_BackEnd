package com.aiops_web.controller;

import com.aiops_web.entity.elasticsearch.OriginalData;
import com.aiops_web.service.mysql.OriginalDataService;
import com.aiops_web.std.ResponseStd;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseStd<List<OriginalData>> getRange(long beginId, long endId) {
        return new ResponseStd<>(originalDataService.getRange(beginId, endId));
    }

    @GetMapping("/relativeRange")
    public ResponseStd<List<OriginalData>> getRelativeRange(int batchId, long beginId, long endId) {
        return new ResponseStd<>(originalDataService.getRelativeRange(batchId, beginId, endId));
    }

    @DeleteMapping("/range")
    public ResponseStd<Boolean> deleteRange(long beginId, long endId) {
        return new ResponseStd<>(originalDataService.deleteRange(beginId, endId));
    }

    @GetMapping("/test")
    public ResponseStd<Long> getAddTest() {
        return new ResponseStd<Long>(originalDataService.addBatchDoc(1, 1, "1"));
    }
}
