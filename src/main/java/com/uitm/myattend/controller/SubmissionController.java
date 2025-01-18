package com.uitm.myattend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @PathVariable("course_id") String courseId) {

        // return all aassignments with submissions as fk
        if(!authService.authenticate(session)) {
            return "redirect:" + request.getContextPath() + "/login";
        }

        if(!authService.authorize(session, FieldUtility.STUDENT_ROLE)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        // retrieve by course id from ma_course student ge using by student id
        List<AssignmentModel> assignmentList = assignmentService.retrieveByStudentCourse(courseId);
        request.setAttribute("assignments", assignmentList);
        request.setAttribute("totalAssignment", assignmentList.size());

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
}
