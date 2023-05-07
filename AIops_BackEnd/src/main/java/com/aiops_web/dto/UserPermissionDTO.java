package com.aiops_web.dto;

import com.aiops_web.entity.sql.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserPermissionDTO extends User implements Serializable {
    private List<Long> permissions;

    private String token;



    public UserPermissionDTO(User user) {
        if (user == null)
            return;
        this.userId = user.getUserId();
        this.roleId = user.getRoleId();
        this.name = user.getName();
        this.permissions = permissionToList(user.getPermitIds());
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
