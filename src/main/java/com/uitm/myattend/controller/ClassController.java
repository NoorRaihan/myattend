package com.uitm.myattend.controller;

import com.uitm.myattend.model.*;
import com.uitm.myattend.service.AttendanceService;
import com.uitm.myattend.service.AuthService;
import com.uitm.myattend.service.ClassService;
import com.uitm.myattend.service.CourseService;
import com.uitm.myattend.utility.FieldUtility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/class")
public class ClassController {

    private final ClassService classService;
    private final CourseService courseService;
    private final AuthService authService;
    private final AttendanceService attendanceService;

    public ClassController(ClassService classService, CourseService courseService, AuthService authService, AttendanceService attendanceService) {
        this.classService = classService;
        this.courseService = courseService;
        this.authService = authService;
        this.attendanceService = attendanceService;
    }

    @GetMapping("")
    public String classMgt(HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        if(!authService.authenticate(session)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return null;
        }
        if(!authService.authorize(session, FieldUtility.LECTURER_ROLE)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        CommonModel commonModel = (CommonModel) session.getAttribute("common");
        List<CourseModel> courseList = courseService.retrieveCourseByLecturer(commonModel.getUser().getId());
        request.setAttribute("courses", courseList);
        return "Lecturer/classes";
    }

    @GetMapping("/list")
    public String classList(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        if(!authService.authenticate(session)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return null;
        }
        if(!authService.authorize(session, FieldUtility.STUDENT_ROLE)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        CommonModel commonModel = (CommonModel) session.getAttribute("common");
        List<ClassModel> todayList = classService.retrieveAll(commonModel.getUser().getId());
        request.setAttribute("todayList", todayList);
        request.setAttribute("totalClass", todayList.size());
        return "Student/classList";
    }

    @GetMapping("/studentList")
    public String studList(HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        if(!authService.authenticate(session)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return null;
        }

        if(!authService.authorize(session, FieldUtility.LECTURER_ROLE)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        CommonModel commonModel = (CommonModel) session.getAttribute("common");
        List<CourseModel> courseList = courseService.retrieveCourseByLecturer(commonModel.getUser().getId());
        request.setAttribute("courses", courseList);
        return "Lecturer/studentList";
    }

    @GetMapping("/course")
    @ResponseBody
    public Map<String, Object> retrieveByCourse(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpSession session) {
        Map<String, Object> respMap = new HashMap<>();
        try {
            if(!authService.authenticate(session)) {
                respMap.put("respCode", "00002");
                respMap.put("respStatus", "error");
                respMap.put("respMessage", "Unauthorized request");
                return respMap;
            }

            List<ClassModel> classList = classService.retrieveByCourse(body);
            CourseModel courseModel = courseService.retrieveDetail(body);

            if(classList == null || courseModel == null) {
                respMap.put("respCode", "00001");
                respMap.put("respStatus", "error");
                respMap.put("respMessage", "Class List does not found!");
            }else{
                respMap.put("respCode", "00000");
                respMap.put("respStatus", "success");
                respMap.put("respMessage", "successfully retrieved");
            }

            Map<String, Object> tempMap = new HashMap<>();
            tempMap.put("course", courseModel);
            tempMap.put("classes", classList);
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

    @GetMapping("/detail")
    @ResponseBody
    public Map<String, Object> retrieveDetail(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpServletRequest request, HttpSession session) {
        Map<String, Object> respMap = new HashMap<>();
        try {
            if(!authService.authenticate(session)) {
                response.sendRedirect(request.getContextPath() + "/login");
                return null;
            }

            ClassModel classModel = classService.retrieveDetail(body);

            if(classModel == null) {
                respMap.put("respCode", "00001");
                respMap.put("respStatus", "error");
                respMap.put("respMessage", "Class List does not found!");
            }else{
                respMap.put("respCode", "00000");
                respMap.put("respStatus", "success");
                respMap.put("respMessage", "successfully retrieved");
            }

            respMap.put("data", classModel);
        }catch (Exception e) {
            e.printStackTrace();
            //session.setAttribute("message", "Internal server error. Please contact admin for futher assistance");
            respMap.put("respCode", "000198");
            respMap.put("respStatus", "error");
            respMap.put("respMessage", "Internal server error. Please contact admin for futher assistance");
        }
        return respMap;
    }

    @PostMapping("/store")
    public void store(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        try {
            if(!authService.authenticate(session)) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            FieldUtility.requiredValidator(body, classRequiredFields());
            if(!classService.insert(body)) {
                session.setAttribute("error", "Failed to register new class");
            }else {
                session.setAttribute("success", "New class successfully created");
            }
        }catch (Exception e) {
            session.setAttribute("error", "Internal server error. Please contact admin for futher assistance");
            e.printStackTrace();
        }
        response.sendRedirect("/class");
    }

    @GetMapping("/generateQR")
    @ResponseBody
    public Map<String, Object> generateQR(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpServletRequest request, HttpSession session) {
        Map<String, Object> respMap = new HashMap<>();
        try {
            if(!authService.authenticate(session)) {
                respMap.put("respCode", "00002");
                respMap.put("respStatus", "error");
                respMap.put("respMessage", "Unauthorized request");
                return respMap;
            }

            //ClassModel classModel = classService.retrieveDetail(body);
            Map<String, Object> uniqueMap = classService.generateAttendanceUnique(body);

            if(uniqueMap == null) {
                respMap.put("respCode", "00001");
                respMap.put("respStatus", "error");
                respMap.put("respMessage", "Failed to generate unique id");
            }else{
                respMap.put("respCode", "00000");
                respMap.put("respStatus", "success");
                respMap.put("respMessage", "successfully retrieved");
            }

            respMap.put("data", uniqueMap);
        }catch (Exception e) {
            e.printStackTrace();
            //session.setAttribute("message", "Internal server error. Please contact admin for futher assistance");
            respMap.put("respCode", "000198");
            respMap.put("respStatus", "error");
            respMap.put("respMessage", "Internal server error. Please contact admin for futher assistance");
        }
        return respMap;
    }

    @PostMapping("/attend")
    @ResponseBody
    public Map<String, Object> attendance(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpServletRequest request, HttpSession session) {
        Map<String, Object> respMap = new HashMap<>();
        try {
            if(!authService.authenticate(session)) {
                respMap.put("respCode", "00002");
                respMap.put("respStatus", "error");
                respMap.put("respMessage", "Unauthorized request");
                return respMap;
            }

            if(attendanceService.checkAttendance(body)) {
                respMap.put("respCode", "00000");
                respMap.put("respStatus", "success");
                respMap.put("respMessage", "Attendance marked");
            }

        }catch (Exception e) {
            e.printStackTrace();
            //session.setAttribute("message", "Internal server error. Please contact admin for futher assistance");
            respMap.put("respCode", "000198");
            respMap.put("respStatus", "error");
            respMap.put("respMessage", e.getMessage());
        }
        return respMap;
    }

    @PostMapping("/update")
    public void update(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        try {
            //will do validation
            if(!authService.authenticate(session)) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            FieldUtility.requiredValidator(body, classRequiredFields());
            if(!classService.update(body)) {
                throw new Exception("Failed to save data");
            }else {
                session.setAttribute("success", "Data saved");
            }
        }catch (Exception e) {
            session.setAttribute("error", e.getMessage());
        }
        response.sendRedirect("/class");
    }

    @PostMapping("/delete")
    public void delete(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        try {
            //will do validation
            if(!authService.authenticate(session)) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            //FieldUtility.requiredValidator(body, studentRequiredFields());
            if(!classService.delete(body)) {
                throw new Exception("Failed to delete data");
            }else {
                session.setAttribute("success", "Data deleted successfully");
            }
        }catch (Exception e) {
            session.setAttribute("error", e.getMessage());
        }
        response.sendRedirect("/class");
    }

    @GetMapping("/attendList")
    @ResponseBody
    public Map<String, Object> attendanceList(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpServletRequest request, HttpSession session) {
        Map<String, Object> respMap = new HashMap<>();
        try {
            List<AttendanceModel> attList = attendanceService.retrieveAttendance(body);

            if(attList == null) {
                respMap.put("respCode", "00001");
                respMap.put("respStatus", "error");
                respMap.put("respMessage", "Internal server error. Please contact admin for further assistance");
            }else{
                respMap.put("respCode", "00000");
                respMap.put("respStatus", "success");
                respMap.put("respMessage", "successfully retrieved");
            }
            respMap.put("data", attList);
        }catch (Exception e) {
            e.printStackTrace();
            //session.setAttribute("message", "Internal server error. Please contact admin for futher assistance");
            respMap.put("respCode", "000198");
            respMap.put("respStatus", "error");
            respMap.put("respMessage", e.getMessage());
        }
        return respMap;
    }

    private String [][] classRequiredFields() {
        String [][] field = {
                {"class_desc", "Class name is required"},
                {"venue", "Venue is required"},
                {"start_time", "Class start time is required"},
                {"end_time", "Class end time is required"},
                {"class_date", "Class date is required"}
        };
        return field;
    }
}
