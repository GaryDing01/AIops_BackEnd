package com.aiops_web.entity.mysql;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
public class WorkflowExec implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "exec_id", type = IdType.AUTO)
    private Integer execId;

    private Date tstamp;

    private Integer reportId;

    private Integer stepId;

    private Integer inputTypeId;

    private Integer outputTypeId;

    private String inputId;

    private String outputId;


}
