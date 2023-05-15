package com.aiops_web.service.mysql.impl;

import com.aiops_web.entity.mysql.StepConfig;
import com.aiops_web.dao.mysql.StepConfigMapper;
import com.aiops_web.service.mysql.StepConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class StepConfigServiceImpl extends ServiceImpl<StepConfigMapper, StepConfig> implements StepConfigService {
    @Resource
    private StepConfigMapper stepConfigMapper;

    public Integer saveSteps(StepConfig stepConfig) {
        stepConfigMapper.insert(stepConfig);
        return stepConfig.getStepId();
    }

    @Override
    public List<StepConfig> getStepsByWf(Integer wfId) {
        QueryWrapper<StepConfig> wrapper = new QueryWrapper<>();
        wrapper.eq("wf_id",wfId);
        return stepConfigMapper.selectList(wrapper);
    }
}
