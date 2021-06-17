package com.example.health.controller;

import com.example.health.data.ApiResult;
import com.example.health.data.UserBean;
import com.example.health.entity.User;
import com.example.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(path = "user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path = "getCurrentUser")
    public ApiResult<User> getCurrentUser(HttpSession session) {
        return userService.getCurrentUser(session);
    }

    @GetMapping(path = "getUser/{id}")
    public ApiResult<User> getUserById(@PathVariable("id") int id) {
        return userService.getUserById(id);
    }

    @PostMapping(path = "update")
    public ApiResult<String> updateUser(@Validated UserBean userBean,
                                        HttpSession session) {
        return userService.update(userBean, session);
    }

}
