package com.aiops_web.service.impl;

import com.aiops_web.dao.sql.RoleEnumMapper;
import com.aiops_web.entity.sql.RoleEnum;
import com.aiops_web.service.RoleEnumService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RoleEnumServiceImpl extends ServiceImpl<RoleEnumMapper, RoleEnum> implements RoleEnumService {
}
