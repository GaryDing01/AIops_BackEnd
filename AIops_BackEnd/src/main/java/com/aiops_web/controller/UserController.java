package com.aiops_web.controller;


import com.aiops_web.entity.sql.User;
import com.aiops_web.service.UserService;
import com.aiops_web.std.ResponseStd;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    @GetMapping("/findAll")
    public ResponseStd<List<User>> findAll() {
        return new ResponseStd(userService.list());
    }
}

