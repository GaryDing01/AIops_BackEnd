package com.aiops_web.entity.sql;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
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
public class WorkflowConfig implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "wf_id", type = IdType.AUTO)
    private Integer wfId;

    private String name;

    private Integer statusId;

    private Integer currentStep;

    private Integer template;

    private String reportIds;

    private Integer userId;


}
