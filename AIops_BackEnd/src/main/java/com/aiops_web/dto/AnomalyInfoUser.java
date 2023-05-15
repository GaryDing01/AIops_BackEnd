package com.aiops_web.dto;

import com.aiops_web.entity.mysql.AnomalyInfo;
import com.aiops_web.entity.mysql.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AnomalyInfoUser extends AnomalyInfo {
    User user;
}
