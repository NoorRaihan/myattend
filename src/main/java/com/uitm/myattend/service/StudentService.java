package com.uitm.myattend.service;

import com.uitm.myattend.mapper.MapperUtility;
import com.uitm.myattend.model.LecturerModel;
import com.uitm.myattend.model.StudentModel;
import com.uitm.myattend.model.UserModel;
import com.uitm.myattend.repository.StudentRepository;
import com.uitm.myattend.utility.FieldUtility;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentModel> retrieveAll() {
        try {
            List<Map<String, String>> lectList = studentRepository.retrieve();

            List<StudentModel> studModelList = new ArrayList<>();
            for(Map<String, String> lect : lectList) {
                studModelList.add((StudentModel) MapperUtility.mapModel(StudentModel.class, lect));
            }
            return studModelList;
        }catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public StudentModel retrieveDetail(int uid) {
        try {
            List<Map<String, String>> studList = studentRepository.retrieveDetail(uid);

            if(studList.size() != 1) {
                throw new Exception("Student data retrieve error occured! StudentList size: " + studList.size());
            }

            Map<String, String> student = studList.get(0);
            return (StudentModel) MapperUtility.mapModel(StudentModel.class, student);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean insert(Map<String, Object> body) {
        try {
            StudentModel student = new StudentModel();
            String curYear = FieldUtility.getCurrentDate().substring(0,4);
            String studId = curYear + FieldUtility.generateUUID().substring(0,6);

            student.setUser_id(Integer.parseInt((String)body.get("uid")));
            student.setStud_id(Integer.parseInt(studId));
            student.setProgram((String)body.get("program"));
            student.setIntake((String)body.get("intake"));
            student.setSemester(Integer.parseInt((String) body.get("semester")));

            if(!studentRepository.insert(student)) {
                throw new Exception("Failed to insert student information");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Map<String, Object> body) {
        try {
            StudentModel student = new StudentModel();

            student.setUser_id(Integer.parseInt((String)body.get("uid")));
            student.setStud_id(Integer.parseInt((String)body.get("stud_id")));
            student.setProgram((String)body.get("program"));
            student.setIntake((String)body.get("intake"));
            student.setSemester((int)body.get("semester"));

            if(!studentRepository.update(student)) {
                throw new Exception("Failed to update student information");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editStudent(Map<String, Object> body) {
        try {
            int uid = Integer.parseInt((String) body.get("uid"));
            List<Map<String, String>> studentList = studentRepository.retrieveRaw(uid, null);

            if(studentList == null) {
                throw new Exception("Retrieve data error on student!");
            }

            if(studentList.size() > 1 ) {
                throw new Exception("Data error multiple rows on student!");
            }

            boolean flag = false;
            if(studentList.size() == 0) {
                flag = insert(body);
            }else {
                flag = update(body);
            }

            if(!flag) {
                throw new Exception("Failed to process student data");
            }

            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Map<String, Object> body) {
        try {
            int uid = Integer.parseInt((String) body.get("uid"));

            if(studentRepository.delete(uid, null)) {
                throw new Exception("Failed to delete student info");
            }

            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
