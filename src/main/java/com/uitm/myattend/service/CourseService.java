package com.uitm.myattend.service;

import com.uitm.myattend.mapper.MapperUtility;
import com.uitm.myattend.model.CourseModel;
import com.uitm.myattend.model.LecturerModel;
import com.uitm.myattend.model.StudentModel;
import com.uitm.myattend.repository.CourseRepository;
import com.uitm.myattend.utility.FieldUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final StudentService studentService;
    private final Environment env;

    @Autowired
    public CourseService(CourseRepository courseRepository, StudentService studentService, Environment env) {
        this.courseRepository = courseRepository;
        this.studentService = studentService;
        this.env = env;
    }

    public List<CourseModel> retrieveAll() {
        try {
            List<Map<String, String>> courseList = courseRepository.retrieve();

            List<CourseModel> courseModelList = new ArrayList<>();
            for(Map<String, String> course : courseList) {
                CourseModel courseObj = (CourseModel) MapperUtility.mapModel(CourseModel.class, course);
                courseObj.setColorConfig(env.getProperty("color." + courseObj.getColor()));
                courseModelList.add(courseObj);
            }
            return courseModelList;
        }catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public boolean insert(Map<String, Object> body) {
        try {
            CourseModel course = new CourseModel();

            course.setId(UUID.randomUUID().toString());
            course.setCourse_code((String) body.get("c_code"));
            course.setCourse_name((String) body.get("c_name"));

            String colorId = (String) body.get("c_color");

            if(env.getProperty("color." + colorId.toUpperCase()) == null) {
                throw new Exception("Failed to retrieve color properties");
            }
            course.setColor(colorId);

            course.setCredit_hour(Double.parseDouble((String) body.get("c_credit")));
            course.setUser_id(Integer.parseInt((String) body.get("c_lect")));

            if(!courseRepository.insert(course)) {
                throw new Exception("Failed to register a course");
            }

            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public CourseModel retrieveDetail(Map<String, Object> body) {
        try {
            List<Map<String, String>> courseList = courseRepository.retrieveDetail((String) body.get("id"));
            if(courseList.size() != 1) {
                throw new Exception("Course data retrieve error occured! CourseList size: " + courseList.size());
            }

            Map<String, String> course = courseList.get(0);
            CourseModel courseObj =  (CourseModel) MapperUtility.mapModel(CourseModel.class, course);
            courseObj.setColorConfig(env.getProperty("color." + courseObj.getColor()));
            return courseObj;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean update(Map<String, Object> body) {
        try {
            CourseModel course = new CourseModel();

            course.setId((String) body.get("id"));
            course.setCourse_code((String) body.get("c_code"));
            course.setCourse_name((String) body.get("c_name"));

            String colorId = (String) body.get("c_color");

            if(env.getProperty("color." + colorId.toUpperCase()) == null) {
                throw new Exception("Failed to retrieve color properties");
            }
            course.setColor(colorId);

            course.setCredit_hour(Double.parseDouble((String) body.get("c_credit")));
            course.setUser_id(Integer.parseInt((String) body.get("c_lect")));

            if(!courseRepository.update(course)) {
                throw new Exception("Failed to update course information");
            }

            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Map<String, Object> body) {
        try {
            String id = (String) body.get("id");

            if(!courseRepository.delete(id)) {
                throw new Exception("Failed to delete course info");
            }

            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean changeStatus(Map<String, Object> body, boolean isDisable) {
        try {
            String cid = (String) body.get("id");

            CourseModel courseModel = new CourseModel();
            courseModel.setId(cid);

            if(!courseRepository.changeStatus(courseModel, isDisable)) {
                throw new Exception("Failed to update course status");
            }

            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean registerStudent(Map<String, Object> body, boolean isRegister) {
        try {
            int uid = Integer.parseInt((String) body.get("uid"));
            String cid = (String) body.get("cid");

            StudentModel studentModel = studentService.retrieveDetail(uid);
            if(studentModel == null) {
                throw new Exception("Student data does not configured or does not exists");
            }

            boolean result;
            if(isRegister) {
                result = courseRepository.registerCourseStudent(uid, cid);
            }else {
                result = courseRepository.deleteCourseStudent(uid, cid);
            }
            return result;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<CourseModel> retrieveAvailableCourseStudent(int uid) {
        try {
            List<Map<String, String>> courseList = courseRepository.retrieveAvailableCourse(uid);
            List<CourseModel> courseModelList = new ArrayList<>();
            for(Map<String, String> course : courseList) {
                CourseModel courseObj = (CourseModel) MapperUtility.mapModel(CourseModel.class, course);
                courseObj.setColorConfig(env.getProperty("color." + courseObj.getColor()));
                courseModelList.add(courseObj);
            }
            return courseModelList;
        }catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<CourseModel> retrieveRegisteredCourseStudent(int uid) {
        try {
            List<Map<String, String>> courseList = courseRepository.retrieveRegisteredStudentCourse(uid);

            List<CourseModel> courseModelList = new ArrayList<>();
            for(Map<String, String> course : courseList) {
                CourseModel courseObj = (CourseModel) MapperUtility.mapModel(CourseModel.class, course);
                courseObj.setColorConfig(env.getProperty("color." + courseObj.getColor()));
                courseModelList.add(courseObj);
            }
            return courseModelList;
        }catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<CourseModel> retrieveCourseByLecturer(int uid) {
        try {
            List<Map<String, String>> courseList = courseRepository.retrieveByLecturer(uid);

            List<CourseModel> courseModelList = new ArrayList<>();
            for(Map<String, String> course : courseList) {
                CourseModel courseObj = (CourseModel) MapperUtility.mapModel(CourseModel.class, course);
                courseObj.setColorConfig(env.getProperty("color." + courseObj.getColor()));
                courseModelList.add(courseObj);
            }
            return courseModelList;
        }catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public boolean unregisterStudent(Map<String, Object> body) {
        try {
            int uid = Integer.parseInt((String) body.get("uid"));
            String cid = (String) body.get("cid");

            if(!courseRepository.deleteCourseStudent(uid, cid)) {
                throw new Exception("Failed to unregister student : " + uid);
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
