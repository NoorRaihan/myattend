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
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
    private final String uploadDirectory = "src/main/webapp/resources/uploads/submissions/";

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

    // @Transactional
    public int getTotalActiveAssignments(List<AssignmentModel> assignmentList) throws ParseException {
        int activeAssignments = 0;
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Formatters
        DateTimeFormatter formatterWithMillis = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        DateTimeFormatter formatterWithoutMillis = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime today = LocalDateTime.now();

        for (AssignmentModel assignmentModel : assignmentList) {
            try {
                LocalDateTime startedAt = parseDate(assignmentModel.getStarted_at(), formatterWithMillis, formatterWithoutMillis);
                LocalDateTime endedAt = parseDate(assignmentModel.getEnded_at(), formatterWithMillis, formatterWithoutMillis);

                // Check if assignment is active
                if (((endedAt.isAfter(today) || endedAt.isEqual(today)) &&
                        (startedAt.isBefore(today) || startedAt.isEqual(today)))
                        ) {
                    activeAssignments++;
                }
            } catch (DateTimeParseException e) {
                System.err.println("Error parsing date: " + assignmentModel.getStarted_at() + " | " + e.getMessage());
            }
        }
        return activeAssignments;
    }

    private LocalDateTime parseDate(String dateString, DateTimeFormatter formatterWithMillis, DateTimeFormatter formatterWithoutMillis) {
        try {
            return LocalDateTime.parse(dateString, formatterWithMillis); // Try parsing with milliseconds
        } catch (DateTimeParseException e) {
            return LocalDateTime.parse(dateString, formatterWithoutMillis); // If it fails, try without milliseconds
        }
    }

    //create submission
    public boolean insert(Map<String, Object> body, int assignmentId, MultipartFile file) {
        try {
            SubmissionModel submissionModel = new SubmissionModel();

            Integer userId = commonModel.getUser().getId();
            StudentModel studentModel = studentService.retrieveDetail(userId);
            int studentId = studentModel.getStud_id();
            // Generate unique ID for the assignment
            String uid = FieldUtility.generateUUID().substring(0, 8);
            String currTms = FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp());

            if (file != null && !file.isEmpty()) {

                // Process file data
                String fileName = file.getOriginalFilename();
                String fileExtension = "";

                if (fileName != null && fileName.contains(".")) {
                    fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1); // Get file extension
                }

                // Generate server file name
                String serverFileName = currTms + "_" + userId + "." + fileExtension;

                if (file.isEmpty()){
                    return false;
                }

                try {
                    final Path directory = Paths.get(this.uploadDirectory);
                    final Path filePth = Paths.get(this.uploadDirectory+serverFileName);

                    if(!Files.exists(directory)){
                        Files.createDirectories(directory);
                    }

                    Files.write(filePth, file.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }

                submissionModel.setOri_filename(fileName);
                submissionModel.setServer_filename(serverFileName);
            }
            // Set assignment details in the model
            submissionModel.setSubmission_id(Integer.parseInt(uid));
            submissionModel.setStudent_id(studentId);
            submissionModel.setAssignment_id(assignmentId);
            submissionModel.setStatus("SUBMITTED");
            submissionModel.setSubmission_text((String) body.get("sub_desc"));
            submissionModel.setFile_path("/submissions");

            // Insert the assignment into the database
            return submissionRepository.insert(submissionModel);

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false in case of error
        }
    }

    //update submission mark

    public boolean updateSubsMark(Map<String, Object> body) {
        try {
            String studentId = (String) body.get("stud_mark");
            String subsMark = (String) body.get("subs_stud_mark");

            if(!submissionRepository.update(studentId,subsMark)) {
                throw new Exception("Failed to update submission info");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //delete assignment

    public boolean delete(Map<String, Object> body) {
        try {
            String submissionId = (String) body.get("sub_id");
            String submissionFilename = (String) body.get("submission_filename");

            if(!submissionRepository.delete(submissionId, submissionFilename)) {
                throw new Exception("Failed to delete submission info");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}