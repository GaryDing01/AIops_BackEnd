package com.aiops_web.dto;

import com.aiops_web.entity.sql.AiopsAlg;
import com.aiops_web.entity.sql.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AlgUser extends AiopsAlg {
    private User user;
}
