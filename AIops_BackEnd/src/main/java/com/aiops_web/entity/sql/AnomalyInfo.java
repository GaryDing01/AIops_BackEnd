package com.aiops_web.entity.sql;

import com.aiops_web.utils.Date2LongSerializer;
import com.baomidou.mybatisplus.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
public class AnomalyInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ano_id", type = IdType.AUTO)
    private Integer anoId;

    private Integer objId;

    private Integer statusId;

    private Integer unitnodeTypeId;

    private String unitnodeName;

    @JsonSerialize(using = Date2LongSerializer.class)//这里 using的类是我们自己创建的 用于将这里的时间转换成 long 毫秒精度
    private Date detectTstamp;

    @JsonSerialize(using = Date2LongSerializer.class)
    @TableField(updateStrategy = FieldStrategy.IGNORED) // 为将来填写预期出现故障时间打算
    private Date predictTstamp;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTstamp;

    private String description;

    private String sourceDataId;

    private String dataSample;

    private Integer userId;

    private Integer wfId;

    @TableLogic
    private Integer deleted;


}
