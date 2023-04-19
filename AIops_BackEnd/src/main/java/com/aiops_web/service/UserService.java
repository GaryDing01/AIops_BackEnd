package com.aiops_web.service;

import com.aiops_web.entity.sql.User;
import com.aiops_web.std.LoginState;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2023-04-12
 */
public interface UserService extends IService<User> {

    List<User> getAllUsers();

    boolean createUser(User user);

    boolean deleteUserById(long userId);

    LoginState checkPwd(long userId, String pwd);

    boolean updatePermissions(long userId, String permissions);

    boolean updatePwd(long userId, String pwd);

    boolean updateInfo(String info) throws JsonProcessingException;

}
