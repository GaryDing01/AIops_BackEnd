package com.aiops_web.dto;

import com.aiops_web.entity.sql.AnomalyInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AnomalyInfoUserDTO extends AnomalyInfo {
    private String username;
}
