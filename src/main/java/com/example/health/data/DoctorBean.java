package com.example.health.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class DoctorBean {

    @NotBlank
    String name;

    @NotNull
    Integer age;

    @NotBlank
    String sex;

    @Size(min = 11, max = 11)
    String tel;

    @NotBlank
    String profile;

    @NotNull
    Integer departmentId;
}
