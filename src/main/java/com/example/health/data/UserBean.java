package com.example.health.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserBean {

    @NotBlank
    private String name;

    @NotNull
    private Integer age;

    @NotBlank
    private String sex;

    @Size(min = 11, max = 11)
    private String tel;

}
