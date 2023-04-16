package com.aiops_web.dao.sql;

import com.aiops_web.dto.TemplateDTO;
import com.aiops_web.entity.sql.WorkflowConfig;
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
public interface WorkflowConfigMapper extends BaseMapper<WorkflowConfig> {
    // 查所有模板
    List<TemplateDTO> selectAllTemplates();

    // 查一个模板
    TemplateDTO selectOneTemplate(Integer wfId);
}
