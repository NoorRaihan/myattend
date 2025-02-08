package com.uitm.myattend.service;

import com.uitm.myattend.mapper.MapperUtility;
import com.uitm.myattend.model.CommonModel;
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
    private final CommonModel commonModel;

    public StudentService(StudentRepository studentRepository, CommonModel commonModel) {
        this.studentRepository = studentRepository;
        this.commonModel = commonModel;
    }

    //retrieve all student
    public List<StudentModel> retrieveAll() {
        try {
            List<Map<String, String>> studList = studentRepository.retrieve();

            List<StudentModel> studModelList = new ArrayList<>();
            for(Map<String, String> stud : studList) {
                studModelList.add((StudentModel) MapperUtility.mapModel(StudentModel.class, stud));
            }
            return studModelList;
        }catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    //retrieve student detail
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

    //insert new student
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

    //update studet data
    public boolean update(Map<String, Object> body) {
        try {
            StudentModel student = new StudentModel();

            student.setUser_id(Integer.parseInt((String)body.get("uid")));
            student.setStud_id(Integer.parseInt((String)body.get("stud_id")));
            student.setProgram((String)body.get("program"));
            student.setIntake((String)body.get("intake"));
            student.setSemester(Integer.parseInt((String)body.get("semester")));

            if(!studentRepository.update(student)) {
                throw new Exception("Failed to update student information");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //edit student data
    public boolean editStudent(Map<String, Object> body) {
        try {

            //retrieve raw user data
            int uid = Integer.parseInt((String) body.get("uid"));
            List<Map<String, String>> studentList = studentRepository.retrieveRaw(uid, null);

            //error handling
            if(studentList == null) {
                throw new Exception("Retrieve data error on student!");
            }

            if(studentList.size() > 1 ) {
                throw new Exception("Data error multiple rows on student!");
            }

            //if data not existed yet since the listing using right join to user table then need to insert into student table
            boolean flag = false;
            if(studentList.isEmpty()) {
                flag = insert(body);
            }else {
                body.put("stud_id", studentList.get(0).get("stud_id"));
                flag = update(body); //update the student info instead if the data already in student table
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

    //delete student data
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

    public List<StudentModel> retrieveByCourse(Map<String, Object> body) {
        try {

            String cid = (String) body.get("id");
            List<Map<String, String>> studentList = studentRepository.retrieveByCourse(cid, commonModel.getSessionModel().getId());

            List<StudentModel> studModelList = new ArrayList<>();
            for(Map<String, String> studMap : studentList) {
                studModelList.add((StudentModel) MapperUtility.mapModel(StudentModel.class, studMap));
            }
            return studModelList;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //retrieve the student detail with course
    public StudentModel retrieveDetailByCourse(String cid, int uid) {
        try {
            List<Map<String, String>> studentList = studentRepository.retrieveDetailByCourse(cid, uid, commonModel.getSessionModel().getId());

            if(studentList.isEmpty()) {
                return null;
            }
            return (StudentModel) MapperUtility.mapModel(StudentModel.class, studentList.get(0));
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
