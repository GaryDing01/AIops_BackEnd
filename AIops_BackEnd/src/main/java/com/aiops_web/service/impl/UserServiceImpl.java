package com.aiops_web.service.impl;

import com.aiops_web.dto.UserPermissionDTO;
import com.aiops_web.entity.sql.User;
import com.aiops_web.dao.sql.UserMapper;
import com.aiops_web.service.UserService;
import com.aiops_web.std.ResponseStd;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.neo4j.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aiops_web.std.LoginState;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Resource
    private UserMapper userMapper;

    @Override
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    @Override
    public UserPermissionDTO getUserById(int userId) {
        User user = userMapper.getUserById(userId);
        if (user == null)
            return null;

        return new UserPermissionDTO(user);
    }

    @Override
    public List<UserPermissionDTO> getUserByIds(List<Integer> ids) {
        List<User> users = userMapper.getUsersByIds(ids);
        if (users.isEmpty())
            return null;

        // 如果查询到了数据
        List<UserPermissionDTO> userPermissionDTOList = new ArrayList<>();
        for (User user : users) {
            userPermissionDTOList.add(new UserPermissionDTO(user));
        }
        return userPermissionDTOList;
    }


    @Override
    public boolean createUser(User user) {
        if (!permissionVerify(user.getPermitIds()))
            return false;
        return userMapper.createUser(user) > 0;
    }

    @Override
    public boolean deleteUserById(long userId) {
        return userMapper.deleteUserById(userId) > 0;
    }

    @Override
    public LoginState checkPwd(long userId, String pwd) {
        String realPwd = userMapper.getPassword(userId);
        if (realPwd==null)
            return LoginState.NOUSER;
        return realPwd.equals(pwd)? LoginState.SUCCESS: LoginState.WRONGPWD;
    }

    @Override
    public boolean updatePermissions(long userId, String permissions) {
        if (!permissionVerify(permissions)) {
            return false;
        }

        return userMapper.updatePermissions(userId,permissions) > 0;
    }

    @Override
    public boolean updatePwd(long userId, String pwd) {
        return userMapper.updatePassword(userId, pwd) > 0;
    }

    @Override
    public boolean updateInfo(String info) throws JsonProcessingException {
        // 解析参数
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(info, Map.class);
        User user = new User();
        user.setUserId((Integer) map.get("userId"));

        String type = (String)map.get("update_type");
        String content = (String)map.get("update_content");
        if (type == null) {
            return false;
        }

        if (type.equals("role")) {
//            user.setRole(content);
        }
        if (type.equals("name")) {
            user.setName(content);
        }
        return userMapper.updateInfo(user) > 0;
    }


    private boolean permissionVerify(String permissions) {
        String[] permits = permissions.split("[|]");
        if (permits.length != 8) {
            return false;
        }

        for (String permit : permits) {
            if (!permit.equals("1") && !permit.equals("0"))
                return false;
        }

        return true;
    }
}
