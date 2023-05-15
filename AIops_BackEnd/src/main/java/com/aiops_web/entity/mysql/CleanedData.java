package com.aiops_web.entity.mysql;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CleanedData {

    private static final long serialVersionUID = 1L;

    @TableId(value = "clean_id", type = IdType.AUTO)
    private Long cleanId;

    private String content;

    @TableLogic
    private Integer deleted;
}
