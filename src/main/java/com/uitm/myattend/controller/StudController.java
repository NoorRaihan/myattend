package com.uitm.myattend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Controller
public class StudController {

    @GetMapping("/studMan")
    public String studMan(HttpServletRequest request, HttpServletResponse response) {
        return "Manage/students";
    }

}
