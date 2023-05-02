package com.aiops_web.entity.sql;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class KnowledgegraphResult {

    private static final long serialVersionUID = 1L;

    @TableId(value = "kgr_id", type = IdType.AUTO)
    private Integer kgrId;

    private String sourceDataSection;

    private String nodeIds;

    private String relationIds;

    private String rootcauseIds;

    @TableLogic
    private Integer deleted;
}
