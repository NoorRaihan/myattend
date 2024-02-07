package com.uitm.myattend.controller;

import com.uitm.myattend.model.RoleModel;
import com.uitm.myattend.service.AttendanceService;
import com.uitm.myattend.service.AuthService;
import com.uitm.myattend.service.HomeService;
import com.uitm.myattend.service.RoleService;
import com.uitm.myattend.utility.FieldUtility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class HomeController {

    private final AuthService authService;
    private final HomeService homeService;
    private final RoleService roleService;

    public HomeController(AuthService authService, HomeService homeService, RoleService roleService) {
        this.authService = authService;
        this.homeService = homeService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String home(HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        System.out.println("Home SID:" + session.getAttribute("sid"));

        if(!authService.authenticate(session)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return null;
        }

        homeService.index(session, request);
        return "Home/home";
    }

    @GetMapping("/qrscan")
    public String qrscan(HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        if(!authService.authenticate(session)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return null;
        }

        if(!authService.authorize(session, FieldUtility.STUDENT_ROLE)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return "Home/qrscan";
    }

    @GetMapping("/utility")
    public String utilityMgt(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        if(!authService.authenticate(session)) {
            response.sendRedirect(request.getContextPath() + "/login");
            return null;
        }

        if(!authService.authorize(session, FieldUtility.ADMIN_ROLE)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        List<RoleModel> roleList = roleService.retrieveAll();
        request.setAttribute("roles", roleList);
        return "Manage/utilities";
    }

    @GetMapping("/utility/detail")
    @ResponseBody
    public Map<String, Object> utilityDetail(@RequestParam Map<String, Object> body, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> respMap = new HashMap<>();
        try {
            int id = Integer.parseInt((String) body.get("id"));
            RoleModel role = roleService.retrieve(id);

            if(role == null) {
                respMap.put("respCode", "00001");
                respMap.put("respStatus", "error");
                respMap.put("respMessage", "Student does not found!");
            }else{
                respMap.put("respCode", "00000");
                respMap.put("respStatus", "success");
                respMap.put("respMessage", "successfully retrieved");
            }
            respMap.put("data", role);

        }catch (Exception e) {
            respMap.put("respCode", "000198");
            respMap.put("respStatus", "error");
            respMap.put("respMessage", "Internal server error. Please contact admin for futher assistance");
            e.printStackTrace();
        }

        return respMap;
    }

    @PostMapping("/role/update")
    public void update(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        try {
            //will do validation
            if(!authService.authenticate(session)) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            //FieldUtility.requiredValidator(body, studentRequiredFields());
            roleService.update(body);
        }catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", e.getMessage());
        }
        response.sendRedirect("/utility");
    }

    @PostMapping("/role/create")
    public void create(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        try {
            //will do validation
            if(!authService.authenticate(session)) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            //FieldUtility.requiredValidator(body, studentRequiredFields());
            roleService.insert(body);
        }catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", e.getMessage());
        }
        response.sendRedirect("/utility");
    }

    @PostMapping("/role/delete")
    public void delete(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        try {
            //will do validation
            if(!authService.authenticate(session)) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            //FieldUtility.requiredValidator(body, studentRequiredFields());
            if(!roleService.delete(body)) {
                throw new Exception("Failed to delete role");
            }
        }catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", e.getMessage());
        }
        response.sendRedirect("/utility");
    }

}
