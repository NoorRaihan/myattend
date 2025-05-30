package com.uitm.myattend.controller;

import com.uitm.myattend.model.CommonModel;
import com.uitm.myattend.model.UserModel;
import com.uitm.myattend.service.AuthService;
import com.uitm.myattend.service.StudentService;
import com.uitm.myattend.service.UserService;
import com.uitm.myattend.utility.FieldUtility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    //retrieve all user in index user page
    @GetMapping("")
    public String index(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        if(!authService.authenticate(session)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return null;
        }

        if(!authService.authorize(session, FieldUtility.ADMIN_ROLE)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        List<UserModel> userList = userService.retrieveAll(session);
        request.setAttribute("users", userList);
        request.setAttribute("totalUser", userList.size());
        return "Manage/users";
    }

    @GetMapping("/create")
    public String create() {
        return "User/create";
    }

    //handle post request to save user data
    @PostMapping("/create")
    public void store(@RequestParam Map<String, Object> body, @RequestParam("dpImage") MultipartFile file, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        try {
            if(!authService.authenticate(session)) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            if(!authService.authorize(session, FieldUtility.ADMIN_ROLE)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }

            //check for required field using generic function
            FieldUtility.requiredValidator(body, userRequiredFields());

            CommonModel common = (CommonModel) session.getAttribute("common");
            //check if the email already existed/taken or not
            if(userService.retrieveUserByEmail((String) body.get("email")) != null) {
                throw new Exception("Email already exists");
            }

            //process the field
            UserModel user = userService.insert(body, file);
            if(user != null) {
                session.setAttribute("success", "New user successfully added");
            }else{
                session.setAttribute("error", "Internal server error. Please contact admin for futher assistance");
            }
            response.sendRedirect("/user");
        }catch (Exception e) {
            session.setAttribute("error", e.getMessage());
            response.sendRedirect("/user");
        }
    }

    //common required field for user data
    private String [][] userRequiredFields() {
        String [][] field = {
                {"fullname", "Fullname is required"},
                {"username", "Username is required"},
                {"email", "Email is required"},
                //{"password", "Password is required"},
                {"gender", "Gender is required"},
                {"birthdate", "Birth date is required"},
                {"role", "Role is required"}
        };
        return field;
    }

    //required field for profile
    private String [][] profileRequiredFields() {
        String [][] field = {
                {"fullname", "Fullname is required"},
                {"username", "Username is required"},
                {"email", "Email is required"},
                {"gender", "Gender is required"},
                {"birthdate", "Birth date is required"}
        };
        return field;
    }

    //API to retrieve user detail
    @GetMapping("/detail")
    @ResponseBody
    public Map<String, Object> show(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpServletRequest request, HttpSession session) {
        Map<String, Object> respMap = new HashMap<>();
        try {
            if(!authService.authenticate(session)) {
                respMap.put("respCode", "00002");
                respMap.put("respStatus", "error");
                respMap.put("respMessage", "Unauthenticated request");
                return respMap;
            }

            if(!authService.authorize(session, FieldUtility.ADMIN_ROLE)) {
                respMap.put("respCode", "00099");
                respMap.put("respStatus", "error");
                respMap.put("respMessage", "Unauthorized request");
                return respMap;
            }

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

    //handle user update request
    @PostMapping("/update")
    public void update(@RequestParam Map<String, Object> body, @RequestParam("dpImage") MultipartFile file, HttpServletResponse response, HttpServletRequest request ,HttpSession session) throws IOException {
        try {
            if(!authService.authenticate(session)) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            if(!authService.authorize(session, FieldUtility.ADMIN_ROLE)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }

            FieldUtility.requiredValidator(body, userRequiredFields());

            CommonModel common = (CommonModel) session.getAttribute("common");
            //check for exisiting email and if same with old just ignore otherwise do checking
            if(!body.get("email").equals(common.getUser().getEmail())) {
                if(userService.retrieveUserByEmail((String) body.get("email")) != null) {
                    throw new Exception("Email already exists");
                }
            }

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

    //handle deletion process of user
    @PostMapping("/delete")
    public void delete(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        try {
            if(!authService.authenticate(session)) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            if(!authService.authorize(session, FieldUtility.ADMIN_ROLE)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }

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


    //API to retrieve user profile
    @GetMapping("/profile")
    @ResponseBody
    public Map<String, Object> profile(HttpServletResponse response, HttpServletRequest request, HttpSession session) {
        Map<String, Object> respMap = new HashMap<>();
        try {
            if(!authService.authenticate(session)) {
                respMap.put("respCode", "00002");
                respMap.put("respStatus", "error");
                respMap.put("respMessage", "Unauthenticated request");
                return respMap;
            }


            CommonModel common = (CommonModel) session.getAttribute("common");
            UserModel user = userService.retrieveUserById(common.getUser().getId());

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

    //handle user profile update -> same as user update just the user id will pull for the session itself.
    @PostMapping("/profile")
    public void profileUpdate(@RequestParam Map<String, Object> body, @RequestParam("dpImage") MultipartFile file, HttpServletResponse response, HttpServletRequest request ,HttpSession session) throws IOException {
        try {
            if(!authService.authenticate(session)) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            CommonModel common = (CommonModel) session.getAttribute("common");
            FieldUtility.requiredValidator(body, profileRequiredFields());
            body.put("uid", Integer.toString(common.getUser().getId()));
            body.put("role", Integer.toString(common.getUser().getRole_id()));

            if(!body.get("email").equals(common.getUser().getEmail())) {
                if(userService.retrieveUserByEmail((String) body.get("email")) != null) {
                    throw new Exception("Email already exists");
                }
            }

            if(!userService.update(body, file, session)) {
                throw new Exception("Failed to update profile");
            }else {
                session.setAttribute("success", "Profile updated successfully");
            }
        }catch (Exception e) {
            session.setAttribute("error", e.getMessage());
            e.printStackTrace();
        }
        response.sendRedirect("/");
    }
}
