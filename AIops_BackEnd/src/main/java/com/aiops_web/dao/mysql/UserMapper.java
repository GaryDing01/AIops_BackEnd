package com.aiops_web.dao.mysql;

import com.aiops_web.entity.mysql.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author
 * @since 2023-04-12
 */
public interface UserMapper extends BaseMapper<User> {

    List<User> getAllUsers();

    User getUserById(int userId);

    List<User> getUsersByIds(@Param("ids")List<Integer> ids);

    int createUser(User user);

    String getInitPermits(int roleId);

    int deleteUserById(long userId);

    String getPassword(long userId);

    int updatePermissions(long userId, String permissions);

    int updatePassword(long userId, String password);

    int updateInfo(User user);
}
