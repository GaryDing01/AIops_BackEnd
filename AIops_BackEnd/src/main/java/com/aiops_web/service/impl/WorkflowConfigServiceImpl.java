package com.aiops_web.service.impl;

import com.aiops_web.dao.sql.AiopsObjEnumMapper;
import com.aiops_web.dao.sql.StepConfigMapper;
import com.aiops_web.entity.sql.AiopsObjEnum;
import com.aiops_web.entity.sql.StepConfig;
import com.aiops_web.entity.sql.WorkflowConfig;
import com.aiops_web.dao.sql.WorkflowConfigMapper;
import com.aiops_web.service.WorkflowConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2023-04-12
 */
@Service
public class WorkflowConfigServiceImpl extends ServiceImpl<WorkflowConfigMapper, WorkflowConfig> implements WorkflowConfigService {

    @Resource
    private WorkflowConfigMapper workflowConfigMapper;

    @Resource
    private StepConfigMapper stepConfigMapper;

    @Resource
    private AiopsObjEnumMapper aiopsObjEnumMapper;

    // 新增流程
    @Override
    public Integer saveWorkflows(Integer userId, String name) {
        // 创建流程
        WorkflowConfig workflowConfig = new WorkflowConfig();
        workflowConfig.setName(name);
        workflowConfig.setStatusId(1);
        workflowConfig.setCurrentStep(-1);
        workflowConfig.setTemplate(0);
        workflowConfig.setReportIds("");
        workflowConfig.setUserId(userId);

        workflowConfigMapper.insert(workflowConfig);
        int wfId = workflowConfig.getWfId();
        System.out.println("workflowConfig.getWfId()" + workflowConfig.getWfId());

        // 创建第一个步骤 -- 源数据 (固定)
        StepConfig stepConfig = new StepConfig();
        stepConfig.setTypeId(1); // 固定编号1为最开始的源数据
        stepConfig.setStepNum(1);

        // 获取数据源对应编号
        QueryWrapper<AiopsObjEnum> wrapper = new QueryWrapper<>();
        wrapper.eq("name","log");
        int dataType = aiopsObjEnumMapper.selectOne(wrapper).getObjId();

        // 插入JSON数据
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("dataType", dataType);
        stepConfig.setParam(map);

        stepConfig.setAlgId(-1); //此时没有算法
        stepConfig.setWfId(wfId);

        stepConfigMapper.insert(stepConfig);

        return workflowConfig.getWfId();
    }
}
