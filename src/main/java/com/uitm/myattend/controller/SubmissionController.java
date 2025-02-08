package com.uitm.myattend.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.uitm.myattend.mapper.MapperUtility;
import com.uitm.myattend.model.AssignmentModel;
import com.uitm.myattend.model.CommonModel;
import com.uitm.myattend.model.CourseModel;
import com.uitm.myattend.model.SemesterSessionModel;
import com.uitm.myattend.model.SubmissionModel;
import com.uitm.myattend.service.AssignmentService;
import com.uitm.myattend.service.AuthService;
import com.uitm.myattend.service.CourseService;
import com.uitm.myattend.service.SemesterSessionService;
import com.uitm.myattend.service.SubmissionService;
import com.uitm.myattend.utility.FieldUtility;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/submission")
public class SubmissionController {

    private final CourseService courseService;
    private final SubmissionService submissionService;
    private final AuthService authService;
    private final AssignmentService assignmentService;
    private final SemesterSessionService semesterSessionService;
    private final CommonModel commonModel;

    public SubmissionController(CourseService courseService,
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

    @GetMapping("/{course_id}")
    public String submissionMgt(HttpServletRequest request, 
    HttpServletResponse response, 
    HttpSession session,
    @PathVariable("course_id") String courseId)  throws ParseException {

        // return all aassignments with submissions as fk
        if(!authService.authenticate(session)) {
            return "redirect:" + request.getContextPath() + "/login";
        }

        if(!authService.authorize(session, FieldUtility.STUDENT_ROLE)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        // retrieve by course id from ma_course student ge using by student id
        List<AssignmentModel> assignmentList = assignmentService.retrieveByStudentCourse(courseId);
        // int activeAssignments = 0;
        // for (AssignmentModel assignmentModel : assignmentList) {
        //     if (assignmentModel.getSubmissions().isEmpty()) {
        //         activeAssignments++;
        //     }
        //     System.out.println(assignmentModel.getSubmissions().isEmpty());
        // }


        int activeAssignments = submissionService.getTotalActiveAssignments(assignmentList);
        request.setAttribute("assignments", assignmentList);
        request.setAttribute("totalAssignment", assignmentList.size());
        request.setAttribute("totalActiveAssignments", activeAssignments);

        return "Student/submissions";
    }

    //retrieve list of submissions by assignment id -> return JSON format

    @GetMapping("/api/assignment/{assignment_id}")
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

    //retrieve single submission detail by submission id -> return JSON format

    @GetMapping("/api/submission/{submission_id}")
    @ResponseBody
    public Map<String, Object> retrieveSubmissionDetailJSON(@RequestParam Map<String, Object> body,
    HttpServletRequest request, 
    HttpServletResponse response, 
    HttpSession session,
    @PathVariable("submission_id") int submissionId) {
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
            // SubmissionModel submissionModel = submissionService.retrieveBySubmission(submissionId); 
            List<SubmissionModel> submissionList = submissionService.retrieveBySubmission(submissionId); 
            SubmissionModel submission = submissionList.get(0);
            

            //error handling
            if(submissionList == null ||  submissionList.isEmpty()) {
                respMap.put("respCode", "00001");
                respMap.put("respStatus", "error");
                respMap.put("respMessage", "Submisson does not found!");
            }else{
                respMap.put("respCode", "00000");
                respMap.put("respStatus", "success");
                respMap.put("respMessage", "successfully retrieved");

                Map<String, Object> tempMap = new HashMap<>();
                tempMap.put("submission", submission);
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

    @PostMapping("/create/{assignment_id}/{course_id}")
    public void store(
        @RequestParam Map<String, Object> body,
        HttpServletResponse response,
        HttpServletRequest request,
        HttpSession session,
        @PathVariable("assignment_id") int assignmentId,
        @PathVariable("course_id") String courseId,
        @RequestParam("sub_attach") MultipartFile file
    ) throws IOException {

        // 0. check is assignment exist
        // 1. check is student id betul ?
        // 2. check is Assignment active
        
        try {
            if(!authService.authenticate(session)) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            FieldUtility.requiredValidator(body, submissionRequiredFields());

            // Validate file type for sub_attach
            if (file != null && !file.isEmpty() && !isValidFileType(file)) {
                session.setAttribute("error", "Please upload file with PDF, PNG, JPEG, or JPG type.");
                response.sendRedirect("/submission/" + courseId);
                return;
            }

            if(submissionService.insert(body, assignmentId, file)) {
                session.setAttribute("success", "New submission successfully added");
            }else {
                session.setAttribute("error", "Internal server error. Please contact admin for further assistance");
            }
        }catch (Exception e) {
            session.setAttribute("error", e.getMessage());
        }
        response.sendRedirect("/submission/" + courseId);
    }

    // update assignment

    @PostMapping("/update")
    public void update(@RequestParam Map<String, Object> body,
    HttpServletResponse response,
    HttpServletRequest request,
    HttpSession session) throws IOException {
        try {
            //will do validation
            if(!authService.authenticate(session)) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // FieldUtility.requiredValidator(body, classRequiredFields());

            if(!submissionService.updateSubsMark(body)) {
                throw new Exception("Failed to update data");
            }else {
                session.setAttribute("success", "Data updated");
            }
        }catch (Exception e) {
            session.setAttribute("error", e.getMessage());
        }
        // response.sendRedirect("/assignment/course?course=" + (String) body.get("course_id"));
        response.sendRedirect("/");
    }

    // delete submission
    @PostMapping("/delete")
    public void delete(@RequestParam Map<String, Object> body, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        String courseId = (String) body.get("course_id");
        // String submissionId = (String) body.get("sub_id");
        // String submissionFilename = (String) body.get("submission_filename");
        try {
            if(!authService.authenticate(session)) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            if(!submissionService.delete(body)) {
                throw new Exception("Failed to delete submission data");
            }else {
                session.setAttribute("success", "Submission data successfully deleted");
            }
        }catch (Exception e) {
            session.setAttribute("error", e.getMessage());
            e.printStackTrace();
        }
        response.sendRedirect("/submission/" + courseId);
    }

    //required field for submission
    private String[][] submissionRequiredFields() {
        return new String[][] {
            {"sub_desc", "Submission Description is required"},
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
