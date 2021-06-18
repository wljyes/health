package com.example.health.controller;

import com.example.health.data.AccountBean;
import com.example.health.data.ApiResult;
import com.example.health.data.Role;
import com.example.health.data.group.BasicAccountInfo;
import com.example.health.data.group.ChangePasswordInfo;
import com.example.health.entity.Doctor;
import com.example.health.entity.User;
import com.example.health.exception.AccountException;
import com.example.health.exception.UnAuthException;
import com.example.health.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping(path = "signIn")
    ApiResult<String> signIn(@Validated(BasicAccountInfo.class) AccountBean accountBean,
            HttpSession session) {
        Role role = Role.of(accountBean.getRole());
        switch (role) {
            case User:
                User user = accountService.userSignIn(accountBean);
                session.setAttribute("user", user);
                break;
            case Doctor:
                Doctor doctor = accountService.doctorSignIn(accountBean);
                session.setAttribute("doctor", doctor);
                break;
        }
        session.setAttribute("role", accountBean.getRole());
        return ApiResult.success();
    }

    @PostMapping(path = "signUp")
    ApiResult<String> signUp(@Validated(BasicAccountInfo.class) AccountBean accountBean) {
        accountService.signUp(accountBean);
        return ApiResult.success();
    }

    @PostMapping(path = "changePassword")
    ApiResult<String> changePassword(@Validated(ChangePasswordInfo.class) AccountBean accountBean,
                                     HttpSession session) {
        if (session.getAttribute("role") == null) {
            throw new UnAuthException("Need login");
        }

        Integer accountId = null;
        Role role = Role.of((Integer) session.getAttribute("role"));
        switch (role) {
            case User:
                accountId = ((User) session.getAttribute("user")).getAccountId();
                break;
            case Doctor:
                accountId = ((Doctor) session.getAttribute("doctor")).getAccountId();
                break;
        }
        accountService.changePassword(accountBean, accountId);
        return ApiResult.success();
    }

    @ExceptionHandler(UnAuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    ApiResult<String> handleUnAuthError(Exception ex) {
        return ApiResult.fail(ex.getLocalizedMessage());
    }

    @ExceptionHandler(AccountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ApiResult<String> handleError(Exception ex) {
        return ApiResult.fail(ex.getLocalizedMessage());
    }

}
