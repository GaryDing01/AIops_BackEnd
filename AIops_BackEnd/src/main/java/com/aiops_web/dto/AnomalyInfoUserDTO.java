package com.aiops_web.dto;

import com.aiops_web.entity.mysql.AnomalyInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AnomalyInfoUserDTO extends AnomalyInfo {
    private String userName;
    private Long unitnodeId;  // unitnodeId对应的id
}
