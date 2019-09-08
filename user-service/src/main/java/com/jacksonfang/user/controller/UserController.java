package com.jacksonfang.user.controller;

import com.jacksonfang.common.RestResponse;
import com.jacksonfang.user.model.User;
import com.jacksonfang.user.service.UserService;
import org.apache.ibatis.annotations.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Jackson Fang
 * Date:   2018/11/15
 * Time:   9:24
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // for api-gateway test
    @GetMapping("/getUsernameById")
    public RestResponse<String> getUsername(Long id) {
        logger.info("========>> Incoming Request");
        String username = userService.getUserById(id).getName();
        return RestResponse.success(username);
    }

    // ------------------------ 查询 ------------------------

    @GetMapping("/getById")
    public RestResponse<User> getUserById(Long id) {
        User user = userService.getUserById(id);
        return RestResponse.success(user);
    }


    @PostMapping("/getList")
    public RestResponse<List<User>> getUserList(@RequestBody User user) {
        List<User> users = userService.getUserByQuery(user);
        return RestResponse.success(users);
    }

    // ------------------------ 注册 ------------------------

    @PostMapping("/add")
    public RestResponse<User> add(@RequestBody User user) {
        userService.addAccount(user, user.getEnableUrl());
        return RestResponse.success();
    }

    @GetMapping("/enable")
    public RestResponse<User> enable(String key) {
        userService.enable(key);
        return RestResponse.success();
    }

    // ---------------------- 登录/鉴权 ----------------------

    @PostMapping("/auth")
    public RestResponse<User> auth(@RequestBody User user) {
        User finalUser = userService.auth(user.getEmail(), user.getPasswd());
        return RestResponse.success(finalUser);
    }

    @PostMapping("/get")
    public RestResponse<User> getUser(String token) {
        User finalUser = userService.getUserByToken(token);
        return RestResponse.success(finalUser);
    }

    @PostMapping("/logout")
    public RestResponse<Object> logout(String token) {
        userService.invalidate(token);
        return RestResponse.success();
    }

}
