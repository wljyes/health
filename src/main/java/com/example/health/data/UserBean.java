package com.example.health.data;

import com.example.health.data.group.BasicAccountInfo;
import com.example.health.data.group.ChangePasswordInfo;
import com.example.health.data.group.AdvanceProfileInfo;
import com.example.health.entity.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserBean {

    @NotBlank(groups = AdvanceProfileInfo.class)
    @NotNull(groups = AdvanceProfileInfo.class)
    private String name;

    @NotNull(groups = AdvanceProfileInfo.class)
    private Integer age;

    @NotBlank(groups = AdvanceProfileInfo.class)
    private String sex;

    @Size(min = 11, max = 11, groups = AdvanceProfileInfo.class)
    private String tel;

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


    public void fillUser(User user) {
        user.setName(name);
        user.setAge(age);
        user.setSex(sex);
        user.setTel(tel);
    }
}
