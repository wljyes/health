package com.example.health.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DepartmentBean {

    @NotNull
    @NotBlank
    String name;

    @NotNull
    int limitPerPeriod;

}
