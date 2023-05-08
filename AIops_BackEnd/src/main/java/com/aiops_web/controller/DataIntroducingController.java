package com.aiops_web.controller;


import com.aiops_web.dto.AlgUserDTO;
import com.aiops_web.dto.DataIntroUserDTO;
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

    // DTO新增
    @PostMapping("")
    public ResponseStd<Integer> saveOne_new(@RequestBody DataIntroUserDTO dataIntroUserDTO) {

        DataIntroducing dataIntroducing = dataIntroducingService.saveOne(dataIntroUserDTO);
        if (dataIntroducing == null) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }

        String filePath = dataIntroducing.getFilePath();
        int batchId = dataIntroducing.getBatchId();
        int objId = dataIntroducing.getObjId();

        File file = new File(filePath);
        // 传入的文件路径不存在, 则直接返回dataIntroducing, 不返回
        if (!file.exists()) {
            System.out.println("The file path is not correct.");
            return new ResponseStd<Integer>(dataIntroducing.getBatchId());
        }

        long addNum = originalDataService.addBatchDoc(batchId, objId, filePath); // 完成导入
        dataIntroducing.setDataNum(addNum);
        List<OriginalData> sampleList = originalDataService.getRelativeRange(batchId, 1, 5);
        dataIntroducing.setDataSample(sampleList.toString());
        // 获得当前时间
        Date date = new Date();
        dataIntroducing.setTstamp(date);
        // 刚导入的源数据放在 OriginalData 中
        dataIntroducing.setPlace("OriginalData");
        // 因之前已经新建, 所以直接保存
        boolean updateResult = dataIntroducingService.updateById(dataIntroducing);
        if (updateResult == false) {
            return new ResponseStd<>(ErrorCode.SYSTEM_ERROR, null);
        }

        return new ResponseStd<Integer>(dataIntroducing.getBatchId());
    }

    @DeleteMapping("/{batchId}")
    public ResponseStd<Boolean> deleteOne(@PathVariable(value = "batchId") int batchId) {
        return new ResponseStd<Boolean>(dataIntroducingService.removeById(batchId));
    }

    @PutMapping("")
    public ResponseStd<Boolean> updateOne(@RequestBody DataIntroUserDTO dataIntroUserDTO) {
//        Date date = new Date(System.currentTimeMillis());
//        dataIntroducing.setTstamp(date);
//        return new ResponseStd<>(dataIntroducingService.saveOrUpdate(dataIntroducing));
        boolean res = dataIntroducingService.updateOne(dataIntroUserDTO);
        return  new ResponseStd<>(res);
    }

    @GetMapping("")
    public ResponseStd<List<DataIntroUserDTO>> getAll() {
        List<DataIntroUserDTO> dataIntroUserDTOList = dataIntroducingService.getAllDataIntroUserDTO();
        // 没有算法
        if (dataIntroUserDTOList.isEmpty()) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<List<DataIntroUserDTO>>(dataIntroUserDTOList);
    }

    // 补
    // 根据batchId获取某一个DataIntroDTO
    @GetMapping("/{batchId}")
    public ResponseStd<DataIntroUserDTO> selectAlgUserDTOById(@PathVariable Integer batchId) {
        return new ResponseStd<DataIntroUserDTO>(dataIntroducingService.getDataIntroUserDTOById(batchId));
    }

}

