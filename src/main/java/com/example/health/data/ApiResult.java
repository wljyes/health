package com.example.health.data;

public class ApiResult<T> {
    int code = 200;
    String message = "OK";
    T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ApiResult() {
    }

    public ApiResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
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
        return new ApiResult<>(500,"fail!", data);
    }

    public static <T> ApiResult<T> fail() {
        return new ApiResult<>(500, "fail!", null);
    }
}
