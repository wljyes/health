package com.example.health.data;

import com.example.health.data.group.BasicAccountInfo;
import com.example.health.data.group.ChangePasswordInfo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AccountBean {

    @NotNull(groups = BasicAccountInfo.class)
    String username;

    @NotNull(groups = {BasicAccountInfo.class, ChangePasswordInfo.class})
    @Size(min = 4, max = 16, groups = {BasicAccountInfo.class, ChangePasswordInfo.class})
    String password;

    @NotNull(groups = ChangePasswordInfo.class)
    @Size(min = 4, max = 16, groups = ChangePasswordInfo.class)
    String newPassword;

    @NotNull(groups = BasicAccountInfo.class)
    int role;
}
