package com.uitm.myattend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CourseController {
    @GetMapping("/course")
    public String course() {
        return "Manage/courses";
    }

}
