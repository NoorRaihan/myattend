package com.uitm.myattend.controller;

import com.uitm.myattend.model.*;
import com.uitm.myattend.service.AuthService;
import com.uitm.myattend.service.CourseService;
import com.uitm.myattend.service.StudentService;
import com.uitm.myattend.utility.FieldUtility;
import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Controller
@RequestMapping("/student")
public class StudController {

    private final StudentService studentService;
    private final CourseService courseService;
    private final AuthService authService;

    public StudController(StudentService studentService, CourseService courseService, AuthService authService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.authService = authService;
    }

    //handle index page for student
    @GetMapping("")
    public String index(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        if(!authService.authenticate(session)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return null;
        }
        if(!authService.authorize(session, FieldUtility.ADMIN_ROLE)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        List<StudentModel> studList = studentService.retrieveAll();
        request.setAttribute("students", studList);
        request.setAttribute("totalStudent", studList.size());
        return "Manage/students";
    }


    //API to retrieve student detail
    @GetMapping("/detail")
    @ResponseBody
    public Map<String, Object> show(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpServletRequest request, HttpSession session) {
        Map<String, Object> respMap = new HashMap<>();
        try {
            if(!authService.authenticate(session)) {
                response.sendRedirect(request.getContextPath() + "/login");
                return null;
            }

            int uid = Integer.parseInt((String)body.get("uid"));
            StudentModel student = studentService.retrieveDetail(uid);

            if(student == null) {
                respMap.put("respCode", "00001");
                respMap.put("respStatus", "error");
                respMap.put("respMessage", "Student does not found!");
            }else{
                respMap.put("respCode", "00000");
                respMap.put("respStatus", "success");
                respMap.put("respMessage", "successfully retrieved");
            }
            respMap.put("data", student);
        }catch (Exception e) {
            e.printStackTrace();
            //session.setAttribute("message", "Internal server error. Please contact admin for futher assistance");
            respMap.put("respCode", "000198");
            respMap.put("respStatus", "error");
            respMap.put("respMessage", "Internal server error. Please contact admin for futher assistance");
        }
        return respMap;
    }

    //handle student data update
    @PostMapping("/update")
    public void update(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        try {
            //will do validation
            if(!authService.authenticate(session)) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            //validate required field before proceed the real process
            FieldUtility.requiredValidator(body, studentRequiredFields());
            if(!studentService.editStudent(body)) {
                throw new Exception("Failed to update student data");
            }else {
                session.setAttribute("success", "Student data successfully updated");
            }
        }catch (Exception e) {
            session.setAttribute("error", e.getMessage());
        }
        response.sendRedirect("/student");
    }

    //handle delete proces of student data
    @PostMapping("/delete")
    public void delete(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        try {
            if(!authService.authenticate(session)) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            if(!studentService.delete(body)) {
                throw new Exception("Failed to delete student data");
            }else {
                session.setAttribute("success", "Student data deleted successfully");
            }
        }catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", e.getMessage());
        }
        response.sendRedirect("/student");
    }

    //API to retrieve student enrolled in specific course
    @GetMapping("/course")
    @ResponseBody
    public Map<String, Object> retrieveByCourse(@RequestParam Map<String, Object> body, HttpSession session) {
        Map<String, Object> respMap = new HashMap<>();
        try {
            if(!authService.authenticate(session)) {
                respMap.put("respCode", "00002");
                respMap.put("respStatus", "error");
                respMap.put("respMessage", "Unauthorized request");
                return respMap;
            }

            List<StudentModel> studentList = studentService.retrieveByCourse(body);
            CourseModel courseModel = courseService.retrieveDetail(body);

            if(studentList == null || courseModel == null) {
                respMap.put("respCode", "00001");
                respMap.put("respStatus", "error");
                respMap.put("respMessage", "Student list does not found!");
            }else{
                respMap.put("respCode", "00000");
                respMap.put("respStatus", "success");
                respMap.put("respMessage", "successfully retrieved");
            }

            Map<String, Object> tempMap = new HashMap<>();
            tempMap.put("course", courseModel);
            tempMap.put("students", studentList);
            respMap.put("data", tempMap);
        }catch (Exception e) {
            e.printStackTrace();
            //session.setAttribute("message", "Internal server error. Please contact admin for futher assistance");
            respMap.put("respCode", "000198");
            respMap.put("respStatus", "error");
            respMap.put("respMessage", "Internal server error. Please contact admin for futher assistance");
        }
        return respMap;
    }

    //handle page to retrieve course registration page
    @GetMapping("/register/course")
    public String regCourse(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        if(!authService.authenticate(session)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return null;
        }

        if(!authService.authorize(session, FieldUtility.STUDENT_ROLE)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        CommonModel commonModel = (CommonModel) session.getAttribute("common");
        List<CourseModel> courseList = courseService.retrieveAvailableCourseStudent(commonModel.getUser().getId());
        request.setAttribute("availableCourses", courseList);

        List<CourseModel> courseList2 = courseService.retrieveRegisteredCourseStudent(commonModel.getUser().getId());
        request.setAttribute("registeredCourses", courseList2);

        request.setAttribute("courses", courseList2);
        request.setAttribute("totalCourse", courseList.size());

        return "Student/courseReg";
    }

    //handle POST request to register course
    @PostMapping("/register/course")
    public void registerCourse(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        try {
            if(!authService.authenticate(session)) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            CommonModel commonModel = (CommonModel) session.getAttribute("common");
            String ind = (String) body.get("ind");
            body.put("uid", Integer.toString(commonModel.getUser().getId()));

            boolean result = false;
            if(ind.equalsIgnoreCase("register")) {
                result = courseService.registerStudent(body, true);
            }else if (ind.equalsIgnoreCase("unregister")) {
                result = courseService.registerStudent(body, false);
            }else{
                System.err.println("Invalid indicator");
            }

            if(!result) {
                session.setAttribute("error" , "Internal server error. Please contact admin for futher assistance");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("/student/register/course");
    }

    //required field for student
    private String[][] studentRequiredFields() {
        return new String[][] {
                {"program", "Program is required"},
                {"intake", "Intake is required"},
                {"semester", "Semester is required"}
        };
    }
}
