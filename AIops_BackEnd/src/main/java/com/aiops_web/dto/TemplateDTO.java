package com.aiops_web.dto;

import com.aiops_web.entity.sql.StepConfig;
import com.aiops_web.entity.sql.WorkflowConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class TemplateDTO extends WorkflowConfig {
    private List<StepConfig> stepConfigList;
}
