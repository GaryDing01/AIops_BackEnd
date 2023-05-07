package com.aiops_web.service.impl;

import com.aiops_web.dao.sql.*;
import com.aiops_web.dto.AnomalyInfoUserDTO;
import com.aiops_web.entity.elasticsearch.OriginalData;
import com.aiops_web.entity.sql.*;
import com.aiops_web.service.AnomalyInfoService;
import com.aiops_web.service.OriginalDataService;
import com.aiops_web.service.UnitnodeTypeEnumService;
import com.aiops_web.utils.Utils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2023-04-12
 */
@Service
public class AnomalyInfoServiceImpl extends ServiceImpl<AnomalyInfoMapper, AnomalyInfo> implements AnomalyInfoService {

    @Resource
    AnomalyInfoMapper anomalyInfoMapper;

    @Resource
    AiopsObjEnumMapper aiopsObjEnumMapper;

    @Resource
    AnomalyStatusEnumMapper anomalyStatusEnumMapper;

    @Resource
    StepConfigMapper stepConfigMapper;

    @Resource
    WorkflowConfigMapper workflowConfigMapper;

    @Resource
    AnodetectResultMapper anodetectResultMapper;

    @Resource
    UnitnodeTypeEnumMapper unitnodeTypeEnumMapper;

    @Resource
    OriginalDataService originalDataService;

    Utils utils = new Utils();

    // Yuran
    @Override
    public List<AnomalyInfoUserDTO> getAnomalyInfos(AnomalyInfo info, int pageNum, int pageSize) {
        pageNum = pageNum > 1? pageNum : 1;
        pageSize = pageSize > 0? pageSize : 5;   // 默认5
        return anomalyInfoMapper.getAnomalyInfos((pageNum-1)*pageSize, pageSize, info);
    }

    @Override
    public boolean deleteByAnoId(int anoId) {
        return anomalyInfoMapper.deleteByAnoId(anoId) > 0;
    }

    @Override
    public AnomalyInfo updateStatusById(int anoId, int statusId) {
        // 更改状态并且返回新的故障信息
        if (statusId <= 0 || statusId > 5)
            return null;
        if (anomalyInfoMapper.updateStatusById(anoId,statusId) > 0) {
            return anomalyInfoMapper.getById(anoId);
        }
        return null;
    }

    @Override
    public boolean updateInfo(AnomalyInfo info) {
        return anomalyInfoMapper.updateAnoInfo(info) > 0;
    }

    // 根据故障检测(本次只涉及故障检测)的执行结果(此时workflowExec信息应该完成)来保存故障信息
    @Override
    public boolean saveAnoInfoByExec(WorkflowExec workflowExec) {
        if (workflowExec.getOutputTypeId() != 5) { // 本项目只涉及故障检测
            return false;
        }

        // 先准备该故障检测的基本信息
        QueryWrapper<AiopsObjEnum> wrapper_1 = new QueryWrapper<>();
        wrapper_1.eq("name", "log");
        int objId = aiopsObjEnumMapper.selectOne(wrapper_1).getObjId();

        QueryWrapper<AnomalyStatusEnum> wrapper_2 = new QueryWrapper<>();
        wrapper_2.eq("name", "未确认");
        int statusId = anomalyStatusEnumMapper.selectOne(wrapper_2).getStatusId();

        QueryWrapper<UnitnodeTypeEnum> wrapper_3 = new QueryWrapper<>();
        wrapper_3.eq("name", "Service");
        int unitnodeTypeId = unitnodeTypeEnumMapper.selectOne(wrapper_3).getTypeId();

        StepConfig stepConfig = stepConfigMapper.selectById(workflowExec.getStepId());
        WorkflowConfig workflowConfig = workflowConfigMapper.selectById(stepConfig.getWfId());

//        long adrId = Long.parseLong(workflowExec.getOutputId());
//        String[] temp = anodetectResultMapper.selectById(adrId).getSourceDataSections().split("\\|");
//        int anoNum = temp.length; // 该次执行发现了多少个故障

        String[] anodetectOutputArray = workflowExec.getOutputId().split("\\|");
        if (anodetectOutputArray.length != 2) {
            return false;
        }
        long startId = Long.parseLong(anodetectOutputArray[0]);
        long endId = Long.parseLong(anodetectOutputArray[1]);
        QueryWrapper<AnodetectResult> wrapper_4 = new QueryWrapper<>();
        wrapper_4.between("adr_id", startId, endId);
        List<AnodetectResult> anodetectResultList = anodetectResultMapper.selectList(wrapper_4);

        int anoNum = anodetectResultList.size(); // 该次执行发现了多少个故障

        for (int i = 0; i < anoNum; i++) {

            AnomalyInfo anomalyInfo = new AnomalyInfo();

            // 先解析故障对应的源日志id
            String sourceDataId = anodetectResultList.get(i).getSourceDataSection();

            // 填写故障信息记录的基本信息
            anomalyInfo.setObjId(objId);
            anomalyInfo.setStatusId(statusId);
            anomalyInfo.setUnitnodeTypeId(unitnodeTypeId);
            anomalyInfo.setUnitnodeName("cart" + i);

            anomalyInfo.setSourceDataId(sourceDataId);
            String[] dataSampleArray = sourceDataId.split("-");
            if (dataSampleArray.length != 2) {
                return false;
            }
            long startId_dataSample = Long.parseLong(dataSampleArray[0]);
            long endId_dataSample = Long.parseLong(dataSampleArray[1]);
            String dataSample = genDataSample(startId_dataSample, endId_dataSample);
            anomalyInfo.setDataSample(dataSample);

            anomalyInfo.setDescription("序号为:" + sourceDataId + "的数据出现故障");
            anomalyInfo.setUserId(workflowConfig.getUserId());
            anomalyInfo.setWfId(workflowConfig.getWfId());
            anomalyInfo.setDeleted(0); // 没被删除
//            Timestamp currentTimestamp = utils.getCurrentTstamp();
            Date currentDate = new Date(System.currentTimeMillis());
            anomalyInfo.setDetectTstamp(currentDate);
            anomalyInfo.setUpdateTstamp(currentDate);

            // 插入数据
//            System.out.println("Anomaly Info: ");
//            System.out.println(anomalyInfo);
//            int insertResult = anomalyInfoMapper.insert(anomalyInfo);
//            if (insertResult < 0) {
//                return false;
//            }
        }

        return true;
    }

    public String genDataSample(long startId, long endId) {
        System.out.println("genDataSample(" + startId + ", " + endId + ")");
        List<OriginalData> originalDataList = originalDataService.getRange((int)startId, (int)endId);
        System.out.println(originalDataList.size());
        // 只取Sample
        if (originalDataList.size() > 5) {
            originalDataList = originalDataList.subList(0, 5);
        }
        // 封装List<String>
        List<String> originalLogStringList = new ArrayList<>();
        for (OriginalData originalData : originalDataList) {
            originalLogStringList.add(
                    "{\"OriginalDataId\": \"" + originalData.getCalcId()
                            + "\", \"BatchId\": \"" + originalData.getBatchId()
                            + "\", \"RelativeId\": \"" + originalData.getRelaId()
                            + "\", \"Content\": \"" + originalData.getContent()
                            + "\"}"
            );
        }
        System.out.println("originalLogStringList.size()" + originalLogStringList.size());
        System.out.println(originalLogStringList);
        return originalLogStringList.toString();
    }
}
