package com.example.health.controller;

import com.example.health.data.AccountBean;
import com.example.health.data.ApiResult;
import com.example.health.data.group.BasicAccountInfo;
import com.example.health.data.group.ChangePasswordInfo;
import com.example.health.repository.AccountRepository;
import com.example.health.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping(path = "signIn")
    ApiResult<String> signIn(@Validated(BasicAccountInfo.class) AccountBean accountBean,
            HttpSession session) {
        return accountService.signIn(accountBean, session);
    }

    @PostMapping(path = "signUp")
    ApiResult<String> signUp(@Validated(BasicAccountInfo.class) AccountBean accountBean) {
        return accountService.signUp(accountBean);
    }

    @PostMapping(path = "changePassword")
    ApiResult<String> changePassword(@Validated(ChangePasswordInfo.class) AccountBean accountBean,
                                     HttpSession session) {
        return accountService.changePassword(accountBean, session);
    }

}
