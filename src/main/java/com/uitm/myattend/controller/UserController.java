package com.uitm.myattend.controller;

import com.uitm.myattend.model.UserModel;
import com.uitm.myattend.service.StudentService;
import com.uitm.myattend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final StudentService studentService;
    private final TransactionTemplate transactionTemplate;

    public UserController(UserService userService, StudentService studentService, TransactionTemplate transactionTemplate) {
        this.userService = userService;
        this.studentService = studentService;
        this.transactionTemplate = transactionTemplate;
    }

    @GetMapping("")
    public String index(HttpServletRequest request, HttpSession session) {
        List<UserModel> userList = userService.retrieveAll(session);
        request.setAttribute("users", userList);
        request.setAttribute("totalUser", userList.size());
        return "Manage/users";
    }

    @GetMapping("/create")
    public String create() {
        return "User/create";
    }

    @PostMapping("/create")
    public void store(@RequestParam Map<String, Object> body, @RequestParam("dpImage") MultipartFile file, HttpServletResponse response, HttpSession session) throws IOException {
        try {
//            userService.fileHandler(file, "1111");
            UserModel user = userService.insert(body, file);
            if(user != null) {
                session.setAttribute("message", "New user successfully added");
            }else{
                session.setAttribute("error", "Failed to add a new user");
            }
            response.sendRedirect("/user");
        }catch (Exception e) {
            session.setAttribute("error", "Internal server error. Please contact admin for futher assistance");
            response.sendRedirect("/user");
        }
    }

    @GetMapping("/detail")
    @ResponseBody
    public Map<String, Object> show(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpSession session) {
        Map<String, Object> respMap = new HashMap<>();
        try {
            int uid = Integer.parseInt((String)body.get("uid"));
            UserModel user = userService.retrieveUserById(uid);

            if(user == null) {
                respMap.put("respCode", "00001");
                respMap.put("respStatus", "error");
                respMap.put("respMessage", "User does not found!");
            }else{
                respMap.put("respCode", "00000");
                respMap.put("respStatus", "success");
                respMap.put("respMessage", "successfully retrieved");
            }
            respMap.put("data", user);
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
    public void update(@RequestParam Map<String, Object> body, @RequestParam("dpImage") MultipartFile file, HttpServletResponse response, HttpSession session) throws IOException {
        try {
            if(!userService.update(body, file)) {
                throw new Exception("Failed to update user data");
            }else {
                session.setAttribute("success", "User data updated successfully");
            }
        }catch (Exception e) {
            session.setAttribute("error", e.getMessage());
            e.printStackTrace();
        }
        response.sendRedirect("/user");
    }

    @PostMapping("/delete")
    public void delete(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpSession session) throws IOException {
        try {
            if(!userService.delete(body)) {
                throw new Exception("Failed to delete user data");
            }else {
                session.setAttribute("success", "User data successfully deleted");
            }
        }catch (Exception e) {
            session.setAttribute("error", e.getMessage());
            e.printStackTrace();
        }
        response.sendRedirect("/user");
    }
}
