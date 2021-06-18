package com.example.health.data;

import com.example.health.data.group.AdvanceProfileInfo;
import com.example.health.data.group.BasicAccountInfo;
import com.example.health.data.group.ChangePasswordInfo;
import com.example.health.entity.Doctor;
import com.example.health.entity.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class DoctorBean {

    @NotBlank(groups = AdvanceProfileInfo.class)
    String name;

    @NotNull(groups = AdvanceProfileInfo.class)
    Integer age;

    @NotBlank(groups = AdvanceProfileInfo.class)
    String sex;

    @Size(min = 11, max = 11, groups = AdvanceProfileInfo.class)
    String tel;

    @NotBlank(groups = AdvanceProfileInfo.class)
    String profile;

    @NotNull(groups = AdvanceProfileInfo.class)
    Integer departmentId;

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

    public void fillDoctor(Doctor doctor) {
        doctor.setName(name);
        doctor.setAge(age);
        doctor.setSex(sex);
        doctor.setTel(tel);
        doctor.setProfile(profile);
        doctor.setDepartmentId(departmentId);
    }
}
