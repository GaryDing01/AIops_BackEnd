package com.aiops_web.entity.sql;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="StepConfig对象", description="")
public class StepConfig implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "step_id", type = IdType.AUTO)
    private Integer stepId;

    private Integer typeId;

    private Integer stepNum;

    private String param;

    private Integer algId;

    private String wfId;


}
