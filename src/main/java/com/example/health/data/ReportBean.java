package com.example.health.data;

import com.example.health.entity.Report;
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

    public void fillReport(Report report) {
        report.setSymptom(symptom);
        report.setRecommend(recommend);
        report.setReservationId(reservationId);
    }
}
