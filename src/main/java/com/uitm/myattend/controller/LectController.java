package com.uitm.myattend.controller;

import com.uitm.myattend.model.LecturerModel;
import com.uitm.myattend.model.StudentModel;
import com.uitm.myattend.service.LecturerService;
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
@RequestMapping("/lecturer")
public class LectController {

    private final LecturerService lecturerService;

    public LectController(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    @GetMapping("")
    public String lecturer (HttpServletRequest request, HttpServletResponse response) {
        List<LecturerModel> lectList = lecturerService.retrieveAll();
        request.setAttribute("lecturers", lectList);
        request.setAttribute("totalLecturer", lectList.size());
        return "Manage/lecturers";
    }

    @GetMapping("/detail")
    @ResponseBody
    public Map<String, Object> show(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpSession session) {
        Map<String, Object> respMap = new HashMap<>();
        try {
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
        return respMap;
    }

    @PostMapping("/update")
    public void update(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpSession session) throws IOException {
        try {
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
    public void delete(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpSession session) {
        try {

        }catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", e.getMessage());
        }
    }
}
