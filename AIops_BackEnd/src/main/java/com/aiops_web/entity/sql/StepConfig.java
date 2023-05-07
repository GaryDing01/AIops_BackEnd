package com.aiops_web.entity.sql;

import com.baomidou.mybatisplus.annotation.*;

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

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer typeId;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer stepNum;

//    @TableField(typeHandler = JacksonTypeHandler.class)
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String param;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer algId;

    private Integer wfId;

}
