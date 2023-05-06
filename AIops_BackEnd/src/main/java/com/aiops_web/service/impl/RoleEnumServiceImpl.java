package com.aiops_web.service.impl;

import com.aiops_web.dao.sql.RoleEnumMapper;
import com.aiops_web.dto.RoleEnumDTO;
import com.aiops_web.entity.sql.RoleEnum;
import com.aiops_web.service.RoleEnumService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleEnumServiceImpl extends ServiceImpl<RoleEnumMapper, RoleEnum> implements RoleEnumService {

    @Resource
    RoleEnumMapper roleEnumMapper;

    @Override
    public List<RoleEnumDTO> selectRoleTypes() {
        List<RoleEnum> roleEnumList = roleEnumMapper.selectList(null);
        List<RoleEnumDTO> roleEnumDTOList = new ArrayList<>();
        for (RoleEnum roleEnum : roleEnumList) {
            roleEnumDTOList.add(new RoleEnumDTO(roleEnum));
        }
        return roleEnumDTOList;
    }

    @Override
    public RoleEnumDTO selectRoleTypeById(Integer roleId) {
        RoleEnum roleEnum = roleEnumMapper.selectById(roleId);
        return new RoleEnumDTO(roleEnum);
    }

    @Override
    public Integer createRoleType(RoleEnumDTO roleEnumDTO) {
        RoleEnum roleEnum = new RoleEnum();
        roleEnum.setName(roleEnumDTO.getName());
        roleEnum.setPermitIds(roleEnum.Listtopermission(roleEnumDTO.getPermissions(), 8)); // 8表示子系统模块的数量
        System.out.println(roleEnum);
        int saveResult = roleEnumMapper.insert(roleEnum);
        if (saveResult < 1) {
            return 0;
        }
        return roleEnum.getRoleId();
    }

    @Override
    public Boolean updateRoleType(RoleEnumDTO roleEnumDTO) {
        RoleEnum roleEnum = new RoleEnum();
        roleEnum.setRoleId(roleEnumDTO.getRoleId());
        roleEnum.setName(roleEnumDTO.getName());
        roleEnum.setPermitIds(roleEnum.Listtopermission(roleEnumDTO.getPermissions(), 8)); // 8表示子系统模块的数量
        System.out.println(roleEnumDTO);
        int saveResult = roleEnumMapper.updateById(roleEnum);
        return saveResult >= 1;
    }
}
