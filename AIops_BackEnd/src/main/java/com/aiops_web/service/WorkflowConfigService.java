package com.aiops_web.service;

import com.aiops_web.entity.sql.WorkflowConfig;
import com.baomidou.mybatisplus.extension.service.IService;

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
    Integer saveWorkflows(Integer userId, String name);
}
