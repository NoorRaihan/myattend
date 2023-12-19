package com.uitm.myattend.service;

import com.uitm.myattend.model.CommonModel;
import com.uitm.myattend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;

public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepo;
    private CommonModel common;

    @Autowired
    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepo, CommonModel common) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }
    public boolean authenticate() {
        try {
            if(!validateToken()) {

            }
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public boolean validateToken() {
        try {
            if(common.getToken() == null) {
                return false;
            }

            List<Map<String, String>> tokenData = userRepo.retrieveToken(common.getToken());
            return tokenData != null && !tokenData.isEmpty() && Integer.parseInt(tokenData.get(0).get("valid")) != 0;

        }catch (Exception e) {
            return false;
        }
    }

}
