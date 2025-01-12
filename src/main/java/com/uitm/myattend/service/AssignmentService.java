package com.uitm.myattend.service;
import com.uitm.myattend.mapper.MapperUtility;
import com.uitm.myattend.model.AssignmentModel;
import com.uitm.myattend.model.AttendanceModel;
import com.uitm.myattend.model.ClassModel;
import com.uitm.myattend.model.CommonModel;
import com.uitm.myattend.model.CourseModel;
import com.uitm.myattend.model.SemesterSessionModel;
import com.uitm.myattend.repository.AssignmentRepository;
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

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final CourseService courseService;
    private final SemesterSessionService semesterSessionService;
    private final CommonModel commonModel;
    private final String uploadDirectory = "src/main/webapp/resources/uploads/assignments/";

    public AssignmentService(AssignmentRepository assignmentRepository,  
    CourseService courseService,
    SemesterSessionService semesterSessionService,
    CommonModel commonModel) {
        this.assignmentRepository = assignmentRepository;
        this.courseService = courseService;
        this.semesterSessionService = semesterSessionService;
        this.commonModel = commonModel;
    }

    //retrieve all assignment
    public List<AssignmentModel> retrieveAll() {
        try {
            List<Map<String, String>> assignmentList = assignmentRepository.retrieve();

            List<AssignmentModel> assignmentModelList = new ArrayList<>();
            for(Map<String, String> assignment : assignmentList) {
                assignmentModelList.add((AssignmentModel) MapperUtility.mapModel(AssignmentModel.class, assignment));
            }
            return assignmentModelList;
        }catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    //retrieve assignment by course

    @Transactional
    public List<AssignmentModel> retrieveByCourse(String courseId) {
        try {
            List<Map<String, String>> assignmentList = assignmentRepository.retrieveByCourse(courseId, commonModel.getSessionModel().getId());
            // System.out.println("assignmentRepository : "+assignmentList);
    
            List<AssignmentModel> assignmentModelList = new ArrayList<>();
            for (Map<String, String> assignment : assignmentList) {
                assignmentModelList.add((AssignmentModel) MapperUtility.mapModel(AssignmentModel.class, assignment));
            }
            // System.out.println("assingnmentModelList :"+assignmentModelList);
            return assignmentModelList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public List<AssignmentModel> retrieveByCourseJSON(Map<String, Object> body) {
        try {

            String courseId = (String) body.get("course_id");
            String sessionId = (String) body.get("session_id");
            List<Map<String, String>> assignmentList = assignmentRepository.retrieveByCourse(courseId, sessionId);

            List<AssignmentModel> assignmentModelList = new ArrayList<>();
            for(Map<String, String> assMap : assignmentList) {
                assignmentModelList.add((AssignmentModel) MapperUtility.mapModel(AssignmentModel.class, assMap));
            }
            return assignmentModelList;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<AssignmentModel> retrieveBySession(String sessionId) {
        try {
            List<Map<String, String>> assignmentList = assignmentRepository.retrieveBySession(sessionId);
    
            List<AssignmentModel> assignmentModelList = new ArrayList<>();
            for (Map<String, String> assignment : assignmentList) {
                assignmentModelList.add((AssignmentModel) MapperUtility.mapModel(AssignmentModel.class, assignment));
            }
            return assignmentModelList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //retrieve assignment by assignment

    @Transactional
    public List<AssignmentModel> retrieveDetail(String assignmentId) {
        try {
            List<Map<String, String>> assignmentList = assignmentRepository.retrieveDetail(assignmentId);

            if(assignmentList.size() != 1) {
                throw new Exception("Data error on assignment list size : " + assignmentList.size());
            }
    
            List<AssignmentModel> assignmentModelList = new ArrayList<>();
            for (Map<String, String> assignment : assignmentList) {
                assignmentModelList.add((AssignmentModel) MapperUtility.mapModel(AssignmentModel.class, assignment));
            }
            return assignmentModelList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    

    //retrieve assignment detail
    // @Transactional
    // public AssignmentModel retrieveDetail(Map<String, Object> body) {
    //     try {

    //         String id = (String) body.get("id");
    //         List<Map<String, String>> assignmentList = assignmentRepository.retrieveDetail(id);

    //         if(assignmentList.size() != 1) {
    //             throw new Exception("Data error on assignment list size : " + assignmentList.size());
    //         }

    //         AssignmentModel assignmentModel = (AssignmentModel) MapperUtility.mapModel(AssignmentModel.class, assignmentList.get(0));
    //         body.put("id", assignmentModel.getCourse_id());
    //         CourseModel courseModel = courseService.retrieveDetail(body);
    //         assignmentModel.setCourse(courseModel);
    //         return assignmentModel;

    //     }catch (Exception e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }

    //retrieve assignment by mark_by which lect



    //retrieve assignment by session_id



    //retrieve assignment by assignment_id




    //create assignment
    public boolean insert(Map<String, Object> body, String courseId, MultipartFile file) {
        try {
            AssignmentModel assignmentModel = new AssignmentModel();

            // Generate unique ID for the assignment
            String uid = FieldUtility.generateUUID().substring(0, 8);
            String userId = Integer.toString(commonModel.getUser().getId());

            // Handle form data
            int isBypassTimeFlag = body.get("ass_late") != null && "1".equals(body.get("ass_late")) ? 1 : 0;
            int disabledFlag = body.get("disabled_flag") != null && "1".equals(body.get("disabled_flag")) ? 1 : 0;

            String startTimeStamp = FieldUtility.dateTimeLocal2Oracle((String) body.get("ass_start"));
            String endTimeStamp = FieldUtility.dateTimeLocal2Oracle((String) body.get("ass_end"));
            String currTms = FieldUtility.timestamp2Oracle(FieldUtility.getCurrentTimestamp());

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

            // Set assignment details in the model
            assignmentModel.setAssignment_id(Integer.parseInt(uid));
            assignmentModel.setSession_id(commonModel.getSessionModel().getId());
            assignmentModel.setCourse_id(courseId);
            assignmentModel.setAssignment_header((String) body.get("ass_title"));
            assignmentModel.setAssignment_desc((String) body.get("ass_desc"));
            assignmentModel.setDisabled_flag(disabledFlag);
            assignmentModel.setBypass_time_flag(isBypassTimeFlag);
            assignmentModel.setOri_filename(fileName);
            assignmentModel.setServer_filename(serverFileName);
            assignmentModel.setFile_path("/assignments");
            assignmentModel.setStarted_at(startTimeStamp);
            assignmentModel.setEnded_at(endTimeStamp);

            // Insert the assignment into the database
            return assignmentRepository.insert(assignmentModel);

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false in case of error
        }
    }


    //update assignment - including all cases ( disable,bypass,etc .. )



    //delete assignment

    public boolean delete(Map<String, Object> body) {
        try {
            String id = (String) body.get("ass_id");

            if(!assignmentRepository.delete(id)) {
                throw new Exception("Failed to delete assignment info");
            }

            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}