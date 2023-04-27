package com.aiops_web.service;

import com.aiops_web.entity.sql.StepConfig;
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
public interface StepConfigService extends IService<StepConfig> {

    Integer saveSteps(StepConfig stepConfig);

    // 根据流程id查看步骤
    List<StepConfig> getStepsByWf(Integer wfId);
}
