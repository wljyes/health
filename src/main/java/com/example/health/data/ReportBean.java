package com.example.health.data;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ReportBean {

    @NotBlank
    String symptom;

    @NotBlank
    String recommend;

    @NotNull
    Integer reservationId;
}
