package com.uitm.myattend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/submission")
public class SubmissionController {

    @GetMapping("")
    public String submissionMgt(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        return "Student/submissions";
    }

}
