package com.aiops_web.service.impl;

import com.aiops_web.dao.sql.*;
import com.aiops_web.entity.sql.*;
import com.aiops_web.service.AnomalyInfoService;
import com.aiops_web.utils.Utils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
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

    Utils utils = new Utils();

    // Yuran
    @Override
    public List<AnomalyInfo> getAnomalyInfos(AnomalyInfo info, int pageNum, int pageSize) {
        pageNum = pageNum > 1? pageNum : 1;
        pageSize = pageSize > 0? pageSize : 5;   // 默认5
        // 参数类型 不用map了
//        Map<String, Object> map = new HashMap<>();
//        map.put("startIdx", (pageNum-1)*pageSize);
//        map.put("pageSize", pageSize);
//        map.put("info", info);
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
        if (workflowExec.getOutputTypeId() != 4) { // 本项目只涉及故障检测
            return false;
        }

        // 先准备该故障检测的基本信息
        QueryWrapper<AiopsObjEnum> wrapper_1 = new QueryWrapper<>();
        wrapper_1.eq("name", "log");
        int objId = aiopsObjEnumMapper.selectOne(wrapper_1).getObjId();

        QueryWrapper<AnomalyStatusEnum> wrapper_2 = new QueryWrapper<>();
        wrapper_2.eq("name", "未确认");
        int statusId = anomalyStatusEnumMapper.selectOne(wrapper_2).getStatusId();

        StepConfig stepConfig = stepConfigMapper.selectById(workflowExec.getStepId());
        WorkflowConfig workflowConfig = workflowConfigMapper.selectById(stepConfig.getWfId());

        long adrId = Long.parseLong(workflowExec.getOutputId());
        String[] temp = anodetectResultMapper.selectById(adrId).getSourceDataSections().split("\\|");
        int anoNum = temp.length; // 该次执行发现了多少个故障
        for (int i = 0; i < temp.length; i++) {

            AnomalyInfo anomalyInfo = new AnomalyInfo();

            // 先解析故障对应的源日志id
            String sourceDataIds = temp[i].replace("-", "|");

            // 填写故障信息记录的基本信息
            anomalyInfo.setObjId(objId);
            anomalyInfo.setStatusId(statusId);
            anomalyInfo.setSourceDataIds(sourceDataIds);
            anomalyInfo.setUserId(workflowConfig.getUserId());
            anomalyInfo.setWfId(workflowConfig.getWfId());
            anomalyInfo.setDeleted(0); // 没被删除
            Timestamp currentTimestamp = utils.getCurrentTstamp();
            anomalyInfo.setDetectTstamp(currentTimestamp);
            anomalyInfo.setUpdateTstamp(currentTimestamp);

            // 插入数据
            int insertResult = anomalyInfoMapper.insert(anomalyInfo);
            if (insertResult < 0) {
                return false;
            }
        }

        return true;
    }
}
