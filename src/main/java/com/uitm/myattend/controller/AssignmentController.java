package com.uitm.myattend.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uitm.myattend.model.CommonModel;
import com.uitm.myattend.model.CourseModel;
import com.uitm.myattend.service.CourseService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/assignment")
public class AssignmentController {

    private final CourseService courseService;

    public AssignmentController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("")
    public String assignmentMgt(HttpServletResponse response, HttpServletRequest request, HttpSession session) {
        CommonModel commonModel = (CommonModel) session.getAttribute("common");
        List<CourseModel> courseList = courseService.retrieveCourseByLecturer(commonModel.getUser().getId());
        request.setAttribute("courses", courseList);
        return "Lecturer/assignments";
    }

}
