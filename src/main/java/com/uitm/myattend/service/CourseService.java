package com.uitm.myattend.service;

import com.uitm.myattend.mapper.MapperUtility;
import com.uitm.myattend.model.CourseModel;
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
    private final Environment env;

    @Autowired
    public CourseService(CourseRepository courseRepository, Environment env) {
        this.courseRepository = courseRepository;
        this.env = env;
    }

    public List<CourseModel> retrieveAll() {
        try {
            List<Map<String, String>> courseList = courseRepository.retrieve();

            List<CourseModel> courseModelList = new ArrayList<>();
            for(Map<String, String> course : courseList) {
                courseModelList.add((CourseModel) MapperUtility.mapModel(CourseModel.class, course));
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
            course.setCourse_code((String) body.get("course-code"));
            course.setCourse_name((String) body.get("course-name"));

            String colorId = (String) body.get("color");
            colorId = env.getProperty(colorId);
            course.setColor(colorId);

            course.setCredit_hour(Double.parseDouble((String) body.get("credit-hour")));
            course.setUser_id(Integer.parseInt((String) body.get("uid")));

            if(!courseRepository.insert(course)) {
                throw new Exception("Failed to register a course");
            }

            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
