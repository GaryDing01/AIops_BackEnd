package com.aiops_web.entity.sql;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
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
public class DataIntroducing implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "batch_id", type = IdType.AUTO)
    private Integer batchId;

    private Date tstamp;

    private String source;

    private String intro;

    private String name;

    private Integer objId;

    private Long dataNum;

    private String dataSample;

    private Integer userId;


}
