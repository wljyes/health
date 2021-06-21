package com.example.health.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ResourceController {

    @GetMapping("user/signUp")
    public String userSignUp() {
        return "user/signUp";
    }
}
