package com.uitm.myattend.controller;

import com.uitm.myattend.model.CourseModel;
import com.uitm.myattend.model.LecturerModel;
import com.uitm.myattend.service.AuthService;
import com.uitm.myattend.service.CourseService;
import com.uitm.myattend.service.LecturerService;
import com.uitm.myattend.utility.FieldUtility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;
    private final LecturerService lecturerService;
    private final AuthService authService;

    public CourseController(CourseService courseService, LecturerService lecturerService, AuthService authService) {
        this.courseService = courseService;
        this.lecturerService = lecturerService;
        this.authService = authService;
    }
    @GetMapping("")
    public String course(HttpServletRequest request, HttpServletResponse response) {
        List<CourseModel> courseList = courseService.retrieveAll();
        request.setAttribute("courses", courseList);

        List<LecturerModel> lectList = lecturerService.retrieveAvailableConfirm();
        request.setAttribute("lecturers", lectList);

        List<LecturerModel> lectList2 = lecturerService.retrieveAll();
        request.setAttribute("lecturersAll", lectList2);
        request.setAttribute("totalCourse", courseList.size());
        return "Manage/courses";
    }

    @PostMapping("/create")
    public void store(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpSession session) throws IOException {
        try {
            if(courseService.insert(body)) {
                session.setAttribute("message", "New course successfully added");
            }else {
                session.setAttribute("message", "Failed to add new course");
            }
        }catch (Exception e) {
            session.setAttribute("error", "Internal server error. Please contact admin for futher assistance");
        }
        response.sendRedirect("/course");
    }

    @GetMapping("/detail")
    @ResponseBody
    public Map<String, Object> show(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpSession session) {
        Map<String, Object> respMap = new HashMap<>();
        try {
            CourseModel course = courseService.retrieveDetail(body);

            if(course == null) {
                respMap.put("respCode", "00001");
                respMap.put("respStatus", "error");
                respMap.put("respMessage", "Course does not found!");
            }else{
                respMap.put("respCode", "00000");
                respMap.put("respStatus", "success");
                respMap.put("respMessage", "successfully retrieved");
            }

            respMap.put("data", course);
        }catch (Exception e) {
            e.printStackTrace();
            //session.setAttribute("message", "Internal server error. Please contact admin for futher assistance");
            respMap.put("respCode", "000198");
            respMap.put("respStatus", "error");
            respMap.put("respMessage", "Internal server error. Please contact admin for futher assistance");
        }
        return respMap;
    }

    @PostMapping("/update")
    public void update(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpSession session) throws IOException {
        try {
            if(!courseService.update(body)) {
                throw new Exception("Failed to update course information");
            }else {
                session.setAttribute("success", "Course updated successfully");
            }
        }catch (Exception e) {
            session.setAttribute("error", e.getMessage());
            e.printStackTrace();
        }
        response.sendRedirect("/course");
    }

    @PostMapping("/delete")
    public void delete(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpSession session) throws IOException {
        try {
            if(!courseService.delete(body)) {
                throw new Exception("Failed to delete course data");
            }else {
                session.setAttribute("success", "Course data successfully deleted");
            }
        }catch (Exception e) {
            session.setAttribute("error", e.getMessage());
            e.printStackTrace();
        }
        response.sendRedirect("/course");
    }

    @PostMapping("/disable")
    public void disable(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpSession session) throws IOException {
        try {
            if(!courseService.changeStatus(body, true)) {
                throw new Exception("Failed to disable course data");
            }else {
                session.setAttribute("success", "Course data successfully disabled");
            }
        }catch (Exception e) {
            session.setAttribute("error", e.getMessage());
            e.printStackTrace();
        }
        response.sendRedirect("/course");
    }

    @PostMapping("/enable")
    public void enable(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpSession session) throws IOException {
        try {
            if(!courseService.changeStatus(body, false)) {
                throw new Exception("Failed to enable course data");
            }else {
                session.setAttribute("success", "Course data successfully enabled");
            }
        }catch (Exception e) {
            session.setAttribute("error", e.getMessage());
            e.printStackTrace();
        }
        response.sendRedirect("/course");
    }
}
