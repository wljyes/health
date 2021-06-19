package com.example.health.controller;

import com.example.health.data.ApiResult;
import com.example.health.data.ReportBean;
import com.example.health.entity.Doctor;
import com.example.health.entity.Report;
import com.example.health.repository.ReportRepository;
import com.example.health.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class ReportController {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    ReservationService reservationService;

    @GetMapping(path = "reservations/{rId}/report")
    public ApiResult<Report> getReportOfReservation(@PathVariable("rId") int reservationId) {
        Report report = reportRepository.findByReservationId(reservationId);
        return ApiResult.success(report);
    }

    @GetMapping(path = "doctors/{doctorId}/reports")
    public ApiResult<List<Report>> getReportsOfDoctor(@PathVariable("doctorId") int doctorId) {
        List<Report> reports = reportRepository.findAllByDoctorId(doctorId);
        return ApiResult.success(reports);
    }

    @PostMapping(path = "reservation/{rId}/report")
    public ApiResult<Report> addReport(@PathVariable("rId") int reservationId,
                                       @Validated ReportBean reportBean,
                                       @SessionAttribute("doctor") Doctor doctor) {
        Report report = new Report();
        reportBean.fillReport(report);
        report.setDate(new Timestamp(new Date().getTime()));
        report.setDoctorId(doctor.getId());
        report.setDoctorName(doctor.getName());
        report = reportRepository.save(report);

        reservationService.doneReservation(reservationId);

        return ApiResult.success(report);
    }



}
