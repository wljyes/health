package com.example.health.controller;

import com.example.health.data.*;
import com.example.health.data.enums.Role;
import com.example.health.data.group.AdvanceProfileInfo;
import com.example.health.data.group.BasicAccountInfo;
import com.example.health.entity.Account;
import com.example.health.entity.Doctor;
import com.example.health.service.AccountService;
import com.example.health.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class DoctorController {

    @Autowired
    DoctorService doctorService;
    @Autowired
    AccountService accountService;

    @PostMapping(path = "doctor/signUp")
    public String signUp(@Validated(BasicAccountInfo.class) DoctorBean doctorBean,
                         Model model) {
        AccountBean accountBean = new AccountBean();
        accountBean.setUsername(doctorBean.getUsername());
        accountBean.setPassword(doctorBean.getPassword());
        accountBean.setRole(Role.Doctor.getCode());

        Account account = accountService.signUp(accountBean);
        doctorService.signUp(doctorBean, account);
        model.addAttribute("doctorName", doctorBean.getUsername());
        return "/doctor/login";
    }

    @PostMapping(path = "doctor/signIn")
    public String signIn(@Validated(BasicAccountInfo.class) DoctorBean doctorBean,
                                    HttpSession session,
                         Model model) {
        AccountBean accountBean = new AccountBean();
        accountBean.setUsername(doctorBean.getUsername());
        accountBean.setPassword(doctorBean.getPassword());
        accountBean.setRole(Role.Doctor.getCode());

        Account account = accountService.accountSignIn(accountBean);
        Doctor doctor = doctorService.getDoctorByAccountId(account.getId());

        session.setAttribute("role", Role.Doctor.getCode());
        session.setAttribute("doctor", doctor);
        model.addAttribute("doctor", doctor);
        return "doctor/index";
    }

    @GetMapping(path = "doctor/getCurrentDoctor")
    public ApiResult<Doctor> getCurrentDoctor(
            @SessionAttribute("doctor") Doctor doctor) {
        return ApiResult.success(doctor);
    }

    @GetMapping(path = "doctors/{id}")
    public ApiResult<Doctor> getDoctorById(@PathVariable("id") int id) {
        return ApiResult.success(doctorService.getDoctorById(id));
    }

    @GetMapping(path = "departments/{departmentId}/doctors")
    public ApiResult<List<Doctor>> getDoctorsOfDepartment(
            @PathVariable("departmentId") int departmentId) {
        List<Doctor> doctors = doctorService.getAllDoctorsByDepartmentId(departmentId);
        return ApiResult.success(doctors);
    }

    @PostMapping(path = "doctor/update")
    public ApiResult<Doctor> updateDoctor(@Validated(AdvanceProfileInfo.class) DoctorBean doctorBean,
                                          @SessionAttribute("doctor") Doctor doctor) {
        doctorService.update(doctorBean, doctor);
        return ApiResult.success(doctor);
    }
}
