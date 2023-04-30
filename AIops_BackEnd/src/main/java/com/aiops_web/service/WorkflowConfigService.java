package com.aiops_web.service;

import com.aiops_web.dto.TemplateDTO;
import com.aiops_web.entity.sql.StepConfig;
import com.aiops_web.entity.sql.WorkflowConfig;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2023-04-12
 */
public interface WorkflowConfigService extends IService<WorkflowConfig> {
    // 新增流程
    Integer saveWorkflowsByUser(Integer userId, String name);

    // 查看已经结束的流程
    List<WorkflowConfig> getEndedWorkflows();

    // 将流程保存为模板(新增模板)
    Integer saveTemplates(Integer wfId);

    // 查看所有模板
    List<TemplateDTO> getAllTemplates();

    // 根据id查看一个模板
    TemplateDTO getOneTemplate(Integer wfId);

    // 根据模板创建流程
    Integer saveWfByT(Integer wfId);

    // 根据已知模板修改流程
    Boolean updateWfByT(Integer wfId_origin, TemplateDTO templateDTO);

    // 根据模板抽出步骤编号
    List<Integer> getSIdByT(TemplateDTO templateDTO);

    // 根据要覆盖的模板编号批量删除步骤
    Boolean removeStepsByT(Integer wfId);

    //  根据原始模板批量增加要覆盖的模板的步骤
    Boolean saveStepsByT(Integer wfId, TemplateDTO templateDTO);
}
