package com.aiops_web.service;

import com.aiops_web.dto.RoleEnumDTO;
import com.aiops_web.dto.UserPermissionDTO;
import com.aiops_web.entity.sql.User;
import com.aiops_web.std.LoginState;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import scala.Int;

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

    List<UserPermissionDTO> getAllUsers();

    UserPermissionDTO getUserById(int userId);

    List<UserPermissionDTO> getUserByIds(List<Integer> ids);

    Integer createUser(User user);

    boolean deleteUserById(long userId);

    boolean updatePermissions(long userId, String permissions);

    boolean updatePwd(long userId, String pwd, String oldPassword);

    boolean updateInfo(String info) throws JsonProcessingException;

    //  登录
    UserPermissionDTO login(UserPermissionDTO userPermissionDTO);

    // 新修改接口
    boolean updateInfo_new(UserPermissionDTO userPermissionDTO);

}
