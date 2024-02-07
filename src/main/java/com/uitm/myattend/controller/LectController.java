package com.uitm.myattend.controller;

import com.uitm.myattend.model.LecturerModel;
import com.uitm.myattend.model.StudentModel;
import com.uitm.myattend.service.AuthService;
import com.uitm.myattend.service.LecturerService;
import com.uitm.myattend.utility.FieldUtility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Controller
@RequestMapping("/lecturer")
public class LectController {

    private final LecturerService lecturerService;
    private final AuthService authService;

    public LectController(LecturerService lecturerService, AuthService authService) {
        this.lecturerService = lecturerService;
        this.authService = authService;
    }

    @GetMapping("")
    public String lecturer (HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        if(!authService.authenticate(session)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return null;
        }
        if(!authService.authorize(session, FieldUtility.ADMIN_ROLE)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        List<LecturerModel> lectList = lecturerService.retrieveAll();
        List<LecturerModel> svList = lecturerService.retrieveSV();
        request.setAttribute("lecturers", lectList);
        request.setAttribute("lecturerSv", svList);
        request.setAttribute("totalLecturer", lectList.size());
        return "Manage/lecturers";
    }

    @GetMapping("/detail")
    @ResponseBody
    public Map<String, Object> show(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpSession session) {
        Map<String, Object> respMap = new HashMap<>();
        try {
            if(!authService.authenticate(session)) {
                respMap.put("respCode", "00002");
                respMap.put("respStatus", "error");
                respMap.put("respMessage", "Unauthorized request");
                return respMap;
            }

            LecturerModel lecturer = lecturerService.retrieveDetail(body);

            if(lecturer == null) {
                respMap.put("respCode", "00001");
                respMap.put("respStatus", "error");
                respMap.put("respMessage", "Lecturer does not found!");
            }else{
                respMap.put("respCode", "00000");
                respMap.put("respStatus", "success");
                respMap.put("respMessage", "successfully retrieved");
            }

            respMap.put("data", lecturer);
        }catch (Exception e) {
            e.printStackTrace();
            //session.setAttribute("message", "Internal server error. Please contact admin for futher assistance");
            respMap.put("respCode", "000198");
            respMap.put("respStatus", "error");
            respMap.put("respMessage", "Internal server error. Please contact admin for futher assistance");
        }
        System.out.println(respMap);
        return respMap;
    }

    @PostMapping("/update")
    public void update(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        try {
            if(!authService.authenticate(session)) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            FieldUtility.requiredValidator(body, lecturerRequiredFields());
            if(!lecturerService.editLecturer(body)) {
                throw new Exception("Failed to update lecturer data");
            }else {
                session.setAttribute("success", "Lecturer data successfully updated");
            }
        }catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", e.getMessage());
        }
        response.sendRedirect("/lecturer");
    }

    @PostMapping
    public void delete(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        try {
            if(!authService.authenticate(session)) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            if(!lecturerService.delete(body)) {
                throw new Exception("Failed to delete lecturer data");
            }else {
                session.setAttribute("success", "Lecturer data deleted successfully");
            }
        }catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", e.getMessage());
        }
        response.sendRedirect("/lecturer");
    }

    private String [][] lecturerRequiredFields() {
        return new String[][]{
                {"startDate", "Lecturer start date is required"},
                {"qualify", "Lecturer qualification is required"},
                {"salary", "Lecturer salary is required"}
        };
    }
}
