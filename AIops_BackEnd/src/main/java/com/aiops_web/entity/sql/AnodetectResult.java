package com.aiops_web.entity.sql;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AnodetectResult {

    private static final long serialVersionUID = 1L;

    @TableId(value = "adr_id", type = IdType.AUTO)
    private Long adrId;

    private String sourceDataSection;

    @TableLogic
    private Integer deleted;

}
