package com.uitm.myattend.controller;

import com.uitm.myattend.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Map;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //handle login page
    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        return "Auth/login";
    }

    //send post request process for login
    @PostMapping("/login")
    public void authLogin(@RequestParam Map<String, Object> body, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        try {
            if(!authService.login(body, session)) {
                throw new Exception("Failed to log in a user");
            }
        }catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        response.sendRedirect(request.getContextPath() + "/");
    }

    //send post request for logout
    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        try {
            if(!authService.logout(session)) {
                throw new Exception("Failed to logout from user session");
            }
        }catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
