package com.aiops_web.controller;

import com.aiops_web.dao.sql.AnomalyInfoMapper;
import com.aiops_web.entity.sql.AnomalyInfo;
import com.aiops_web.entity.sql.WorkflowExec;
import com.aiops_web.service.AnomalyInfoService;
import com.aiops_web.std.ErrorCode;
import com.aiops_web.std.ResponseStd;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/statistics")
public class AnomalyStatisticsController {

    @Resource
    AnomalyInfoService anomalyInfoService;

    // 返回故障统计数据
    @GetMapping("")
    public ResponseStd<List<AnomalyInfo>> getAnomalyStatistics(@RequestParam(required = false) String startTstamp, @RequestParam(required = false) String endTstamp,
                                                               @RequestParam(required = false) List<Integer> objIdList, @RequestParam(required = false) Integer unitnodeTypeId
                                                               ) {

        QueryWrapper<AnomalyInfo> wrapper = new QueryWrapper<>();

        // 收集查询的条件
        if (startTstamp != null) {
            long sTstamp = Long.parseLong(startTstamp);
            Date date = new Date(sTstamp);
            wrapper.ge("detect_tstamp", date);
        }
        if (endTstamp != null) {
            long eTstamp = Long.parseLong(endTstamp);
            Date date = new Date(eTstamp);
            wrapper.le("detect_tstamp", date);
        }
        if (objIdList != null) {
            wrapper.in("obj_id", objIdList);
        }
        if (unitnodeTypeId != null) {
            wrapper.eq("unitnode_type_id", unitnodeTypeId);
        }

        List<AnomalyInfo> anomalyInfoList = anomalyInfoService.list(wrapper);
        System.out.println(anomalyInfoList);
        if (anomalyInfoList == null) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR);
        }
        return new ResponseStd<List<AnomalyInfo>>(anomalyInfoList);
    }
}
