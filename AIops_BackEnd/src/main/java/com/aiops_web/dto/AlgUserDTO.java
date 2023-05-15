package com.aiops_web.dto;

import com.aiops_web.entity.mysql.AiopsAlg;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AlgUserDTO extends AiopsAlg {
    private String userName;

    @Override
    public String toString() {
        return  "AiopsAlg" + super.toString() +
                " AlgUserDTO{" +
                "username='" + userName + '\'' +
                '}';
    }
}
