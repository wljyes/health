package com.example.health.repository;

import com.example.health.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findAllByDepartmentId(int departmentId);

    List<Reservation> findAllByDepartmentIdAndStatus(int departmentId, int status);

    List<Reservation> findAllByUserId(int userId);

    List<Reservation> findAllByUserIdAndStatus(int userId, int status);

    int countByDepartmentIdAndDateAndPeriod(int departmentId, Timestamp date, String period);
}
