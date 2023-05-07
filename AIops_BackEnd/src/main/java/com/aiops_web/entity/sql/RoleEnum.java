package com.aiops_web.entity.sql;

<<<<<<< HEAD
=======
import com.aiops_web.dto.RoleEnumDTO;
>>>>>>> temp
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

<<<<<<< HEAD
=======
import java.util.List;

>>>>>>> temp
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleEnum {

    private static final long serialVersionUID = 1L;

    @TableId(value = "role_id", type = IdType.AUTO)
<<<<<<< HEAD
    private Integer roleId;

    private String name;

    private String permitIds;
=======
    protected Integer roleId;

    protected String name;

    private String permitIds;

    /***
     * 前端传过来的List<Long>改成String存到数据库中
     * @param permissions
     * @return
     */
    public String Listtopermission(List<Long> permissions, int permitNum) {
        StringBuilder permitIds = new StringBuilder("");
        for (long i = 0; i < permitNum; i++) {
            if (permissions.contains(i + 1)) {
                permitIds.append(1);
            }
            else {
                permitIds.append(0);
            }
            if (i != permitNum - 1) {
                permitIds.append("|");
            }
        }
        return permitIds.toString();
    }
>>>>>>> temp
}
