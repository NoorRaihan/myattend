package com.uitm.myattend.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.uitm.myattend.model.CommonModel;
import com.uitm.myattend.model.CourseModel;
import com.uitm.myattend.model.AssignmentModel;
import com.uitm.myattend.model.ClassModel;
import com.uitm.myattend.service.AuthService;
import com.uitm.myattend.service.CourseService;
import com.uitm.myattend.service.AssignmentService;
import com.uitm.myattend.utility.FieldUtility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/assignment")
public class AssignmentController {

    private final CourseService courseService;
    private final AuthService authService;
    private final AssignmentService assignmentService;

    public AssignmentController(CourseService courseService,
        AuthService authService,
        AssignmentService assignmentService
        ) {
        this.courseService = courseService;
        this.authService = authService;
        this.assignmentService = assignmentService;
    }

    @GetMapping("")
    public String assignmentMgt(HttpServletResponse response, HttpServletRequest request, HttpSession session) {

        if(!authService.authenticate(session)) {
            // response.sendRedirect(request.getContextPath() + "/login");
            // return null;
            return "redirect:" + request.getContextPath() + "/login";
        }
        if(!authService.authorize(session, FieldUtility.LECTURER_ROLE)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        CommonModel commonModel = (CommonModel) session.getAttribute("common");
        List<CourseModel> courseList = courseService.retrieveCourseByLecturer(commonModel.getUser().getId());
        request.setAttribute("courses", courseList);

        List<AssignmentModel> assignmentList = assignmentService.retrieveAll();
        request.setAttribute("assignments", assignmentList);
        request.setAttribute("totalAssignment", assignmentList.size());

        return "Lecturer/assignments";
    }

    //retrieve assignment by course -> return JSON format

    @GetMapping("/api/course")
    @ResponseBody
    public Map<String, Object> retrieveByCourse(@RequestParam Map<String, Object> body,HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Map<String, Object> respMap = new HashMap<>();
        try {
            // //authenticate request
            // if(!authService.authenticate(session)) {
            //     respMap.put("respCode", "00002");
            //     respMap.put("respStatus", "error");
            //     respMap.put("respMessage", "Unauthorized request");
            //     return respMap;
            // }

            //retrieve all assignments JSON
            List<AssignmentModel> assignmentList = assignmentService.retrieveByCourseJSON(body);
            if (body.containsKey("course_id") && body.get("course_id") != null) {
                body.put("id", body.get("course_id").toString());
            } else {
                respMap.put("respCode", "00001");
                respMap.put("respStatus", "error");
                respMap.put("respMessage", "Assignment List does not found!");
            }            
            // CourseModel courseModel = courseService.retrieveDetail(body);

            //error handling
            if(assignmentList == null ||  assignmentList.isEmpty()) {
                respMap.put("respCode", "00001");
                respMap.put("respStatus", "error");
                respMap.put("respMessage", "Assignment List does not found!");
            }else{
                respMap.put("respCode", "00000");
                respMap.put("respStatus", "success");
                respMap.put("respMessage", "successfully retrieved");

                //response hashmap to return as json
            Map<String, Object> tempMap = new HashMap<>();
            // tempMap.put("course", courseModel);
            tempMap.put("assignments", assignmentList);
            respMap.put("data", tempMap);
            }

        } catch (Exception e) {
            e.printStackTrace();
            respMap.put("respCode", "000198");
            respMap.put("respStatus", "error");
            respMap.put("respMessage", "Internal server error. Please contact admin for futher assistance");
        }
        return respMap;

        // return "Lecturer/assignments";
    }

    @GetMapping("/course")
    public String retrieveByCourse(
            @RequestParam("course") String courseId,  // Reads 'course' parameter directly
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session) {

        // Authentication
        if (!authService.authenticate(session)) {
            return "redirect:" + request.getContextPath() + "/login";
        }

        try {
            // Pass the course ID to the service
            List<AssignmentModel> assignmentList = assignmentService.retrieveByCourse(courseId);
            // System.out.println("assignmentService : "+assignmentList);

            CommonModel commonModel = (CommonModel) session.getAttribute("common");
            List<CourseModel> courseList = courseService.retrieveCourseByLecturer(commonModel.getUser().getId());
            request.setAttribute("courses", courseList);

            Map<String, Object> body = new HashMap<>();
            body.put("id", courseId);
            CourseModel courseModel = courseService.retrieveDetail(body);

            // Set data for the JSP
            request.setAttribute("assignments", assignmentList);
            request.setAttribute("course", courseModel);  // Optionally add course ID to request attributes

            // System.out.println("Assignments List size : "+assignmentList.size());

            // Return the view name
            return "Lecturer/assignments";

        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions and show an error page if needed
            return "error";
        }
    }

    //retrieve assignment by session_id

    @GetMapping("/session")
    public String retrieveBySession(
            @RequestParam("session") String sessionId,  // Reads 'course' parameter directly
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session) {

        // Authentication
        if (!authService.authenticate(session)) {
            return "redirect:" + request.getContextPath() + "/login";
        }

        try {
            // Pass the course ID to the service
            List<AssignmentModel> assignmentList = assignmentService.retrieveBySession(sessionId);

            // Set data for the JSP
            request.setAttribute("assignments", assignmentList);
            request.setAttribute("session", sessionId);  // Optionally add course ID to request attributes

            // Return the view name
            return "Test/Test";

        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions and show an error page if needed
            return "error";
        }
    }

    //retrieve assignment by assignment_id

    @GetMapping("/detail")
    public String retrieveByAssignment(
            @RequestParam("assignment") String assignmentId,  // Reads 'course' parameter directly
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session) {

        // Authentication
        if (!authService.authenticate(session)) {
            return "redirect:" + request.getContextPath() + "/login";
        }

        try {
            // Pass the course ID to the service
            List<AssignmentModel> assignmentList = assignmentService.retrieveDetail(assignmentId);

            // Set data for the JSP
            request.setAttribute("assignments", assignmentList);
            request.setAttribute("assignment", assignmentId);  // Optionally add course ID to request attributes

            // Return the view name
            return "Test/AssignmentDetail";

        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions and show an error page if needed
            return "error";
        }
    }

    @PostMapping("/create/{course_id}")
    public void store(
        @RequestParam Map<String, Object> body,
        HttpServletResponse response,
        HttpServletRequest request,
        HttpSession session,
        @PathVariable("course_id") String courseId,
        @RequestParam("ass_attach") MultipartFile file
    ) throws IOException {
        try {
            if(!authService.authenticate(session)) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            FieldUtility.requiredValidator(body, assignmentRequiredFields());
            if(assignmentService.insert(body, courseId, file)) {
                session.setAttribute("message", "New assignment successfully added");
            }else {
                session.setAttribute("message", "Internal server error. Please contact admin for further assistance");
            }
        }catch (Exception e) {
            session.setAttribute("error", e.getMessage());
        }
        // response.sendRedirect("/assignment");
        response.sendRedirect("/assignment/course?course=" + courseId);
    }

    //update assignment - including all cases ( disable,bypass,etc .. )



    //delete assignment

    @PostMapping("/delete")
    public void delete(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        // String assignmentId = (String) body.get("ass_id");
        String courseId = (String) body.get("course_id");
        try {
            if(!authService.authenticate(session)) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            if(!assignmentService.delete(body)) {
                throw new Exception("Failed to delete assignment data");
            }else {
                session.setAttribute("success", "Assignment data successfully deleted");
            }
        }catch (Exception e) {
            session.setAttribute("error", e.getMessage());
            e.printStackTrace();
        }
        response.sendRedirect("/assignment/course?course=" + courseId);
    }

    //required field for student
    private String[][] assignmentRequiredFields() {
        return new String[][] {
                {"ass_title", "Assignment Title is required"},
                {"ass_desc", "Assignment Description is required"},
                // {"ass_attach", "Attachment is required"}, // maybe need multiple
                {"ass_start", "Assignment Start Date and Time is required"}, 
                {"ass_end", "Assignment End Date and Time is required"}, 
        };
    }
}
