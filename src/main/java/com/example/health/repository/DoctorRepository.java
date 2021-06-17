package com.example.health.repository;

import com.example.health.entity.Doctor;
import org.springframework.data.repository.CrudRepository;

public interface DoctorRepository extends CrudRepository<Doctor, Integer> {

    Doctor findByAccountId(int accountId);
}
