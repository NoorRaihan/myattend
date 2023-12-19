package com.uitm.myattend.controller;

import com.uitm.myattend.model.CommonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
@Controller
@Async
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final CommonModel commonModel;

    @Autowired
    public AuthController(PasswordEncoder passwordEncoder, CommonModel commonModel) {
        this.passwordEncoder = passwordEncoder;
        this.commonModel = commonModel;
    }
    @GetMapping("/test")
    public void test() {
        System.out.println(commonModel.getEmail());
    }

    @GetMapping("/login")
    public String login() {
        return "Test/Test";
    }

    @PostMapping("/login")
    public void auth(@RequestParam Map<String, Object> body) {
        commonModel.setEmail((String)body.get("username"));
        System.out.println(commonModel.getEmail());
    }
}
