package com.example.health.controller;

import com.example.health.data.ApiResult;
import com.example.health.data.DoctorBean;
import com.example.health.entity.Doctor;
import com.example.health.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @GetMapping(path = "doctor/getCurrentDoctor")
    public ApiResult<Doctor> getCurrentDoctor(
            @SessionAttribute("doctor") Doctor doctor) {
        return ApiResult.success(doctor);
    }

    @GetMapping(path = "doctor/{id}")
    public ApiResult<Doctor> getDoctorById(@PathVariable("id") int id) {
        return ApiResult.success(doctorService.getDoctorById(id));
    }

    @GetMapping(path = "department/{departmentId}/doctors")
    public ApiResult<List<Doctor>> getDoctorsOfDepartment(
            @PathVariable("departmentId") int departmentId) {
        List<Doctor> doctors = doctorService.getAllDoctorsByDepartmentId(departmentId);
        return ApiResult.success(doctors);
    }

    @PostMapping(path = "update")
    public ApiResult<Doctor> updateDoctor(@Validated DoctorBean doctorBean,
                                          @SessionAttribute("doctor") Doctor doctor) {
        doctorService.update(doctorBean, doctor);
        return ApiResult.success(doctor);
    }
}
