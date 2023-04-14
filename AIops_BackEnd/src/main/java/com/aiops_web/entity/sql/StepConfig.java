package com.aiops_web.entity.sql;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Map;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2023-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StepConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "step_id", type = IdType.AUTO)
    private Integer stepId;

    private Integer typeId;

    private Integer stepNum;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> param;

    private Integer algId;

    private Integer wfId;


}
