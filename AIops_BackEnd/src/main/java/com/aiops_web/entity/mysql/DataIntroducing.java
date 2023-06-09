package com.aiops_web.entity.mysql;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @since 2023-04-12
 */
@Data
@TableName(value = "data_introducing")
@EqualsAndHashCode(callSuper = false)
public class DataIntroducing implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "batch_id", type = IdType.AUTO)
    private Integer batchId;

    private Date tstamp;

    private String source;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String filePath;

    private String intro;

    private String name;

    @Column(name = "obj_id")
    private Integer objId;

    @Column(name = "data_num")
    private Long dataNum;

    @Column(name = "data_sample")
    private String dataSample;

    @Column(name = "user_id")
    private Integer userId;

    private String place;
}
