package com.aiops_web.entity.sql;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
@ApiModel(value="AnomalyInfo对象", description="")
public class AnomalyInfo implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "ano_id", type = IdType.AUTO)
    private Integer anoId;

    private Integer objId;

    private Integer statusId;

    private Date detectTstamp;

    private Date predictTstamp;

    private Date updateTstamp;

    private String sourceDataIds;

    private Integer userId;

    private Integer wfId;

    @TableLogic
    private Integer deleted;


}
