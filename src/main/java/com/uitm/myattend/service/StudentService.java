package com.uitm.myattend.service;

import com.uitm.myattend.model.StudentModel;
import com.uitm.myattend.model.UserModel;
import com.uitm.myattend.repository.StudentRepository;
import com.uitm.myattend.utility.FieldUtility;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public boolean insert(UserModel user, Map<String, Object> body) {
        try {
            StudentModel student = new StudentModel();
            String studId = FieldUtility.getCurrentDate().substring(0,4) + FieldUtility.generateUUID().substring(0,6);

            student.setUser_id(user.getId());
            student.setStud_id(Integer.parseInt(studId));
            student.setProgram((String)body.get("program"));
            student.setIntake((String)body.get("intake"));
            student.setSemester((int)body.get("semester"));

            if(!studentRepository.insert(student)) {
                throw new Exception("Failed to register student information");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
