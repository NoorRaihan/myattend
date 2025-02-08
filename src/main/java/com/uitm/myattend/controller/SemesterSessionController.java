package com.uitm.myattend.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.uitm.myattend.model.SemesterSessionModel;
import com.uitm.myattend.service.AuthService;
import com.uitm.myattend.service.SemesterSessionService;
import com.uitm.myattend.utility.FieldUtility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class SemesterSessionController {

    private final SemesterSessionService semesterSessionService;
    private final AuthService authService;

    public SemesterSessionController(SemesterSessionService semesterSessionService, AuthService authService) {
        this.semesterSessionService = semesterSessionService;
        this.authService = authService;
    }

    @GetMapping("/session")
    public String sessionAll(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        // if(!authService.authenticate(session)) {
        //     response.sendRedirect(request.getContextPath() + "/login");
        //     return null;
        // }

        // if(!authService.authorize(session, FieldUtility.ADMIN_ROLE)) {
        //     throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        // }

        List<SemesterSessionModel> sessionList = semesterSessionService.retrieveAll(); //retrieve all sessions
        request.setAttribute("sessions", sessionList);
        return "Manage/utilities";
    }

    //API to retrieve session detaul
    @GetMapping("/session/detail")
    @ResponseBody
    public Map<String, Object> utilityDetail(@RequestParam Map<String, Object> body, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> respMap = new HashMap<>();
        try {

            SemesterSessionModel session = semesterSessionService.retrieveDetail(body); //retrieve session by id

            if(session == null) {
                respMap.put("respCode", "00001");
                respMap.put("respStatus", "error");
                respMap.put("respMessage", "Session does not found!");
            }else{
                respMap.put("respCode", "00000");
                respMap.put("respStatus", "success");
                respMap.put("respMessage", "successfully retrieved");
            }
            respMap.put("data", session);

        }catch (Exception e) {
            respMap.put("respCode", "000198");
            respMap.put("respStatus", "error");
            respMap.put("respMessage", "Internal server error. Please contact admin for futher assistance");
            e.printStackTrace();
        }

        return respMap;
    }

    //activate the session
    @PostMapping("/session/activate")
    public void activate(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        try {
            //will do validation
            if(!authService.authenticate(session)) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            FieldUtility.requiredValidator(body, sessionRequiredFields());
            semesterSessionService.activateDisable(body, true);
        }catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", e.getMessage());
        }
        response.sendRedirect("/utility");
    }

    //disable the session
    @PostMapping("/session/disable")
    public void disable(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        try {
            //will do validation
            if(!authService.authenticate(session)) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            FieldUtility.requiredValidator(body, sessionRequiredFields());
            semesterSessionService.activateDisable(body, false);
        }catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", e.getMessage());
        }
        response.sendRedirect("/utility");
    }

    //disable the session
    @PostMapping("/session/update")
    public void update(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        try {
            //will do validation
            if(!authService.authenticate(session)) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            FieldUtility.requiredValidator(body, sessionRequiredFields2());
            semesterSessionService.update(body);
        }catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", e.getMessage());
        }
        response.sendRedirect("/utility");
    }

    //create new session
    @PostMapping("/session/create")
    public void create(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        try {
            //will do validation
            // if(!authService.authenticate(session)) {
            //     response.sendRedirect(request.getContextPath() + "/login");
            //     return;
            // }

            FieldUtility.requiredValidator(body, sessionRequiredFields3());
            semesterSessionService.insert(body);
        }catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", e.getMessage());
        }
        response.sendRedirect("/utility");
    }

    //delete session
    @PostMapping("/session/delete")
    public void delete(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        try {
            //will do validation
            if(!authService.authenticate(session)) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            FieldUtility.requiredValidator(body, sessionRequiredFields());
            if(!semesterSessionService.delete(body)) {
                throw new Exception("Failed to delete session");
            }
        }catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", e.getMessage());
        }
        response.sendRedirect("/utility");
    }

    private String [][] sessionRequiredFields() {
        return new String[][]{
                {"session_id", "Session id is required"},
        };
    }

    private String [][] sessionRequiredFields2() {
        return new String[][]{
                {"session_id", "Session id is required"},
                {"session_name", "Session name is required"}
        };
    }

    private String [][] sessionRequiredFields3() {
        return new String[][]{
                {"session_name", "Session name is required"}
        };
    }
}

