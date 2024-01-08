package com.uitm.myattend.controller;

import com.uitm.myattend.model.UserModel;
import com.uitm.myattend.service.StudentService;
import com.uitm.myattend.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.http.HttpResponse;
import java.sql.SQLException;
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

    @GetMapping("/")
    public String index() {
        //System.out.println(userService.retrieveAll());
        return "Manage/users";
    }

    @GetMapping("/create")
    public String create() {
        return "User/create";
    }

    @PostMapping("/create")
    public void store(@RequestParam Map<String, Object> body, HttpServletResponse response) {
        try {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        UserModel user = userService.insert(body);
                        studentService.insert(user,body);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            response.sendRedirect("/user/");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
