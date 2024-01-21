package com.uitm.myattend.controller;

import com.uitm.myattend.model.CourseModel;
import com.uitm.myattend.model.LecturerModel;
import com.uitm.myattend.service.CourseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    @GetMapping("")
    public String course(HttpServletRequest request, HttpServletResponse response) {
        List<CourseModel> courseList = courseService.retrieveAll();
        request.setAttribute("courses", courseList);
        request.setAttribute("totalCourse", courseList.size());
        return "Manage/courses";
    }

}
