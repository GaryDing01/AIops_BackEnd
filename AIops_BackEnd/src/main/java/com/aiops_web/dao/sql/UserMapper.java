package com.aiops_web.dao.sql;

import com.aiops_web.entity.sql.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import scala.Int;

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

    int deleteUserById(long userId);

    String getPassword(long userId);

    int updatePermissions(long userId, String permissions);

    int updatePassword(long userId, String password);

    int updateInfo(User user);
}
