package com.uitm.myattend.service;

import com.uitm.myattend.model.CommonModel;
import com.uitm.myattend.model.LecturerModel;
import com.uitm.myattend.model.UserModel;
import com.uitm.myattend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;

public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final StudentService studentService;
    private final LecturerService lecturerService;
    private CommonModel common;

    @Autowired
    public AuthService(PasswordEncoder passwordEncoder,
                       UserService userService,
                       StudentService studentService,
                       LecturerService lecturerService,
                       CommonModel common) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.studentService = studentService;
        this.lecturerService = lecturerService;
    }
    public boolean authenticate() {
        try {
            if(!validateToken()) {
                return false;
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

            List<Map<String, String>> tokenData = userService.retrieveToken(common.getToken());
            return tokenData != null && !tokenData.isEmpty() && Integer.parseInt(tokenData.get(0).get("valid")) != 0;

        }catch (Exception e) {
            return false;
        }
    }

    public boolean register(Map<String, Object> body) {
        try {
            //will enhance to retrieve all roles and do dynamic checking
            if(body.get("role") == null || "".equals(body.get("role"))) {
                throw new Exception("Role can't be empty");
            }
            int role = (int)body.get("role");

            if(role != 1 && role != 2 && role !=3 && role != 4) {
                throw new Exception("Invalid role");
            }

            UserModel user = userService.insert(body);
            try {
                switch(role) {
                    case 2:
                        lecturerService.insert(user, body);
                        break;
                    case 3:
                        studentService.insert(user, body);
                        break;
                    default:
                        throw new Exception("Invalid role");
                }
            } catch (Exception e) {
                e.printStackTrace();
                userService.delete(user.getId());
                throw new Exception("Failed to register the role");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
