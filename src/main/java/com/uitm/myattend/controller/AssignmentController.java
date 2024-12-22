package com.uitm.myattend.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.uitm.myattend.model.CommonModel;
import com.uitm.myattend.model.CourseModel;
import com.uitm.myattend.model.AssignmentModel;
import com.uitm.myattend.service.AuthService;
import com.uitm.myattend.service.CourseService;
import com.uitm.myattend.service.AssignmentService;
import com.uitm.myattend.utility.FieldUtility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/assignment")
public class AssignmentController {

    private final CourseService courseService;
    private final AuthService authService;
    private final AssignmentService assignmentService;

    public AssignmentController(CourseService courseService, AuthService authService, AssignmentService assignmentService) {
        this.courseService = courseService;
        this.authService = authService;
        this.assignmentService = assignmentService;
    }

    @GetMapping("")
    public String assignmentMgt(HttpServletResponse response, HttpServletRequest request, HttpSession session) {

        if(!authService.authenticate(session)) {
            // response.sendRedirect(request.getContextPath() + "/login");
            // return null;
            return "redirect:" + request.getContextPath() + "/login";
        }
        if(!authService.authorize(session, FieldUtility.LECTURER_ROLE)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        CommonModel commonModel = (CommonModel) session.getAttribute("common");
        List<CourseModel> courseList = courseService.retrieveCourseByLecturer(commonModel.getUser().getId());
        request.setAttribute("courses", courseList);

        List<AssignmentModel> assignmentList = assignmentService.retrieveAll();
        request.setAttribute("assignments", assignmentList);
        request.setAttribute("totalAssignment", assignmentList.size());

        return "Lecturer/assignments";
    }

    //retrieve assignment by mark_by which lect



    //retrieve assignment by session_id


    //retrieve assignment by course_id



    //retrieve assignment by assignment_id



    //create assignment



    //update assignment - including all cases ( disable,bypass,etc .. )



    //delete assignment

    //required field for student
    private String[][] assignmentRequiredFields() {
        return new String[][] {
                {"program", "Program is required"},
                {"intake", "Intake is required"},
                {"semester", "Semester is required"}
        };
    }
}
