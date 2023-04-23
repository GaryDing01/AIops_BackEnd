package com.aiops_web.controller;


import com.aiops_web.entity.sql.DataIntroducing;
import com.aiops_web.service.DataIntroducingService;
import com.aiops_web.std.ResponseStd;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;

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

    public DataIntroducingController(DataIntroducingService dataIntroducingService) {
        this.dataIntroducingService = dataIntroducingService;
    }

    @PostMapping("")
    public ResponseStd saveOne(@RequestBody DataIntroducing dataIntroducing) {
        Date date = new Date(); //获得当前时间
        Timestamp t = new Timestamp(date.getTime()); //将时间转换成 Timestamp 类型，这样便可以存入到 Mysql 数据库中
        dataIntroducing.setTstamp(t);
        return new ResponseStd(dataIntroducingService.save(dataIntroducing));
    }

    @DeleteMapping("/{batchId}")
    public ResponseStd deleteOne(@PathVariable(value = "batchId") int batchId) {
        return new ResponseStd(dataIntroducingService.removeById(batchId));
    }

    @PutMapping("")
    public ResponseStd updateOne(@RequestBody DataIntroducing dataIntroducing) {
        Date date = new Date();
        Timestamp t = new Timestamp(date.getTime());
        dataIntroducing.setTstamp(t);
        return new ResponseStd(dataIntroducingService.saveOrUpdate(dataIntroducing));
    }

    @GetMapping("")
    public ResponseStd getAll() {
        return new ResponseStd(dataIntroducingService.list());
    }
}

