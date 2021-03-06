package com.example.health.controller;

import com.example.health.data.ApiResult;
import com.example.health.exception.AccountException;
import com.example.health.exception.NoSuchRoleException;
import com.example.health.exception.UnAuthException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(NoSuchRoleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<String> handleNoSuchRoleException(NoSuchRoleException ex) {
        return ApiResult.fail(ex.getMessage());
    }

    @ExceptionHandler(UnAuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ApiResult<String> handleUnAuthError(UnAuthException ex) {
        return ApiResult.fail(ex.getMessage());
    }

    @ExceptionHandler(AccountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResult<String> handleError(AccountException ex) {
        return ApiResult.fail(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiResult<String> handleAllException(Exception ex) {
        return ApiResult.fail(ex.getMessage());
    }
}
