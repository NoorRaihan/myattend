package com.uitm.myattend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.uitm.myattend.model.AssignmentModel;
import com.uitm.myattend.model.CommonModel;
import com.uitm.myattend.model.CourseModel;
import com.uitm.myattend.service.AssignmentService;
import com.uitm.myattend.service.AuthService;
import com.uitm.myattend.service.CourseService;
import com.uitm.myattend.service.SemesterSessionService;
import com.uitm.myattend.utility.FieldUtility;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/submission")
public class SubmissionController {

    private final CourseService courseService;
    private final AuthService authService;
    private final AssignmentService assignmentService;
    private final SemesterSessionService semesterSessionService;
    private final CommonModel commonModel;

    public SubmissionController(CourseService courseService,
        AuthService authService,
        AssignmentService assignmentService,
        SemesterSessionService semesterSessionService,
        CommonModel commonModel) {
        this.courseService = courseService;
        this.authService = authService;
        this.assignmentService = assignmentService;
        this.semesterSessionService = semesterSessionService;
        this.commonModel = commonModel;
    }

    @GetMapping("/{course_id}")
    public String submissionMgt(HttpServletRequest request, 
    HttpServletResponse response, 
    HttpSession session,
    @PathVariable("course_id") String courseId) {

        // return all aassignments with submissions as fk
        if(!authService.authenticate(session)) {
            return "redirect:" + request.getContextPath() + "/login";
        }

        if(!authService.authorize(session, FieldUtility.STUDENT_ROLE)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        // retrieve by course id from ma_course student ge using by student id
        List<AssignmentModel> assignmentList = assignmentService.retrieveByStudentCourse(courseId);
        request.setAttribute("assignments", assignmentList);
        request.setAttribute("totalAssignment", assignmentList.size());

        return "Student/submissions";
    }

}
