package com.example.health.service;

import com.example.health.data.DoctorBean;
import com.example.health.entity.Doctor;
import com.example.health.exception.DoctorNotFoundException;
import com.example.health.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public List<Doctor> getAllDoctorsByDepartmentId(int departmentId) {
        return doctorRepository.findAllByDepartmentId(departmentId);
    }

    public Doctor getDoctorById(int id) {
        return doctorRepository.findById(id).orElseThrow(DoctorNotFoundException::new);
    }

    public void update(DoctorBean doctorBean, Doctor currentDoctor) {
        currentDoctor.setName(doctorBean.getName());
        currentDoctor.setAge(doctorBean.getAge());
        currentDoctor.setSex(doctorBean.getSex());
        currentDoctor.setTel(doctorBean.getTel());
        currentDoctor.setProfile(doctorBean.getProfile());
        currentDoctor.setDepartmentId(doctorBean.getDepartmentId());

        doctorRepository.save(currentDoctor);
    }
}
