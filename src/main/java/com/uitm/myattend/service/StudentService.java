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
            student.setProgram(null);
            student.setIntake("2023/2");
            student.setSemester(2);

            studentRepository.insert(student);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
