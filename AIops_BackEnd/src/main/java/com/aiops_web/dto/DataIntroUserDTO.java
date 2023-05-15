package com.aiops_web.dto;

import com.aiops_web.entity.mysql.DataIntroducing;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DataIntroUserDTO extends DataIntroducing {
    private String userName;

    @Override
    public String toString() {
        return  "DataIntroducing" + super.toString() +
                "DataIntroUserDTO{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
