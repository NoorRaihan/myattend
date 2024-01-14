package com.uitm.myattend.controller;

import com.uitm.myattend.model.LecturerModel;
import com.uitm.myattend.model.StudentModel;
import com.uitm.myattend.service.LecturerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class LectController {

    private final LecturerService lecturerService;

    public LectController(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    @GetMapping("/lecturer")
    public String lecturer (HttpServletRequest request, HttpServletResponse response) {
        List<LecturerModel> lectList = lecturerService.retrieveAll();
        request.setAttribute("lecturers", lectList);
        request.setAttribute("totalLecturer", lectList.size());
        return "Manage/lecturers";
    }

}
