package com.example.health.data;

import com.example.health.entity.Reservation;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ReservationBean {

    @NotNull
    java.sql.Timestamp date;

    @NotBlank
    String period;

    @NotNull
    int departmentId;

    public void fillReservation(Reservation reservation) {
        reservation.setDate(date);
        reservation.setPeriod(period);
        reservation.setDepartmentId(departmentId);
    }
}
