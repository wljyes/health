package com.example.health.controller;

import com.example.health.data.ApiResult;
import com.example.health.data.ReservationBean;
import com.example.health.entity.Department;
import com.example.health.entity.Reservation;
import com.example.health.entity.User;
import com.example.health.exception.AlreadyChangedException;
import com.example.health.exception.IllegalReservationException;
import com.example.health.exception.UnableReserveException;
import com.example.health.repository.DepartmentRepository;
import com.example.health.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class ReservationController {

    @Autowired
    ReservationService reservationService;
    @Autowired
    DepartmentRepository departmentRepository;

    @GetMapping(path = "departments/{id}/reservation")
    public ApiResult<List<Reservation>> getAllReservationOfDepartment(@PathVariable("id") int id) {
        List<Reservation> reservations = reservationService.getAllByDepartmentId(id);
        return ApiResult.success(reservations);
    }

    @GetMapping(path = "departments/{id}/reservation/{status}")
    public ApiResult<List<Reservation>> getAllReservationOfDepartment(@PathVariable("id") int id,
                                                                      @PathVariable("status") int status) {
        List<Reservation> reservations = reservationService.getAllByDepartmentIdAndStatus(id, status);
        return ApiResult.success(reservations);
    }

    @GetMapping(path = "user/reservations")
    public String getAllReservationOfUser(@SessionAttribute("user") User user, Model model) {
        List<Reservation> reservations = reservationService.getAllReservationByUserId(user.getId());
        model.addAttribute("reservations", reservations);
        return "user/reservations";
    }

    @GetMapping(path = "user/reservations/{status}")
    public ApiResult<List<Reservation>> getAllReservationOfUser(@SessionAttribute("user") User user,
                                                                @PathVariable("status") int status) {
        List<Reservation> reservations = reservationService.getAllByUserIdAndStatus(user.getId(), status);
        return ApiResult.success(reservations);
    }

    @PostMapping(path = "reservations/create")
    public String create(@Validated ReservationBean reservationBean,
                                         @SessionAttribute("user") User user,
                         Model model) {
        Department department = departmentRepository.getById(reservationBean.getDepartmentId());
        if (reservationService.getReservationCount(reservationBean) >= department.getLimitPerPeriod()) {
            throw new UnableReserveException("预约达到上限");
        }

        Reservation reservation = reservationService.createReservation(reservationBean, user);
        return "redirect:user/reservations";
    }

    @PutMapping(path = "reservations/{id}/cancel")
    public ApiResult<String> cancel(@PathVariable("id") int reservationId, @SessionAttribute("user") User user) {
        reservationService.cancelReservation(reservationId, user.getId());
        return ApiResult.success();
    }

    @PostMapping(path = "reservations/{id}/change")
    public ApiResult<Reservation> change(@PathVariable("id") int reservationId,
                                         @Validated ReservationBean reservationBean,
                                         @SessionAttribute("user") User user) {
        Reservation reservation = reservationService.changeReservation(reservationId, reservationBean, user.getId());
        return ApiResult.success(reservation);
    }

    @ExceptionHandler(AlreadyChangedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ApiResult<String> handleAlreadyChangedException() {
        return ApiResult.fail("只能修改一次");
    }

    @ExceptionHandler(UnableReserveException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ApiResult<String> handleUnableReservationException(UnableReserveException ex) {
        return ApiResult.fail(ex.getMessage());
    }

    @ExceptionHandler(IllegalReservationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ApiResult<String> handleUnableReservationException() {
        return ApiResult.fail();
    }
}
