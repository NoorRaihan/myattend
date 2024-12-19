package com.uitm.myattend.service;

import com.uitm.myattend.mapper.MapperUtility;
import com.uitm.myattend.model.*;
import com.uitm.myattend.repository.ClassRepository;
import com.uitm.myattend.utility.FieldUtility;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ClassService {

    private final ClassRepository classRepository;
    private final CourseService courseService;
    private final AttendanceService attendanceService;
    private final CommonModel commonModel;
    private final Environment env;

    public ClassService(ClassRepository classRepository, Environment env, CourseService courseService, AttendanceService attendanceService, CommonModel commonModel) {
        this.classRepository = classRepository;
        this.env = env;
        this.courseService = courseService;
        this.attendanceService = attendanceService;
        this.commonModel = commonModel;
    }

    //retrieve class list by course
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

    //retrieve class detail
    @Transactional
    public ClassModel retrieveDetail(Map<String, Object> body) {
        try {

            String id = (String) body.get("id");
            List<Map<String, String>> classList = classRepository.retrieveDetail(id);

            if(classList.size() != 1) {
                throw new Exception("Data error on class list size : " + classList.size());
            }

            ClassModel classModel = (ClassModel) MapperUtility.mapModel(ClassModel.class, classList.get(0));
            body.put("id", classModel.getCourse_id());
            CourseModel courseModel = courseService.retrieveDetail(body);
            classModel.setCourse(courseModel);
            return classModel;

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    //insert new class
    public boolean insert(Map<String, Object> body) {
        String uuid = UUID.randomUUID().toString();
        try {
            ClassModel classModel = new ClassModel();
            //format conversion from front to backend standard
            String classDate = FieldUtility.getFormatted((String) body.get("class_date"), "yyyy-MM-dd", "yyyyMMdd");
            String startTime = classDate + ((String) body.get("start_time")).replace(":", "") + "00000";
            String endTime = classDate + ((String) body.get("end_time")).replace(":", "") + "00000";

            classModel.setId(uuid);
            classModel.setCourse_id((String) body.get("course_id"));
            classModel.setClass_desc((String) body.get("class_desc"));
            classModel.setClass_date(classDate);
            classModel.setStart_time(startTime);
            classModel.setEnd_time(endTime);
            classModel.setVenue((String) body.get("venue"));

            if(!classRepository.insert(classModel, commonModel.getSessionModel().getId())) {
                throw new Exception("Failed to register a new class");
            }
            attendanceService.registerAttendance(Objects.requireNonNull(body.get("course_id")).toString(), classModel.getId());

            return true;
        }catch (Exception e) {
            //delete class -> will cascade to delete all attendanc
            throw new RuntimeException(e);
        }
    }


    //generate unique id for each qr
    public Map<String, Object> generateAttendanceUnique(Map<String, Object> body) {
        try {
            String encrypted;
            Map<String, Object> respMap = new HashMap<>();
            //retrieve class detail
            ClassModel classModel = retrieveDetail(body);
            //generate the pattern
            //pattern : {classID}.{currentTimestamp}
            String buildURL = classModel.getId() + "." + FieldUtility.getCurrentTimestamp();
            //encrypted the original pattern and build new pattern
            //pattern: myattend.{encryptedBASE64}
            encrypted = "myattend" + "." + FieldUtility.encryptStringBase64(env.getProperty("app.key"),buildURL);
            respMap.put("class", classModel);
            respMap.put("attendanceURL", encrypted);

            return respMap;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //update the class data
    public boolean update(Map<String, Object> body) {
        try {
            ClassModel classModel = new ClassModel();
            String classDate = FieldUtility.getFormatted((String) body.get("class_date"), "yyyy-MM-dd", "yyyyMMdd");
            String startTime = classDate + ((String) body.get("start_time")).replace(":", "") + "00000";
            String endTime = classDate + ((String) body.get("end_time")).replace(":", "") + "00000";

            classModel.setId((String) body.get("id"));
            classModel.setCourse_id((String) body.get("course_id"));
            classModel.setClass_desc((String) body.get("class_desc"));
            classModel.setClass_date(classDate);
            classModel.setStart_time(startTime);
            classModel.setEnd_time(endTime);
            classModel.setVenue((String) body.get("venue"));

            if(!classRepository.update(classModel)) {
                throw new Exception("Failed to update class info");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //delete class data
    public boolean delete(Map<String, Object> body) {
        try {
            String id = (String) body.get("id");
            if(!classRepository.delete(id)) {
                throw new Exception("Failed to delete class");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //retrieve active class
    public List<ClassModel> retrieveActive(HttpSession session) {
        try {
            CommonModel commonModel = (CommonModel) session.getAttribute("common");
            String currTms = FieldUtility.getCurrentTimestamp();
            String currDate = FieldUtility.getCurrentDate();

            List<Map<String, String>> classList = new ArrayList<>();
            if(commonModel.getUser().getRole_id() == FieldUtility.ADMIN_ROLE) {
                classList = classRepository.retrieveActive(currDate, currTms, commonModel.getSessionModel().getId());
            }else if(commonModel.getUser().getRole_id() == FieldUtility.STUDENT_ROLE) {
                classList = classRepository.retrieveActiveStudent(currDate, currTms, commonModel.getUser().getId(), commonModel.getSessionModel().getId());
            }else if(commonModel.getUser().getRole_id() == FieldUtility.LECTURER_ROLE) {
                classList = classRepository.retrieveActiveLecturer(currDate, currTms, commonModel.getUser().getId(), commonModel.getSessionModel().getId());
            }

            List<ClassModel> activeList = new ArrayList<>();
            for(Map<String, String> data : classList) {
                ClassModel obj = (ClassModel) MapperUtility.mapModel(ClassModel.class, data);
                obj.getCourse().setColorConfig(env.getProperty("color." + obj.getCourse().getColor()));
                activeList.add(obj);
            }
            return activeList;
        }catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    //retrieve today class
    public List<ClassModel> retrieveToday(HttpSession session) {
        try {
            CommonModel commonModel = (CommonModel) session.getAttribute("common");
            String currDate = FieldUtility.getCurrentDate();

            List<Map<String, String>> classList = new ArrayList<>();
            if(commonModel.getUser().getRole_id() == FieldUtility.ADMIN_ROLE) {
                classList = classRepository.retrieveToday(currDate, commonModel.getSessionModel().getId());
            }else if(commonModel.getUser().getRole_id() == FieldUtility.STUDENT_ROLE) {
                classList = classRepository.retrieveTodayStudent(currDate, commonModel.getUser().getId(), commonModel.getSessionModel().getId());
            }else if(commonModel.getUser().getRole_id() == FieldUtility.LECTURER_ROLE) {
                classList = classRepository.retrieveTodayLecturer(currDate, commonModel.getUser().getId(), commonModel.getSessionModel().getId());
            }

            List<ClassModel> activeList = new ArrayList<>();
            for(Map<String, String> data : classList) {
                activeList.add((ClassModel) MapperUtility.mapModel(ClassModel.class, data));
            }
            return activeList;
        }catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    //retrieve all classes
    public List<ClassModel> retrieveAll(int uid) {
        try {
            String currDate = FieldUtility.getCurrentDate();

            List<Map<String, String>> classList = classRepository.retrieveAllStudent(currDate, uid, commonModel.getSessionModel().getId());
            List<ClassModel> activeList = new ArrayList<>();
            for(Map<String, String> data : classList) {
                activeList.add((ClassModel) MapperUtility.mapModel(ClassModel.class, data));
            }
            return activeList;
        }catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
