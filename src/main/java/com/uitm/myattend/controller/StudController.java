package com.uitm.myattend.controller;

import com.uitm.myattend.model.LecturerModel;
import com.uitm.myattend.model.StudentModel;
import com.uitm.myattend.model.UserModel;
import com.uitm.myattend.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/student")
public class StudController {

    private final StudentService studentService;

    public StudController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        List<StudentModel> studList = studentService.retrieveAll();
        request.setAttribute("students", studList);
        request.setAttribute("totalStudent", studList.size());
        return "Manage/students";
    }

    @GetMapping("/detail")
    @ResponseBody
    public Map<String, Object> show(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpSession session) {
        Map<String, Object> respMap = new HashMap<>();
        try {
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

    @PostMapping("/update")
    public void update(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpSession session) throws IOException {
        try {
            //will do validation

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

    @PostMapping("/delete")
    public void delete(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpSession session) throws IOException {
        try {

            if(studentService.delete(body)) {
                throw new Exception("Failed to delete student data");
            }else {
                session.setAttribute("success", "Student data deleted successfully");
            }
        }catch (Exception e) {
            session.setAttribute("error", e.getMessage());
        }
        response.sendRedirect("/student");
    }
}
