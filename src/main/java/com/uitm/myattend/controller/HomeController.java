package com.uitm.myattend.controller;

import com.uitm.myattend.service.AttendanceService;
import com.uitm.myattend.service.AuthService;
import com.uitm.myattend.service.HomeService;
import com.uitm.myattend.utility.FieldUtility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Paths;


@Controller
public class HomeController {

    private final AuthService authService;
    private final HomeService homeService;

    public HomeController(AuthService authService, HomeService homeService) {
        this.authService = authService;
        this.homeService = homeService;
    }

    @GetMapping("/")
    public String home(HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        System.out.println("Home SID:" + session.getAttribute("sid"));

        if(!authService.authenticate(session)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return null;
            //throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        homeService.index(session, request);
        return "Home/home";
    }

    @GetMapping("/qrscan")
    public String qrscan(HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        if(!authService.authenticate(session)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return null;
            //throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        if(!authService.authorize(session, FieldUtility.STUDENT_ROLE)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return "Home/qrscan";
    }

    @GetMapping("/utility")
    public String utilityMgt() {
        return "Manage/utilities";
    }

}
