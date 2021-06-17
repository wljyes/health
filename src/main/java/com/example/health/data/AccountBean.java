package com.example.health.data;

import lombok.Data;

@Data
public class AccountBean {
    String username;
    String password;
    String newPassword;
    int role;
}
