package com.uitm.myattend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CourseRegController {
    @GetMapping("/courseReg")
    public String regCourse() {
        return "Student/courseReg";
    }

}
