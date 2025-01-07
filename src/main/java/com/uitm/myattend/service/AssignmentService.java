package com.uitm.myattend.service;
import com.uitm.myattend.mapper.MapperUtility;
import com.uitm.myattend.model.AssignmentModel;
import com.uitm.myattend.model.AttendanceModel;
import com.uitm.myattend.model.ClassModel;
import com.uitm.myattend.model.CourseModel;
import com.uitm.myattend.model.SemesterSessionModel;
import com.uitm.myattend.repository.AssignmentRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final CourseService courseService;
    private final SemesterSessionService semesterSessionService;

    public AssignmentService(AssignmentRepository assignmentRepository,  
    CourseService courseService,
    SemesterSessionService semesterSessionService) {
        this.assignmentRepository = assignmentRepository;
        this.courseService = courseService;
        this.semesterSessionService = semesterSessionService;;
    }

    //retrieve all assignment
    public List<AssignmentModel> retrieveAll() {
        try {
            List<Map<String, String>> assignmentList = assignmentRepository.retrieve();

            List<AssignmentModel> assignmentModelList = new ArrayList<>();
            for(Map<String, String> assignment : assignmentList) {
                assignmentModelList.add((AssignmentModel) MapperUtility.mapModel(AssignmentModel.class, assignment));
                // AssignmentModel obj = (AssignmentModel) MapperUtility.mapModel(AssignmentModel.class, assignment);
                // assignmentModelList.add(obj);
            }
            return assignmentModelList;
        }catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    //retrieve assignment by course

    public List<AssignmentModel> retrieveByCourse(String courseId) {
        try {
            List<Map<String, String>> assignmentList = assignmentRepository.retrieveByCourse(courseId);
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
    // public AssignmentModel retrieveByCourse(String courseId) {
    //     try {

    //         List<Map<String, String>> assignmentList = assignmentRepository.retrieveByCourse(courseId);
    //         System.out.println("assignmentRepository : "+assignmentList);

    //         if(assignmentList.size() != 1) {
    //             throw new Exception("Data error on class list size : " + assignmentList.size());
    //         }

    //         AssignmentModel assignmentModel = (AssignmentModel) MapperUtility.mapModel(AssignmentModel.class, assignmentList.get(0));
    //         Map<String, Object> body = new HashMap<>();
    //         body.put("id", assignmentModel.getCourse_id());
    //         CourseModel courseModel = courseService.retrieveDetail(body);
    //         assignmentModel.setCourse(courseModel);
    //         SemesterSessionModel semesterSessionModel = semesterSessionService.retrieveDetail(body);
    //         assignmentModel.setSession(semesterSessionModel);
    //         return assignmentModel;

    //     }catch (Exception e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }

    //retrieve assignment by course

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



    //update assignment - including all cases ( disable,bypass,etc .. )



    //delete assignment

    public boolean delete(Map<String, Object> body) {
        try {
            String id = (String) body.get("id");

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