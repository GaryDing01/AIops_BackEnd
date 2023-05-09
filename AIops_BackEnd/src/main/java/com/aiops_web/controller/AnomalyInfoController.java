package com.aiops_web.controller;


import com.aiops_web.dto.AnomalyInfoUserDTO;
import com.aiops_web.dto.AnomalyInfoUserKGDTO;
import com.aiops_web.dto.ExecStepDTO;
import com.aiops_web.dto.RootCauseKGDTO;
import com.aiops_web.entity.sql.*;
import com.aiops_web.service.AnomalyInfoService;
import com.aiops_web.service.ReportService;
import com.aiops_web.service.WorkflowConfigService;
import com.aiops_web.service.WorkflowExecService;
import com.aiops_web.std.ErrorCode;
import com.aiops_web.std.ResponseStd;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    public ResponseStd<List<AnomalyInfoUserKGDTO>> getAnomalyInfos(@RequestParam int pageNum, @RequestParam int count, @RequestParam(required = false) String user_name,
                                                                   @RequestParam(required = false) Integer ano_id, @RequestParam(required = false) Integer obj_id,
                                                                   @RequestParam(required = false) Integer status_id, @RequestParam(required = false) Integer user_id,
                                                                   @RequestParam(required = false) Integer wf_id, @RequestParam(required = false) Integer deleted,
                                                                   @RequestParam(required = false) Integer unitnode_type_id, @RequestParam(required = false) String unitnode_name,
                                                                   @RequestParam(required = false) String source_data_id, @RequestParam(required = false) String detect_tstamp,
                                                                   @RequestParam(required = false) String predict_tstamp, @RequestParam(required = false) String update_tstamp) {
        // 收集查询的条件
        AnomalyInfoUserDTO info = new AnomalyInfoUserDTO();
        info.setUserName(user_name);
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
        if (infos.isEmpty()) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR);
        }

        // 再次封装成AnomalyInfoUserKGDTO
        List<AnomalyInfoUserKGDTO> anomalyInfoUserKGDTOList = new ArrayList<>();
        for (AnomalyInfoUserDTO anomalyInfoUserDTO : infos) {
            AnomalyInfoUserKGDTO anomalyInfoUserKGDTO = new AnomalyInfoUserKGDTO(anomalyInfoUserDTO, anomalyInfoService.checkAnoKG(anomalyInfoUserDTO.getAnoId()));
            anomalyInfoUserKGDTOList.add(anomalyInfoUserKGDTO);
        }

        return new ResponseStd<>(anomalyInfoUserKGDTOList);
    }

    // 补, 获取所有故障信息
    @GetMapping("")
    public ResponseStd<List<AnomalyInfoUserKGDTO>> selectAllAnoUserKGDTO() {
        List<AnomalyInfoUserKGDTO> anomalyInfoUserKGDTOList = anomalyInfoService.getAllAnomalyInfoUserDTO();
        if (anomalyInfoUserKGDTOList.isEmpty()) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<List<AnomalyInfoUserKGDTO>>(anomalyInfoUserKGDTOList);
    }

    // 根据id查找某一个故障
    @GetMapping("/{anoId}")
    public ResponseStd<AnomalyInfoUserKGDTO> selectAnoUserKGDTOById(@PathVariable Integer anoId) {
        return new ResponseStd<AnomalyInfoUserKGDTO>(anomalyInfoService.getAnomalyInfoUserDTOById(anoId));
    }

    // 更新故障状态
    @PutMapping("/updateStatus/{anoId}")
    public ResponseStd<Boolean> updateStatus(@PathVariable int anoId, @RequestBody JSONObject jsonObject) {
        int statusId = (Integer) jsonObject.get("statusId");
        AnomalyInfo info = anomalyInfoService.updateStatusById(anoId, statusId);
        if (info == null) {
            return new ResponseStd<>(ErrorCode.PARAMS_ERROR);
        }

        return new ResponseStd<>(true);
    }

    // 更新故障信息
    @PutMapping()
    public ResponseStd<Boolean> updateAnoInfo(@RequestBody AnomalyInfo info) {
//        System.out.println(info);
//        System.out.println();
        boolean res = anomalyInfoService.updateInfo(info);
        if (res == false) {
            return new ResponseStd<>(ErrorCode.PARAMS_ERROR);
        }

        return new ResponseStd<>(res);
    }

//    // 更新故障信息(后端自查)
//    @PostMapping("/autocheck")
//    public ResponseStd<Boolean>  updateAnoInfoAutoCheck(@RequestBody WorkflowExec workflowExec) {
//
//        boolean res = anomalyInfoService.saveAnoInfoByExec(workflowExec);
//        if (res == false) {
//            return new ResponseStd<>(ErrorCode.PARAMS_ERROR);
//        }
//
//        return new ResponseStd<>(res);
//    }

    // 后端自查
    // 该故障是否有对应的知识图谱
    @GetMapping("/{anoId}/checkKG")
    public ResponseStd<Integer> checkAnoKG(@PathVariable Integer anoId) {
        return new ResponseStd<Integer>(anomalyInfoService.checkAnoKG(anoId));
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
        // 故障中有Mock的非日志故障数据, 应先排除
        int wfId = anomalyInfo.getWfId();
        if (wfId <= 0 || stepNum <= 0) {
            return new ResponseStd<Report>(null, ErrorCode.PARAMS_ERROR.getCode(), "", "该故障不是日志故障, 无法查看报告. ");
        }
        // 以下应该是真实日志故障数据
        ExecStepDTO execStepDTO = workflowExecService.getOneExecStep(anomalyInfo.getWfId(), stepNum);
        if (execStepDTO == null) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        // 最后找到报告
        Report report = reportService.getById(execStepDTO.getReportId());
        if (report == null) {
            return new ResponseStd<>(ErrorCode.SYSTEM_ERROR, null);
        }

        return new ResponseStd<Report>(report);
    }

    // 获取故障对应的知识图谱
    @GetMapping("/{anoId}/kg")
    public ResponseStd<RootCauseKGDTO> selectKGByAno(@PathVariable Integer anoId) {
        return new ResponseStd<RootCauseKGDTO>(anomalyInfoService.getKGByAno(anoId));
    }

}

