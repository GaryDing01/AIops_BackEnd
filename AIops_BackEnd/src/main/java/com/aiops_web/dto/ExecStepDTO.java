package com.aiops_web.dto;

import com.aiops_web.entity.sql.WorkflowExec;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExecStepDTO extends WorkflowExec {

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer typeId;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer stepNum;

    //    @TableField(typeHandler = JacksonTypeHandler.class)
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String param;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer algId;

    private Integer wfId;

    @Override
    public String toString() {
        return super.toString() +
                " ExecStepDTO{" +
                "typeId=" + typeId +
                ", stepNum=" + stepNum +
                ", param='" + param + '\'' +
                ", algId=" + algId +
                ", wfId=" + wfId +
                '}';
    }
}
