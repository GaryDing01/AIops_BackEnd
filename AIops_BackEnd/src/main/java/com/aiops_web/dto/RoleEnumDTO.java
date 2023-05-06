package com.aiops_web.dto;

import com.aiops_web.entity.sql.RoleEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class RoleEnumDTO extends RoleEnum implements Serializable {
    private List<Long> permissions;

    public RoleEnumDTO(RoleEnum roleEnum) {
        if (roleEnum == null) {
            return ;
        }
        this.roleId = roleEnum.getRoleId();
        this.name = roleEnum.getName();
        this.permissions = permissionToList(roleEnum.getPermitIds());
    }

    /***
     * 把数据库里的  string 形式的字符串转换成list
     * @param permitStr
     * @return
     */
    private List<Long> permissionToList(String permitStr) {
        List<Long> list = new ArrayList<>();
        String[] strArr = permitStr.split("[|]");
        for (int i = 0; i < strArr.length; i++) {
            if (strArr[i].equals("1")) {
                list.add(i + 1L);
            }
        }

        return list;
    }
}
