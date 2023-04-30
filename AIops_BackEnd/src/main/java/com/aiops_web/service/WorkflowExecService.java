package com.aiops_web.service;

import com.aiops_web.dto.ExecStepDTO;
import com.aiops_web.entity.sql.WorkflowExec;
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
public interface WorkflowExecService extends IService<WorkflowExec> {

    // 简单包装的工具方法
    // 查一个执行步骤
    ExecStepDTO getOneExecStep(Integer wfId, Integer stepNum);

    // 单步执行流程
    Integer saveOneExec(Integer stepId, Integer inputTypeId, String inputId);

    // 根据步骤id（step_id ）获取到对应的执行的outputids
    String getOutputId(Integer stepId);

    // 结束流程
    Boolean closeWorkflow(Integer wfId);

    // 根据流程id获取流程对应的执行信息
    List<WorkflowExec> getExecsByWf(Integer wfId);

}
