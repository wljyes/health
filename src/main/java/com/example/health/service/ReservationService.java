package com.example.health.service;

import com.example.health.data.ReservationBean;
import com.example.health.data.enums.ReservationStatus;
import com.example.health.entity.Reservation;
import com.example.health.entity.User;
import com.example.health.exception.AlreadyChangedException;
import com.example.health.exception.IllegalReservationException;
import com.example.health.exception.UnableReserveException;
import com.example.health.repository.ReservationRepository;
import com.example.health.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    public List<Reservation> getAllByDepartmentIdAndStatus(int departmentId, int status) {
        return reservationRepository.findAllByDepartmentIdAndStatus(departmentId, status);
    }

    public List<Reservation> getAllByDepartmentId(int departmentId) {
        return reservationRepository.findAllByDepartmentId(departmentId);
    }

    public List<Reservation> getAllReservationByUserId(int uid) {
        return reservationRepository.findAllByUserId(uid);
    }

    public List<Reservation> getAllByUserIdAndStatus(int uid, int status) {
        return reservationRepository.findAllByUserIdAndStatus(uid, status);
    }

    public int getReservationCount(ReservationBean reservationBean) {
        return reservationRepository.countByDepartmentIdAndDateAndPeriod(
                reservationBean.getDepartmentId(),
                reservationBean.getDate(),
                reservationBean.getPeriod()
        );
    }

    public Reservation createReservation(ReservationBean reservationBean,
                                         User user) {

        checkReservationBean(reservationBean);

        Reservation reservation = new Reservation();
        reservationBean.fillReservation(reservation);
        reservation.setUserId(user.getId());
        reservation.setUserName(user.getName());
        reservation.setStatus(ReservationStatus.Reserved.getStatusCode());

        return reservationRepository.save(reservation);
    }

    public Reservation changeReservation(int rId, ReservationBean reservationBean, int userId) {

        checkReservationBean(reservationBean);

        Reservation reservation = reservationRepository.getById(rId);
        if (reservation.getUserId() != userId) {
            throw new IllegalReservationException();
        }
        if (reservation.getStatus() == ReservationStatus.Changed.getStatusCode()) {
            throw new AlreadyChangedException();
        }
        int departmentId = reservation.getDepartmentId();
        reservationBean.fillReservation(reservation);
        reservation.setDepartmentId(departmentId);
        reservation.setStatus(ReservationStatus.Changed.getStatusCode());
        return reservationRepository.save(reservation);
    }

    public void cancelReservation(int rId, int userId) {
        Reservation reservation = reservationRepository.getById(rId);
        if (reservation.getUserId() != userId) {
            throw new IllegalReservationException();
        }
        if (reservation.getStatus() == ReservationStatus.Done.getStatusCode()) {
            throw new IllegalReservationException();
        } else {
            reservation.setStatus(ReservationStatus.Canceled.getStatusCode());
        }
        reservationRepository.save(reservation);
    }

    public void doneReservation(int rId) {
        Reservation reservation = reservationRepository.getById(rId);

        if (reservation.getStatus() == ReservationStatus.Canceled.getStatusCode()) {
            throw new IllegalReservationException();
        }
        reservation.setStatus(ReservationStatus.Done.getStatusCode());
        reservationRepository.save(reservation);
    }

    void checkReservationBean(ReservationBean reservationBean) {
        Timestamp reservationDate = reservationBean.getDate();
        if (reservationDate.before(new Date())) {
            throw new UnableReserveException("预约失败");
        } else if (reservationDate.after(TimeUtil.getEndOfDayTimeFromNow(2).getTime())) {
            throw new UnableReserveException("预约失败");
        }

        List<String> periods = List.of("上午", "下午");
        if (!periods.contains(reservationBean.getPeriod())) {
            throw new UnableReserveException("预约失败");
        }
    }
}
