package com.aiops_web.dto;

import com.aiops_web.entity.sql.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserPermissionDTO {
    private Integer userId;

    private String role;

    private String name;

    private List<Integer> permissions;

    public UserPermissionDTO(User user) {
        if (user == null)
            return;
        this.userId = user.getUserId();
//        this.role = user.getRole();
        this.name = user.getName();
        this.permissions = permissionToList(user.getPermitIds());
    }

    /***
     * 把数据库里的  string 形式的字符串转换成list
     * @param permitStr
     * @return
     */
    private List<Integer> permissionToList(String permitStr) {
        List<Integer> list = new ArrayList<>();
        String[] strArr = permitStr.split("[|]");
        for (int i = 0; i < strArr.length; i++) {
            if (strArr[i].equals("1")) {
                list.add(i+1);
            }
        }

        return list;
    }
}
