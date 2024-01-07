package com.uitm.myattend.controller;

import com.uitm.myattend.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        System.out.println("SID: " + session.getAttribute("sid"));
        return "Auth/login";
    }

    @PostMapping("/login")
    public void authLogin(@RequestParam Map<String, Object> body, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        try {
            if(authService.login(body, session)) {
                response.sendRedirect(request.getContextPath() + "/");
            }else{
                System.out.println(session.getAttribute("error"));
                response.sendRedirect(request.getContextPath() + "/login");
            }
        }catch (Exception e) {
            System.out.println("error");
        }

    }
}
