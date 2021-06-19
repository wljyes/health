package com.example.health.repository;

import com.example.health.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

    Report findByReservationId(int reservationId);

    List<Report> findAllByDoctorId(int doctorId);
}
