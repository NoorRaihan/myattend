package com.uitm.myattend.service;

import com.uitm.myattend.mapper.MapperUtility;
import com.uitm.myattend.model.AttendanceModel;
import com.uitm.myattend.model.ClassModel;
import com.uitm.myattend.model.StudentModel;
import com.uitm.myattend.repository.ClassRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClassService {

    private final ClassRepository classRepository;
    private final StudentService studentService;
    private final AttendanceService attendanceService;

    public ClassService(ClassRepository classRepository, StudentService studentService, AttendanceService attendanceService) {
        this.classRepository = classRepository;
        this.studentService = studentService;
        this.attendanceService = attendanceService;
    }

    public List<ClassModel> retrieveByCourse(Map<String, Object> body) {
        try {

            String cid = (String) body.get("id");
            List<Map<String, String>> classList = classRepository.retrieveByCourse(cid);

            List<ClassModel> classModelList = new ArrayList<>();
            for(Map<String, String> classMap : classList) {
                classModelList.add((ClassModel) MapperUtility.mapModel(ClassModel.class, classMap));
            }
            return classModelList;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ClassModel retrieveDetail(Map<String, Object> body) {
        try {

            String cid = (String) body.get("id");
            List<Map<String, String>> classList = classRepository.retrieveByCourse(cid);

            if(classList.size() != 1) {
                throw new Exception("Data error on class list size : " + classList.size());
            }

            return (ClassModel) MapperUtility.mapModel(ClassModel.class, classList.get(0));

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean insert(Map<String, Object> body) {
        String uuid = UUID.randomUUID().toString();
        boolean created = false;
        try {
            ClassModel classModel = new ClassModel();
            String classDate = (String) body.get("class_date");
            String startTime = classDate + " " + (String) body.get("start_time") + ":00";
            String endTime = classDate + " " + (String) body.get("end_time") + ":00";

            classModel.setId(uuid);
            classModel.setCourse_id((String) body.get("cid"));
            classModel.setClass_desc((String) body.get("class_desc"));
            classModel.setClass_date(classDate);
            classModel.setStart_time(startTime);
            classModel.setEnd_time(endTime);
            classModel.setVenue((String) body.get("venue"));

            if(!classRepository.insert(classModel)) {
                throw new Exception("Failed to register a new class");
            }
            // attendance auto process will handle by sql trigger event not by program
//            created = true;
//            Map<String, Object> tempMap = new HashMap<>();
//            tempMap.put("id", body.get("cid"));
//
//            List<StudentModel> studentList = studentService.retrieveByCourse(tempMap);
//
//            for(StudentModel student : studentList) {
//                AttendanceModel attendanceModel = new AttendanceModel();
//                attendanceModel.setId(UUID.randomUUID().toString());
//                attendanceModel.setClass_id(uuid);
//                attendanceModel.setStud_id(student.getUser_id());
//                attendanceModel.setStatus("AB");
//
//                if(!attendanceService.insert(attendanceModel)) {
//                    throw new Exception("Failed to process attendance");
//                }
//            }
            return true;
        }catch (Exception e) {
            //delete class -> will cascade to delete all attendance
            if(created) {
                classRepository.delete(uuid);
            }
            e.printStackTrace();
            return false;
        }
    }

}
