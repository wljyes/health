package com.example.health.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("view")
public class ResourceController {

    @GetMapping("user/signUp")
    public String userSignUp() {
        return "/user/signUp";
    }

    @GetMapping("user/login")
    public String userLogin() {
        return "/user/login";
    }
}
