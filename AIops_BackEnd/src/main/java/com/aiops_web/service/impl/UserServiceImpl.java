package com.aiops_web.service.impl;

import com.aiops_web.dto.UserPermissionDTO;
import com.aiops_web.entity.sql.User;
import com.aiops_web.dao.sql.UserMapper;
import com.aiops_web.service.UserService;
import com.aiops_web.utils.JWTUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aiops_web.std.LoginState;

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

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserPermissionDTO> getAllUsers() {
        List<UserPermissionDTO> userDTOs = new ArrayList<>();
        List<User> users = userMapper.getAllUsers();
        for ( User user : users) {
            userDTOs.add(new UserPermissionDTO(user));
        }
        return userDTOs;
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
    public Integer createUser(User user) {
//        if (!permissionVerify(user.getPermitIds()))
//            return false;
        //   创建的时候不需要permitIds  根据roleId 拉取初始化permits
        String permitIds = userMapper.getInitPermits(user.getRoleId());
        if (permitIds == null || permitIds.equals("")) {
            return 0;
        }

        user.setPermitIds(permitIds);
        int saveResult = userMapper.insert(user);
        if (saveResult == 0) {
            return 0;
        }

        return user.getUserId();
    }

    @Override
    public boolean deleteUserById(long userId) {
        return userMapper.deleteUserById(userId) > 0;
    }

    @Override
    public UserPermissionDTO login(UserPermissionDTO userPermissionDTO) {
        LoginState loginState = checkPwd(userPermissionDTO.getUserId(), userPermissionDTO.getPassword());

        UserPermissionDTO dto = new UserPermissionDTO();
        if (loginState == LoginState.SUCCESS) {
            dto = getUserById(userPermissionDTO.getUserId());
            String token = JWTUtils.createJWT(dto.getUserId(), dto.getRoleId());
            dto.setToken(token);
        } else if (loginState == LoginState.NOUSER){
            dto.setToken("NoUser");
        } else if (loginState == LoginState.WRONGPWD) {
            dto.setToken("WrongPwd");
        }
        return dto;
    }

    @Override
    public boolean updatePermissions(long userId, String permissions) {
        if (!permissionVerify(permissions)) {
            return false;
        }

        return userMapper.updatePermissions(userId,permissions) > 0;
    }

    @Override
    public boolean updatePwd(long userId, String pwd, String oldPassword) {
        String curPwd = userMapper.getPassword(userId);
        if (!curPwd.equals(oldPassword)) {
            return false;
        }
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
            // 这里更改role  同时把 role 的初始permits给这个user
            String permits = userMapper.getInitPermits(Integer.parseInt(content));
            if (permits == null || permits.equals("")) {
                return false;
            }

            user.setRoleId(Integer.parseInt(content));
            user.setPermitIds(permits);
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

    public LoginState checkPwd(long userId, String pwd) {
        String realPwd = userMapper.getPassword(userId);
        if (realPwd==null)
            return LoginState.NOUSER;
        return realPwd.equals(pwd)? LoginState.SUCCESS: LoginState.WRONGPWD;
    }

    @Override
    public boolean updateInfo_new(UserPermissionDTO userPermissionDTO) {
        User user = new User();
        user.setUserId(userPermissionDTO.getUserId());
        user.setRoleId(userPermissionDTO.getRoleId());
        user.setName(userPermissionDTO.getName());
        user.setPassword(userPermissionDTO.getPassword());
        user.setPermitIds(user.Listtopermission(userPermissionDTO.getPermissions(), 8)); // 8表示子系统模块的数量
        int updateResult = userMapper.updateById(user);
        return updateResult >= 1;
    }
}
