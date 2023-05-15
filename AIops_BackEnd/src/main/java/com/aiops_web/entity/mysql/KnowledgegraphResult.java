package com.aiops_web.entity.mysql;

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

    private String allNodeIds;

    private String allRelationIds;

    private String rootcauseNodeNames;

    private String rootcauseNodeIds;

    private String rootcauseRelationIds;

    @TableLogic
    private Integer deleted;
}
