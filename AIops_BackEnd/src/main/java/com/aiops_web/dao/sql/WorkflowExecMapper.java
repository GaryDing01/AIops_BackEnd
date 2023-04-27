package com.aiops_web.dao.sql;

import com.aiops_web.dto.ExecStepDTO;
import com.aiops_web.entity.sql.WorkflowExec;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2023-04-12
 */
public interface WorkflowExecMapper extends BaseMapper<WorkflowExec> {
    // 查一个执行步骤
    ExecStepDTO selectOneExecStep(Integer wfId, Integer stepNum);

    // 根据wfId查找所有对应的执行步骤
    List<ExecStepDTO> selectExecStepByWf(Integer wfId);

    // 根据wfId查找所有对应的执行情况
    List<WorkflowExec> selectExecByWf(Integer wfId);
}
