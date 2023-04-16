package com.aiops_web.service.impl;

import com.aiops_web.entity.sql.StepConfig;
import com.aiops_web.dao.sql.StepConfigMapper;
import com.aiops_web.service.StepConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.annotation.Resources;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2023-04-12
 */
@Service
public class StepConfigServiceImpl extends ServiceImpl<StepConfigMapper, StepConfig> implements StepConfigService {
    @Resource
    private StepConfigMapper stepConfigMapper;

    public Integer saveSteps(StepConfig stepConfig) {
        stepConfigMapper.insert(stepConfig);
        return stepConfig.getStepId();
    }
}
