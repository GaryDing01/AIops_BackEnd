package com.aiops_web.controller;


import com.aiops_web.entity.elasticsearch.OriginalData;
import com.aiops_web.entity.sql.DataIntroducing;
import com.aiops_web.service.DataIntroducingService;
import com.aiops_web.service.OriginalDataService;
import com.aiops_web.std.ErrorCode;
import com.aiops_web.std.ResponseStd;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @since 2023-04-12
 */
@RestController
@RequestMapping("/dataIntro")
public class DataIntroducingController {
    private final DataIntroducingService dataIntroducingService;
    private final OriginalDataService originalDataService;

    public DataIntroducingController(DataIntroducingService dataIntroducingService,
                                     OriginalDataService originalDataService) {
        this.dataIntroducingService = dataIntroducingService;
        this.originalDataService = originalDataService;
    }

    @PostMapping("")
    public ResponseStd<Boolean> saveOne(@RequestBody DataIntroducing dataIntroducing) {
        String filePath = dataIntroducing.getSource();
        int batchId = dataIntroducing.getBatchId();
        int objId = dataIntroducing.getObjId();
        // 没有传入源日志的文件路径
        if (filePath == null || batchId < 0 || objId < 0) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR);
        }
        File file = new File(filePath);
        // 传入的文件路径不存在
        if (!file.exists()) {
            return new ResponseStd<>(ErrorCode.PARAMS_ERROR);
        }
        originalDataService.addBatchDoc(batchId, objId, filePath);
        List<OriginalData> sampleList = originalDataService.getRelativeRange(batchId, 1, 5);
        dataIntroducing.setDataSample(sampleList.toString());
        // 获得当前时间
        Date date = new Date();
        Timestamp t = new Timestamp(date.getTime()); //将时间转换成 Timestamp 类型，这样便可以存入到 Mysql 数据库中
        dataIntroducing.setTstamp(t);
        // 刚导入的源数据放在 OriginalData 中
        dataIntroducing.setPlace("OriginalData");
        return new ResponseStd<>(dataIntroducingService.save(dataIntroducing));
    }

    @DeleteMapping("/{batchId}")
    public ResponseStd<Boolean> deleteOne(@PathVariable(value = "batchId") int batchId) {
        return new ResponseStd<>(dataIntroducingService.removeById(batchId));
    }

    @PutMapping("")
    public ResponseStd<Boolean> updateOne(@RequestBody DataIntroducing dataIntroducing) {
        Date date = new Date();
        Timestamp t = new Timestamp(date.getTime());
        dataIntroducing.setTstamp(t);
        return new ResponseStd<>(dataIntroducingService.saveOrUpdate(dataIntroducing));
    }

    @GetMapping("")
    public ResponseStd<List<DataIntroducing>> getAll() {
        return new ResponseStd<>(dataIntroducingService.list());
    }
}

