package com.aiops_web.controller;


import com.aiops_web.dto.UserPermissionDTO;
import com.aiops_web.entity.sql.User;
import com.aiops_web.service.UserService;
import com.aiops_web.std.ErrorCode;
import com.aiops_web.std.LoginState;
import com.aiops_web.std.ResponseStd;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.bouncycastle.asn1.DERTaggedObject;
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

    @GetMapping("/findAll")
    public ResponseStd<List<User>> findAll() {
        return new ResponseStd(userService.list());
    }

    // 查询所有用户
    @GetMapping()
    public ResponseStd<List<User>> getAllUsers() {
        List<User> alg = userService.getAllUsers();
        // 没有user  (数据库问题)
        if (alg.isEmpty()) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, null);
        }
        return new ResponseStd<>(alg);
    }

    // 查询用户状态
    @GetMapping("/{userId}/login")
    public ResponseStd<Boolean> login(@PathVariable long userId, @RequestParam String pwd) {
        LoginState loginState = userService.checkPwd(userId, pwd);
        if (loginState == LoginState.SUCCESS) {
            return new ResponseStd<>(true);  //登录成功
        }

        if (loginState == LoginState.NOUSER) {
            return new ResponseStd<>(ErrorCode.NULL_ERROR, false); // 用户不存在
        }

        return new ResponseStd<>(ErrorCode.PARAMS_ERROR, false);   // 密码错误
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
    public ResponseStd<Boolean> createUser(@RequestBody User user) {
        boolean res = userService.createUser(user);
        System.out.println();
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
    public ResponseStd<Boolean> updatePwd(@PathVariable long userId, @RequestParam String password) {
        boolean res = userService.updatePwd(userId, password);
        return new ResponseStd<>(res);
    }
}

