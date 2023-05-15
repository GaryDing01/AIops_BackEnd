package com.aiops_web.entity.mysql;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RootcauseResult {

    private static final long serialVersionUID = 1L;

    @TableId(value = "rcr_id", type = IdType.AUTO)
    private Integer rcrId;

    private String sourceDataSection;

    private String path;

    @TableLogic
    private Integer deleted;
}
