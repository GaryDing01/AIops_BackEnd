package com.aiops_web.entity.sql;

import com.baomidou.mybatisplus.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
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
public class AnomalyInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ano_id", type = IdType.AUTO)
    private Integer anoId;

    private Integer objId;

    private Integer statusId;

    private Integer unitnodeTypeId;

    private String unitnodeName;

    private Timestamp detectTstamp;

    @TableField(updateStrategy = FieldStrategy.IGNORED) // 为将来填写预期出现故障时间打算
    private Timestamp predictTstamp;

    private Timestamp updateTstamp;

    private String sourceDataIds;

    private Integer userId;

    private Integer wfId;

    @TableLogic
    private Integer deleted;


}
