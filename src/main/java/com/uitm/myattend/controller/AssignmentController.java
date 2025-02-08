package com.uitm.myattend.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

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
import com.uitm.myattend.model.SemesterSessionModel;
import com.uitm.myattend.model.SubmissionModel;
import com.uitm.myattend.model.AssignmentModel;
import com.uitm.myattend.model.ClassModel;
import com.uitm.myattend.service.AuthService;
import com.uitm.myattend.service.CourseService;
import com.uitm.myattend.service.AssignmentService;
import com.uitm.myattend.service.SemesterSessionService;
import com.uitm.myattend.service.SubmissionService;
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
    private final SubmissionService submissionService;
    private final SemesterSessionService semesterSessionService;
    private final CommonModel commonModel;

    public AssignmentController(CourseService courseService,
        AuthService authService,
        AssignmentService assignmentService,
        SubmissionService submissionService,
        SemesterSessionService semesterSessionService,
        CommonModel commonModel) {
        this.courseService = courseService;
        this.authService = authService;
        this.assignmentService = assignmentService;
        this.submissionService = submissionService;
        this.semesterSessionService = semesterSessionService;
        this.commonModel = commonModel;
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

            // Validate file type for ass_attach
            if (file != null && !file.isEmpty() && !isValidFileType(file)) {
                session.setAttribute("error", "Please upload file with PDF, PNG, JPEG, or JPG type.");
                response.sendRedirect("/assignment/course?course=" + courseId);
                return;
            }

            if(assignmentService.insert(body, courseId, file)) {
                session.setAttribute("success", "New assignment successfully added");
            }else {
                session.setAttribute("error", "Internal server error. Please contact admin for further assistance");
            }
        }catch (Exception e) {
            session.setAttribute("error", e.getMessage());
        }
        // response.sendRedirect("/assignment");
        response.sendRedirect("/assignment/course?course=" + courseId);
    }

    //update assignment - including all cases ( disable,bypass,etc .. )

    @PostMapping("/update/{asssignment_id}")
    public void update(@RequestParam Map<String, Object> body,
    HttpServletResponse response,
    HttpServletRequest request,
    HttpSession session,
    @PathVariable("asssignment_id") String assignmentId,
    @RequestParam("ass_attach") MultipartFile file) throws IOException {
        try {
            //will do validation
            if(!authService.authenticate(session)) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // FieldUtility.requiredValidator(body, classRequiredFields());
            // Validate file type for ass_attach
            if (file != null && !file.isEmpty() && !isValidFileType(file)) {
                session.setAttribute("error", "Please upload file with PDF, PNG, JPEG, or JPG type.");
                response.sendRedirect("/assignment/course?course=" + (String) body.get("course_id"));
                return;
            }

            if(!assignmentService.update(body, assignmentId, file)) {
                throw new Exception("Failed to save data");
            }else {
                session.setAttribute("success", "Data saved");
            }
        }catch (Exception e) {
            session.setAttribute("error", e.getMessage());
        }
        response.sendRedirect("/assignment/course?course=" + (String) body.get("course_id"));
    }

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

    // assignment api - return json

    //retrieve assignment by course -> return JSON format

    @GetMapping("/api/course")
    @ResponseBody
    public Map<String, Object> retrieveByCourseJSON(@RequestParam Map<String, Object> body,HttpServletRequest request, HttpServletResponse response, HttpSession session) {
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
                respMap.put("respMessage", "Assignment does not found!");
            }else{
                respMap.put("respCode", "00000");
                respMap.put("respStatus", "success");
                respMap.put("respMessage", "successfully retrieved");

                //response hashmap to return as json
                Map<String, Object> tempMap = new HashMap<>();
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
    }

    //retrieve assignment by assignment id -> return JSON format

    @GetMapping("/api/assignment/{assignment_id}")
    @ResponseBody
    public Map<String, Object> retrieveByAssignmentJSON(@RequestParam Map<String, Object> body,
    HttpServletRequest request, 
    HttpServletResponse response, 
    HttpSession session,
    @PathVariable("assignment_id") String assignmentId) {
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
            List<AssignmentModel> assignmentList = assignmentService.retrieveDetail(assignmentId);
            if (body.containsKey("course_id") && body.get("course_id") != null) {
                body.put("id", body.get("course_id").toString());
            } else {
                respMap.put("respCode", "00001");
                respMap.put("respStatus", "error");
                respMap.put("respMessage", "Assignment does not found!");
            }            

            //error handling
            if(assignmentList == null ||  assignmentList.isEmpty()) {
                respMap.put("respCode", "00001");
                respMap.put("respStatus", "error");
                respMap.put("respMessage", "Assignment does not found!");
            }else{
                respMap.put("respCode", "00000");
                respMap.put("respStatus", "success");
                respMap.put("respMessage", "successfully retrieved");

                AssignmentModel assignment = assignmentList.get(0);
                
                body.put("id", assignment.getCourse_id());
                body.put("session_id", assignment.getSessionId());
                CourseModel courseModel = courseService.retrieveDetail(body);
                SemesterSessionModel sessionModel = semesterSessionService.retrieveDetail(body); 
                assignment.setCourse(courseModel);
                assignment.setSession(sessionModel);

                //response hashmap to return as json
                Map<String, Object> tempMap = new HashMap<>();
                tempMap.put("assignment", assignment);
                respMap.put("data", tempMap);
            }

        } catch (Exception e) {
            e.printStackTrace();
            respMap.put("respCode", "000198");
            respMap.put("respStatus", "error");
            respMap.put("respMessage", "Internal server error. Please contact admin for futher assistance");
        }
        return respMap;
    }

    //retrieve list of submissions by assignment id -> return JSON format

    @GetMapping("/api/submissions/{assignment_id}")
    @ResponseBody
    public Map<String, Object> retrieveByAssignmentJSON(@RequestParam Map<String, Object> body,
    HttpServletRequest request, 
    HttpServletResponse response, 
    HttpSession session,
    @PathVariable("assignment_id") int assignmentId) {
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
            List<SubmissionModel> submissionList = submissionService.retrieveDetail(assignmentId); 
            // List<SubmissionModel> submissionList = submissionService.retrieveAll(); 
            

            //error handling
            if(submissionList == null ||  submissionList.isEmpty()) {
                respMap.put("respCode", "00001");
                respMap.put("respStatus", "error");
                respMap.put("respMessage", "Submissons of this assignment does not found!");
            }else{
                respMap.put("respCode", "00000");
                respMap.put("respStatus", "success");
                respMap.put("respMessage", "successfully retrieved");

                List<AssignmentModel> assignmentList = assignmentService.retrieveDetail(Integer.toString(assignmentId));
                AssignmentModel assignment = assignmentList.get(0);
                // assignment.setSubmissions(submissionList);
                //response hashmap to return as json
                Map<String, Object> tempMap = new HashMap<>();
                tempMap.put("assignment", assignment);
                tempMap.put("submissions", submissionList);
                respMap.put("data", tempMap);
            }

        } catch (Exception e) {
            e.printStackTrace();
            respMap.put("respCode", "000198");
            respMap.put("respStatus", "error");
            respMap.put("respMessage", "Internal server error. Please contact admin for futher assistance");
        }
        return respMap;
    }

    //required field for student
    private String[][] assignmentRequiredFields() {
        return new String[][] {
                {"ass_title", "Assignment Title is required"},
                {"ass_desc", "Assignment Description is required"},
                // {"ass_attach", "Attachment is required"}, // maybe need multiple
                // {"ass_start", "Assignment Start Date and Time is required"}, 
                {"ass_end", "Assignment End Date and Time is required"}, 
        };
    }

    private boolean isValidFileType(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }
    
        // Get the file's original name and MIME type
        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();
    
        // Define allowed MIME types and extensions
        String[] allowedExtensions = {".pdf", ".png", ".jpeg", ".jpg"};
        String[] allowedMimeTypes = {"application/pdf", "image/png", "image/jpeg", "image/jpg"};
    
        // Check MIME type
        if (contentType != null && Arrays.asList(allowedMimeTypes).contains(contentType)) {
            return true;
        }
    
        // Check file extension
        if (fileName != null) {
            for (String extension : allowedExtensions) {
                if (fileName.toLowerCase().endsWith(extension)) {
                    return true;
                }
            }
        }
    
        return false;
    }
}
