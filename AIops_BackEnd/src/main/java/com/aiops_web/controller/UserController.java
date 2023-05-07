package com.aiops_web.controller;


import com.aiops_web.dto.UserPermissionDTO;
import com.aiops_web.service.UserService;
import com.aiops_web.std.ErrorCode;
import com.aiops_web.std.JWTUtils;
import com.aiops_web.std.LoginState;
import com.aiops_web.std.ResponseStd;
import com.baomidou.mybatisplus.extension.api.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 
 * @since 2023-04-12
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Resource
    UserService userService;

    // 查询所有用户
    @GetMapping()
    public ResponseStd<List<UserPermissionDTO>> getAllUsers() {
        List<UserPermissionDTO> alg = userService.getAllUsers();
        // 没有user  (数据库问题)
        if (alg.isEmpty()) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<>(alg);
    }

    /**
     * 用户 登录  和 token状态查询
     */
    @PostMapping("/login")
    public ResponseStd<UserPermissionDTO> login(@RequestBody UserPermissionDTO userPermissionDTO) {
        UserPermissionDTO dto = userService.login(userPermissionDTO);
        // 根据dto的token的信息返回正确信息或者错误编码
        if (dto.getToken().equals("NoUser")) {
            return new ResponseStd<>(null, 40000, "NoUser");
        } else if (dto.getToken().equals("WrongPwd")) {
            return new ResponseStd<>(null, 40000, "WrongPwd");
        }

        return new ResponseStd<>(dto);
    }

    @GetMapping("/login")
    public ResponseStd<Boolean> checkToken(@RequestHeader("Authorization") String authorization) {
        LoginState loginState = JWTUtils.checkToken(authorization);
        if (loginState == LoginState.CHECKFAIL) {
            return new ResponseStd<>(false, 40000, "CheckFail");
        } else if (loginState == LoginState.EXPIRE) {
            return new ResponseStd<>(false, 40000, "TimeExpire");
        }

        return new ResponseStd<>(true);
    }


    // 根据userid查询用户信息
    @GetMapping("/{userId}")
    public ResponseStd<UserPermissionDTO> getUserById(@PathVariable Integer userId) {
        UserPermissionDTO userPermissionDTO = userService.getUserById(userId);
        if (userPermissionDTO == null)
            return new ResponseStd<>(ErrorCode.NULL_ERROR);
        return new ResponseStd<>(userPermissionDTO);
    }

    @GetMapping("/batch")
    public ResponseStd<List<UserPermissionDTO>> getUserBatch(@RequestParam List<Integer> ids) {
        List<UserPermissionDTO> lists = userService.getUserByIds(ids);
        if (lists == null)
            return new ResponseStd<>(ErrorCode.NULL_ERROR);
        return new ResponseStd<>(lists);
    }

    @PostMapping()
    public ResponseStd<Boolean> createUser(@RequestBody UserPermissionDTO user) {
        boolean res = userService.createUser(user);
        return new ResponseStd<>(res);
    }

    @DeleteMapping("/{userId}")
    public ResponseStd<Boolean> deleteUser(@PathVariable Long userId) {
        return new ResponseStd<>(userService.deleteUserById(userId));
    }

    @PutMapping("/{userId}/permissions")
    public ResponseStd<Boolean> updatePermissions(@PathVariable long userId, @RequestParam String permit_contents) {
        boolean res = userService.updatePermissions(userId, permit_contents);
        return new ResponseStd<>(res);
    }

    @PutMapping()
    public ResponseStd<Boolean> updateInfo(@RequestBody String info) throws JsonProcessingException {
        boolean res = userService.updateInfo(info);
        return new ResponseStd<>(res);
    }

    @PutMapping("/{userId}/pwds")
    public ResponseStd<Boolean> updatePwd(@PathVariable long userId, @RequestParam String password, @RequestParam String oldPassword) {
        boolean res = userService.updatePwd(userId, password, oldPassword);
        return new ResponseStd<>(res);
    }
}

