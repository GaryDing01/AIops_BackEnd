package com.aiops_web.service.impl;

import com.aiops_web.entity.sql.Permission;
import com.aiops_web.dao.sql.PermissionMapper;
import com.aiops_web.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2023-04-12
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

}
