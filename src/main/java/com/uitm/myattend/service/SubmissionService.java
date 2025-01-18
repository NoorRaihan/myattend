package com.uitm.myattend.service;
import com.uitm.myattend.mapper.MapperUtility;
import com.uitm.myattend.model.AssignmentModel;
import com.uitm.myattend.model.AttendanceModel;
import com.uitm.myattend.model.ClassModel;
import com.uitm.myattend.model.CommonModel;
import com.uitm.myattend.model.CourseModel;
import com.uitm.myattend.model.SemesterSessionModel;
import com.uitm.myattend.model.StudentModel;
import com.uitm.myattend.model.SubmissionModel;
import com.uitm.myattend.repository.AssignmentRepository;
import com.uitm.myattend.repository.SubmissionRepository;
import com.uitm.myattend.utility.FieldUtility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final CourseService courseService;
    private final SemesterSessionService semesterSessionService;
    private final CommonModel commonModel;
    private final StudentModel studentModel;
    private final StudentService studentService;
    private final String uploadDirectory = "src/main/webapp/resources/uploads/assignments/";

    public SubmissionService(SubmissionRepository submissionRepository,  
    CourseService courseService,
    SemesterSessionService semesterSessionService,
    CommonModel commonModel,
    StudentModel studentModel,
    StudentService studentService) {
        this.submissionRepository = submissionRepository;
        this.courseService = courseService;
        this.semesterSessionService = semesterSessionService;
        this.commonModel = commonModel;
        this.studentModel = studentModel;
        this.studentService = studentService;
    }

    //retrieve all submissions
    public List<SubmissionModel> retrieveAll() {
        try {
            List<Map<String, String>> submissionList = submissionRepository.retrieve();

            List<SubmissionModel> submissionModelList = new ArrayList<>();
            for(Map<String, String> submission : submissionList) {
                submissionModelList.add((SubmissionModel) MapperUtility.mapModel(SubmissionModel.class, submission));
            }
            return submissionModelList;
        }catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Transactional
    public List<SubmissionModel> retrieveDetail(int assignmentId) {
        try {
            List<Map<String, String>> submissionList = submissionRepository.retrieveDetail(assignmentId);
            // System.out.println("submissionList hoho");
            // System.out.println(submissionList);

            // if(submissionList.size() != 1) {
            //     throw new Exception("Data error on submission list size : " + submissionList.size());
            // }
    
            List<SubmissionModel> submissionModelList = new ArrayList<>();
            for (Map<String, String> submission : submissionList) {
                submissionModelList.add((SubmissionModel) MapperUtility.mapModel(SubmissionModel.class, submission));
            }
            return submissionModelList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public List<SubmissionModel> retrieveBySubmission(int submissionId) {
        try {
            List<Map<String, String>> submissionList = submissionRepository.retrieveSubmissionDetail(submissionId);
            System.out.println("submissionList hoho");
            System.out.println(submissionList);

            if(submissionList.size() != 1) {
                throw new Exception("Data error on submission list size : " + submissionList.size());
            }
    
            List<SubmissionModel> submissionModelList = new ArrayList<>();
            for (Map<String, String> submission : submissionList) {
                submissionModelList.add((SubmissionModel) MapperUtility.mapModel(SubmissionModel.class, submission));
            }
            return submissionModelList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}