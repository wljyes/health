package com.example.health.controller;

import com.example.health.data.AccountBean;
import com.example.health.data.ApiResult;
import com.example.health.data.enums.Role;
import com.example.health.data.UserBean;
import com.example.health.data.group.AdvanceProfileInfo;
import com.example.health.data.group.BasicAccountInfo;
import com.example.health.entity.Account;
import com.example.health.entity.User;
import com.example.health.service.AccountService;
import com.example.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    AccountService accountService;

    @PostMapping(path = "/user/signUp")
    @Transactional
    public String signUp(@Validated(BasicAccountInfo.class) UserBean userBean, Model model) {
        AccountBean accountBean = new AccountBean();
        accountBean.setUsername(userBean.getUsername());
        accountBean.setPassword(userBean.getPassword());
        accountBean.setRole(Role.User.getCode());

        Account account = accountService.signUp(accountBean);
        userService.signUp(userBean, account);
        model.addAttribute("username", userBean.getUsername());
        return "user/login";
    }

    @PostMapping(path = "user/signIn")
    public String signIn(@Validated(BasicAccountInfo.class) UserBean userBean,
                HttpSession session, Model model) {
        AccountBean accountBean = new AccountBean();
        accountBean.setUsername(userBean.getUsername());
        accountBean.setPassword(userBean.getPassword());
        accountBean.setRole(Role.User.getCode());

        Account account = accountService.accountSignIn(accountBean);
        User user = userService.getUserByAccountId(account.getId());

        session.setAttribute("role", Role.User.getCode());
        session.setAttribute("user", user);

        model.addAttribute("user", user);

        return "redirect:user/index";
    }

    @GetMapping(path = "user/getCurrentUser")
    public ApiResult<User> getCurrentUser(@SessionAttribute(value = "user") User currentUser) {
        return ApiResult.success(currentUser);
    }

    @GetMapping(path = "users/{id}")
    public ApiResult<User> getUserById(@PathVariable("id") int id) {
        User user = userService.getUserById(id);
        return ApiResult.success(user);
    }

    @PostMapping(path = "user/update")
    public String updateUser(@Validated(AdvanceProfileInfo.class) UserBean userBean,
                                        @SessionAttribute("user") User user,
                             Model model) {
        userService.update(userBean, user);
        model.addAttribute("user", user);
        return "user/profile";
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
