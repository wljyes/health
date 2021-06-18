package com.example.health.controller;

import com.example.health.data.AccountBean;
import com.example.health.data.ApiResult;
import com.example.health.data.UserBean;
import com.example.health.data.group.BasicAccountInfo;
import com.example.health.entity.Account;
import com.example.health.entity.User;
import com.example.health.service.AccountService;
import com.example.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@RestController
@RequestMapping(path = "user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path = "getCurrentUser")
    public ApiResult<User> getCurrentUser(@SessionAttribute(value = "user") User currentUser) {
        return ApiResult.success(currentUser);
    }

    @GetMapping(path = "{id}")
    public ApiResult<User> getUserById(@PathVariable("id") int id) {
        User user = userService.getUserById(id);
        return ApiResult.success(user);
    }

    @PostMapping(path = "update")
    public ApiResult<User> updateUser(@Validated UserBean userBean,
                                        @SessionAttribute("user") User user) {
        userService.update(userBean, user);
        return ApiResult.success(user);
    }

//    @ExceptionHandler(HttpSessionRequiredException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ResponseBody
//    ApiResult<String> handleNoUserError(HttpSessionRequiredException ex) {
//        if (Objects.equals(ex.getExpectedAttribute(), "user")) {
//            return ApiResult.fail("Need user login");
//        } else {
//            return ApiResult.fail("Unknown Error");
//        }
//    }

}
