package com.example.health.controller;

import com.example.health.data.ApiResult;
import com.example.health.data.DepartmentBean;
import com.example.health.entity.Department;
import com.example.health.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "department")
public class DepartController {

    @Autowired
    DepartmentRepository departmentRepository;

    @GetMapping(path = "")
    public ApiResult<List<Department>> getAllDepartments() {
        return ApiResult.success(departmentRepository.findAll());
    }

    @PostMapping(path = "add")
    public ApiResult<Department> add(@Validated DepartmentBean departmentBean) {
        Department department = new Department();
        department.setName(departmentBean.getName());
        department.setLimitPerPeriod(departmentBean.getLimitPerPeriod());
        department = departmentRepository.save(department);
        return ApiResult.success(department);
    }
}
