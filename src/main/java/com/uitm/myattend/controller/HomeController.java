package com.uitm.myattend.controller;

import com.uitm.myattend.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;


@Controller
public class HomeController {

    private final AuthService authService;

    public HomeController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/")
    public String home(HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        System.out.println("Home SID:" + session.getAttribute("sid"));

        if(!authService.authenticate()) {
            response.sendRedirect(request.getContextPath() + "/login");
        }
        return "Home/home";
    }

}
