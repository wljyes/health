package com.example.health.data;

import com.example.health.exception.NoSuchRoleException;

public enum Role {
    User(1), Doctor(2);

    private final int code;

    Role(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Role of(int code) {
        switch (code) {
            case 1:
                return User;
            case 2:
                return Doctor;
            default:
                throw new NoSuchRoleException("未知的Role code : " + code);
        }
    }
}
