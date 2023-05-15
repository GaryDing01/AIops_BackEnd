package com.aiops_web.service.mysql;

import com.aiops_web.dto.RoleEnumDTO;
import com.aiops_web.entity.mysql.RoleEnum;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface RoleEnumService extends IService<RoleEnum> {

    // RoleEnumDTO相关

    // 增加一个RoleEnum
    Integer createRoleType(RoleEnumDTO roleEnumDTO);

    // 更新一个RoleEnum
    Boolean updateRoleType(RoleEnumDTO roleEnumDTO);

    // 获取所有RoleEnumDTO
    List<RoleEnumDTO> selectRoleTypes();

    // 根据Id获取RoleEnumDTO
    RoleEnumDTO selectRoleTypeById(Integer roleId);
}
