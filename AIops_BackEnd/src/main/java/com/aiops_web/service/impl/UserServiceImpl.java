package com.aiops_web.service.impl;

import com.aiops_web.entity.sql.User;
import com.aiops_web.dao.sql.UserMapper;
import com.aiops_web.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
