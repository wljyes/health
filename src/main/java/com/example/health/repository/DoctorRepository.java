package com.example.health.repository;

import com.example.health.entity.Doctor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DoctorRepository extends CrudRepository<Doctor, Integer> {

    Doctor findByAccountId(int accountId);

    List<Doctor> findAllByDepartmentId(int departmentId);
}
