package com.aiops_web.controller;


import com.aiops_web.dto.AnomalyInfoUserDTO;
import com.aiops_web.entity.sql.AnomalyInfo;
import com.aiops_web.service.AnomalyInfoService;
import com.aiops_web.std.ErrorCode;
import com.aiops_web.std.ResponseStd;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2023-04-12
 */
@RestController
@RequestMapping("/anomaly")
public class AnomalyInfoController {

    @Autowired
    private AnomalyInfoService anomalyInfoService;

    @GetMapping()
    public ResponseStd<List<AnomalyInfoUserDTO>> getAnomalyInfos(@RequestParam int pageNum, @RequestParam int count, @RequestParam(required = false) String username,
                                                                 @RequestParam(required = false) Integer ano_id, @RequestParam(required = false) Integer obj_id,
                                                                 @RequestParam(required = false) Integer status_id, @RequestParam(required = false) Integer user_id,
                                                                 @RequestParam(required = false) Integer wf_id, @RequestParam(required = false) Integer deleted,
                                                                 @RequestParam(required = false) Integer unitnode_type_id, @RequestParam(required = false) String unitnode_name,
                                                                 @RequestParam(required = false) String source_data_id, @RequestParam(required = false) String detect_tstamp,
                                                                 @RequestParam(required = false) String predict_tstamp, @RequestParam(required = false) String update_tstamp) {
        // 收集查询的条件
        AnomalyInfoUserDTO info = new AnomalyInfoUserDTO();
        info.setUsername(username);
        info.setAnoId(ano_id);
        info.setObjId(obj_id);
        info.setStatusId(status_id);
        info.setUnitnodeName(unitnode_name);
        info.setUnitnodeTypeId(unitnode_type_id);
        info.setUserId(user_id);
        info.setWfId(wf_id);
        info.setDeleted(deleted);
        info.setSourceDataId(source_data_id);
        if (detect_tstamp != null) {
            info.setDetectTstamp(new Date(Long.parseLong(detect_tstamp)));
        }
        if (predict_tstamp != null) {
            info.setPredictTstamp(new Date(Long.parseLong(predict_tstamp)));
        }
        if (update_tstamp != null) {
            info.setUpdateTstamp(new Date(Long.parseLong(update_tstamp)));
        }

        List<AnomalyInfoUserDTO> infos = anomalyInfoService.getAnomalyInfos(info, pageNum, count);
        if (infos == null) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR);
        }
        return new ResponseStd<>(infos);
    }

    @PutMapping("/updateStatus/{anoId}")
    public ResponseStd<Boolean> updateStatus(@PathVariable int anoId, @RequestBody JSONObject jsonObject) {
        int statusId = (Integer) jsonObject.get("statusId");
        AnomalyInfo info = anomalyInfoService.updateStatusById(anoId, statusId);
        if (info == null) {
            return new ResponseStd<>(ErrorCode.PARAMS_ERROR);
        }

        return new ResponseStd<>(true);
    }

    @PutMapping()
    public ResponseStd<Boolean>  updateAnoInfo(@RequestBody AnomalyInfoUserDTO info) {
        System.out.println();
        boolean res = anomalyInfoService.updateInfo(info);
        if (res == false) {
            return new ResponseStd<>(ErrorCode.PARAMS_ERROR);
        }

        return new ResponseStd<>(res);
    }

    @DeleteMapping("/{anoId}")
    public ResponseStd<Boolean> deleteAnoInfoById(@PathVariable int anoId) {
        boolean res = anomalyInfoService.deleteByAnoId(anoId);
        return new ResponseStd<>(res);
    }

}

