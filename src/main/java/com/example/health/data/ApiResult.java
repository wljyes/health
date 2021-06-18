package com.example.health.data;

public class ApiResult<T> {
    int code = 200;
    String msg = "OK";
    T data;

    public ApiResult() {
    }

    public ApiResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> result = new ApiResult<>();
        result.data = data;
        return result;
    }

    public static <T> ApiResult<T> success() {
        return new ApiResult<>();
    }

    public static <T> ApiResult<T> fail(T data) {
        return new ApiResult<>(500, "fail!", data);
    }

    public static <T> ApiResult<T> fail() {
        return new ApiResult<>(500, "fail!", null);
    }
}
