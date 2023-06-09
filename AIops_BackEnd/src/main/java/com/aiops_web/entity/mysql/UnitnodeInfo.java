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
public class UnitnodeInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "unit_id", type = IdType.AUTO)
    private Integer unitId;

    private Integer typeId;

    private Date tstamp;

    private String content;


}
