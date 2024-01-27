package com.uitm.myattend.service;

import com.uitm.myattend.model.CommonModel;
import com.uitm.myattend.model.LecturerModel;
import com.uitm.myattend.model.UserModel;
import com.uitm.myattend.repository.UserRepository;
import com.uitm.myattend.utility.FieldUtility;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private CommonModel common;
    private final UserRepository userRepository;

    @Autowired
    public AuthService(PasswordEncoder passwordEncoder,
                       UserService userService,
                       CommonModel common,
                       UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.common = common;
        this.userRepository = userRepository;
    }
    public boolean authenticate(HttpSession session) {
        try {
            if(!validateToken()) {
                return false;
            }
            session.setAttribute("sid", common.getToken());
            session.setAttribute("common", common);

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
            if(tokenData != null && !tokenData.isEmpty() && Integer.parseInt(tokenData.get(0).get("valid")) != 0) {
                if(common.getUser() == null) {
                    UserModel userObj = userService.retrieveUserById(Integer.parseInt(tokenData.get(0).get("user_id")));
                    common.setUser(userObj);
                }
                return true;
            }
            return false;
        }catch (Exception e) {
            return false;
        }
    }

    public boolean forbiddenValidator(HttpSession session, int roleid) {
        CommonModel common = (CommonModel) session.getAttribute("common");
        return roleid == common.getUser().getRole_id();
    }

//    public boolean register(Map<String, Object> body) {
//        try {
//            //will enhance to retrieve all roles and do dynamic checking
//            if(body.get("role") == null || "".equals(body.get("role"))) {
//                throw new Exception("Role can't be empty");
//            }
//            int role = (int)body.get("role");
//
//            if(role != 1 && role != 2 && role !=3 && role != 4) {
//                throw new Exception("Invalid role");
//            }
//
//            UserModel user = userService.insert(body);
//            try {
//                switch(role) {
//                    case 2 -> lecturerService.insert(user, body);
//                    case 3 -> studentService.insert(user, body);
//                    default -> throw new Exception("Invalid role");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                userService.delete(user.getId());
//                throw new Exception("Failed to register the role");
//            }
//            return true;
//        }catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    public boolean login(Map<String, Object> body, HttpSession session) {
        try {
            String username = (String)body.get("username");
            String rawPassword = (String)body.get("password");

            if(username.isBlank()) {
                throw new Exception("Username cannot be empty");
            }else if(rawPassword.isBlank()) {
                throw new Exception("Password cannot be empty");
            }

            UserModel userObj = userService.retrieveUserByEmail(username);

            if(userObj == null) {
                throw new Exception("Account does not exists");
            }

            //validate password
            if(!passwordEncoder.matches(rawPassword, userObj.getPassword())) {
                throw new Exception("Incorrect password");
            }

            //initiate session toDO
            String token = userService.initToken(userObj.getId());
            if(token == null) {
                throw new Exception("Failed to initiate user session");
            }

            common.setToken(token);
            common.setUser(userObj);
            session.setAttribute("sid", token);
            session.setAttribute("common", common);

            return true;
        }catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", e.getMessage());
            return false;
        }
    }

    public boolean logout(HttpSession session) {
        try {
            if(session != null) {
                CommonModel common = (CommonModel) session.getAttribute("common");
                int uid = common.getUser().getId();
                String token = common.getToken();

                if(!userRepository.updateToken(token, uid, 0)) {
                    throw new Exception("Failed to invalidate session token");
                }
                session.invalidate();
            }

            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
