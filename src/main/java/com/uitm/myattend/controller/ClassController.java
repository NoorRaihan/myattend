package com.uitm.myattend.controller;

import com.uitm.myattend.model.ClassModel;
import com.uitm.myattend.model.CommonModel;
import com.uitm.myattend.model.CourseModel;
import com.uitm.myattend.model.StudentModel;
import com.uitm.myattend.service.AuthService;
import com.uitm.myattend.service.ClassService;
import com.uitm.myattend.service.CourseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    public ClassController(ClassService classService, CourseService courseService, AuthService authService) {
        this.classService = classService;
        this.courseService = courseService;
        this.authService = authService;
    }

    @GetMapping("")
    public String classMgt(HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        if(!authService.authenticate(session)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return null;
        }

        CommonModel commonModel = (CommonModel) session.getAttribute("common");
        List<CourseModel> courseList = courseService.retrieveCourseByLecturer(commonModel.getUser().getId());
        request.setAttribute("courses", courseList);
        return "Lecturer/classes";
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

            List<Object> respList = new ArrayList<>();
            Map<String, Object> courseMap = new HashMap<>();
            Map<String, List<ClassModel>> classMap = new HashMap<>();

            courseMap.put("course", courseModel);
            classMap.put("classes", classList);
            respList.add(courseMap);
            respList.add(classMap);
            respMap.put("data", respList);
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
            CourseModel courseModel = courseService.retrieveDetail(body);

            if(classModel == null || courseModel == null) {
                respMap.put("respCode", "00001");
                respMap.put("respStatus", "error");
                respMap.put("respMessage", "Class List does not found!");
            }else{
                respMap.put("respCode", "00000");
                respMap.put("respStatus", "success");
                respMap.put("respMessage", "successfully retrieved");
            }

            List<Object> respList = new ArrayList<>();
            Map<String, Object> courseMap = new HashMap<>();
            Map<String, ClassModel> classMap = new HashMap<>();

            courseMap.put("course", courseModel);
            classMap.put("class", classModel);
            respList.add(courseMap);
            respList.add(classMap);
            respMap.put("data", respList);
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
}
