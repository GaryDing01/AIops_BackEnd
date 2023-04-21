package com.aiops_web.service.impl;

import com.aiops_web.dao.sql.AiopsObjEnumMapper;
import com.aiops_web.dao.sql.StepConfigMapper;
import com.aiops_web.dto.TemplateDTO;
import com.aiops_web.entity.sql.*;
import com.aiops_web.dao.sql.WorkflowConfigMapper;
import com.aiops_web.service.WorkflowConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
        int dataTypeId = aiopsObjEnumMapper.selectOne(wrapper).getObjId();

//        // 插入JSON数据
//        Map<String, Object> map = new LinkedHashMap<>();
//        map.put("dataType", dataType);
//        stepConfig.setParam(map);

        // 插入String数据，用数据类型代表param
        String param_1 = "[{\"dataTypeId\": " + dataTypeId + "}]";
        stepConfig.setParam(param_1);

        stepConfig.setAlgId(-1); //此时没有算法
        stepConfig.setWfId(wfId);

        stepConfigMapper.insert(stepConfig);

        return workflowConfig.getWfId();
    }

    @Override
    public Integer saveTemplates(Integer wfId) {
        // 流程复制
        WorkflowConfig workflowConfig = workflowConfigMapper.selectById(wfId);
        // name保留
        workflowConfig.setStatusId(3); // 状态已完成
        workflowConfig.setCurrentStep(-1);
        workflowConfig.setTemplate(1); // 设置为模板
        workflowConfig.setReportIds("");
        // user_id保留
        workflowConfigMapper.insert(workflowConfig);
        int wfId_new = workflowConfig.getWfId();

        // 步骤复制
        QueryWrapper<StepConfig> wrapper = new QueryWrapper<>();
        wrapper.eq("wf_id",wfId); // 一定是对应数据库里的字段
        List<StepConfig> steps = stepConfigMapper.selectList(wrapper);
        for (StepConfig stepConfig : steps) {
            stepConfig.setWfId(wfId_new); // 统一修改模板编号，其他不动，当然step_id保持自增
            stepConfigMapper.insert(stepConfig);
        }

        // 返回模板编号
        return wfId_new;
    }

    @Override
    public List<TemplateDTO> getAllTemplates() {
        List<TemplateDTO> templateDTOS = workflowConfigMapper.selectAllTemplates();
//        templateDTOS.forEach(System.out::println);
        return templateDTOS;
    }

    @Override
    public TemplateDTO getOneTemplate(Integer wfId) {
        TemplateDTO templateDTO = workflowConfigMapper.selectOneTemplate(wfId);
        return templateDTO;
    }

    @Override
    public Integer saveWfByT(Integer wfId) {
        // 先取出模板
        TemplateDTO templateDTO = workflowConfigMapper.selectOneTemplate(wfId);

        // 创建流程记录
        WorkflowConfig workflowConfig = new WorkflowConfig();
        workflowConfig.setName(templateDTO.getName());
        workflowConfig.setStatusId(1); // 初始都是1
        workflowConfig.setCurrentStep(-1);
        workflowConfig.setTemplate(0); // new一个新流程
        workflowConfig.setReportIds("");
        workflowConfig.setUserId(templateDTO.getUserId());

        workflowConfigMapper.insert(workflowConfig);
        int wfId_new = workflowConfig.getWfId();
        System.out.println("workflowConfig.getWfId()" + wfId_new);

        // 拷贝步骤
        for (StepConfig stepConfig : templateDTO.getStepConfigList()) {
            StepConfig sC = new StepConfig();
            sC.setTypeId(stepConfig.getTypeId());
            sC.setStepNum(stepConfig.getStepNum());
            sC.setParam(stepConfig.getParam());
            sC.setAlgId(stepConfig.getAlgId());
            sC.setWfId(wfId_new);
            stepConfigMapper.insert(sC);
        }

        // 返回新的流程id
        return wfId_new;
    }

    /**
     *
     * @param wfId 目标需要覆盖的模板
     * @param templateDTO 原始模板数据
     * @return 成功与否
     */
    @Override
    public Boolean updateWfByT(Integer wfId, TemplateDTO templateDTO) {
        WorkflowConfig workflowConfig = workflowConfigMapper.selectById(wfId);
        workflowConfig.setName(templateDTO.getName());
        workflowConfig.setStatusId(templateDTO.getStatusId());
        workflowConfig.setCurrentStep(-1); // 模板没有当前步骤
        workflowConfig.setTemplate(1); // 肯定是模板
        workflowConfig.setReportIds(""); // 模板没有报告
        int rows = workflowConfigMapper.updateById(workflowConfig);
        return rows > 0;
    }

    @Override
    public List<Integer> getSIdByT(TemplateDTO templateDTO) {
        List<StepConfig> stepConfigList = templateDTO.getStepConfigList();
        List<Integer> stepIdList = new ArrayList<>();
        for (StepConfig sC : stepConfigList) {
            stepIdList.add(sC.getStepId());
        }
        return stepIdList;
    }

    @Override
    public Boolean removeStepsByT(Integer wfId) {
        Map<String,Object> map = new HashMap<>();
        map.put("wf_id",wfId);
        int delete = stepConfigMapper.deleteByMap(map);
        System.out.println("delete: " + delete);
        return delete > 0;
    }

    @Override
    public Boolean saveStepsByT(Integer wfId, TemplateDTO templateDTO) {
        List<StepConfig> stepConfigList = templateDTO.getStepConfigList();
        for(StepConfig stepConfig : stepConfigList) {
            stepConfig.setWfId(wfId);
            int insert = stepConfigMapper.insert(stepConfig);
            System.out.println("insert: " + insert);
            if (insert < 0) return false;
        }
        return true;
    }
}
