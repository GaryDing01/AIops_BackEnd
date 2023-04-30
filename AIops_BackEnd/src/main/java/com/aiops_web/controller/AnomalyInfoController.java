package com.aiops_web.controller;


import com.aiops_web.dto.ExecStepDTO;
import com.aiops_web.entity.sql.*;
import com.aiops_web.service.AnomalyInfoService;
import com.aiops_web.service.ReportService;
import com.aiops_web.service.WorkflowConfigService;
import com.aiops_web.service.WorkflowExecService;
import com.aiops_web.std.ErrorCode;
import com.aiops_web.std.ResponseStd;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
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

    @Resource
    AnomalyInfoService anomalyInfoService;

    @Resource
    WorkflowConfigService workflowConfigService;

    @Resource
    WorkflowExecService workflowExecService;

    @Resource
    ReportService reportService;

    // 分页查找
    @GetMapping("/page")
    public ResponseStd<List<AnomalyInfo>> getAnomalyInfos(@RequestParam int pageNum, @RequestParam int count,
                                                          @RequestParam(required = false) Integer ano_id, @RequestParam(required = false) Integer obj_id,
                                                          @RequestParam(required = false) Integer status_id, @RequestParam(required = false) Integer user_id,
                                                          @RequestParam(required = false) Integer wf_id, @RequestParam(required = false) Integer deleted,
                                                          @RequestParam(required = false) String source_data_id, @RequestParam(required = false) String detect_tstamp,
                                                          @RequestParam(required = false) String predict_tstamp, @RequestParam(required = false) String update_tstamp) {
        // 收集查询的条件
        AnomalyInfo info = new AnomalyInfo();
        info.setAnoId(ano_id);
        info.setObjId(obj_id);
        info.setStatusId(status_id);
        info.setUserId(user_id);
        info.setWfId(wf_id);
        info.setDeleted(deleted);
        info.setSourceDataIds(source_data_id);
        if (detect_tstamp != null) {
            info.setDetectTstamp(new Timestamp(Long.parseLong(detect_tstamp)));
        }
        if (predict_tstamp != null) {
            info.setPredictTstamp(new Timestamp(Long.parseLong(predict_tstamp)));
        }
        if (update_tstamp != null) {
            info.setUpdateTstamp(new Timestamp(Long.parseLong(update_tstamp)));
        }

        List<AnomalyInfo> infos = anomalyInfoService.getAnomalyInfos(info, pageNum, count);
        if (infos == null) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR);
        }
        return new ResponseStd<>(infos);
    }

    // 补, 获取所有故障信息
    @GetMapping("")
    public ResponseStd<List<AnomalyInfo>> selectAllAnoInfo() {
        List<AnomalyInfo> anomalyInfoList = anomalyInfoService.list();
        if (anomalyInfoList.isEmpty()) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<List<AnomalyInfo>>(anomalyInfoList);
    }

    // 更新故障状态
    @PutMapping("/updateStatus/{anoId}")
    public ResponseStd<AnomalyInfo> updateStatus(@PathVariable int anoId, @RequestParam int statusId) {
        AnomalyInfo info = anomalyInfoService.updateStatusById(anoId, statusId);
        if (info == null) {
            return new ResponseStd<>(ErrorCode.PARAMS_ERROR);
        }

        return new ResponseStd<>(info);
    }

    // 更新故障信息
    @PutMapping()
    public ResponseStd<Boolean>  updateAnoInfo(@RequestBody AnomalyInfo info) {
        System.out.println();
        boolean res = anomalyInfoService.updateInfo(info);
        if (res == false) {
            return new ResponseStd<>(ErrorCode.PARAMS_ERROR);
        }

        return new ResponseStd<>(res);
    }

    // 删除故障
    @DeleteMapping("/{anoId}")
    public ResponseStd<Boolean> deleteAnoInfoById(@PathVariable int anoId) {
        boolean res = anomalyInfoService.deleteByAnoId(anoId);
        return new ResponseStd<>(res);
    }

    // 获取每一步骤报告
    @GetMapping("/{anoId}/stepReport")
    public ResponseStd<Report> selectOneReport(@PathVariable int anoId, @RequestParam int stepNum) {
        // 先找对应故障
        AnomalyInfo anomalyInfo = anomalyInfoService.getById(anoId);
        if (anomalyInfo == null) {
            return new ResponseStd<>(ErrorCode.SYSTEM_ERROR, null);
        }
        // 根据接口可以直接找对应步骤执行
        ExecStepDTO execStepDTO = workflowExecService.getOneExecStep(anomalyInfo.getWfId(), stepNum);
        if (execStepDTO == null) {
            return new ResponseStd<>(ErrorCode.SYSTEM_ERROR, null);
        }
        // 最后找到报告
        Report report = reportService.getById(execStepDTO.getReportId());
        if (report == null) {
            return new ResponseStd<>(ErrorCode.SYSTEM_ERROR, null);
        }

        return new ResponseStd<Report>(report);
    }

}

