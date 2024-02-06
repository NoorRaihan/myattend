package com.uitm.myattend.mapper;

import com.uitm.myattend.model.*;
import com.uitm.myattend.utility.FieldUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.Map;
import java.util.TreeMap;

public class MapperUtility {

    public static Object mapModel(Class<?> modelClass, Map<String, String> data) throws Exception{
        String className = modelClass.getSimpleName();
        Object obj = null;

        //convert into case insensitive mapping
        TreeMap<String, String> tempMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        tempMap.putAll(data);

        switch(className.toUpperCase()) {
            case "USERMODEL" -> obj = userMapper(tempMap);
            case "LECTURERMODEL" -> obj = lecturerMapper(tempMap);
            case "STUDENTMODEL" -> obj = studentMapper(tempMap);
            case "COURSEMODEL" -> obj = courseModel(tempMap);
            case "CLASSMODEL" -> obj = classModel(tempMap);
            case "ATTENDANCEMODEL" -> obj = attendanceModel(tempMap);
            case "ROLEMODEL" -> obj = roleMapper(tempMap);
            default -> throw new Exception("Invalid class");
        }

        return obj;
    }

    private static UserModel userMapper(TreeMap<String, String> data) {
        UserModel userObj = new UserModel();

        if(data.containsKey("UID")) {
            userObj.setId(Integer.parseInt(data.get("UID") == null ? "-1" : data.get("UID")));
        }else{
            userObj.setId(Integer.parseInt(data.get("ID") == null ? "-1" : data.get("ID")));
        }

        userObj.setUsername(data.get("USERNAME"));
        userObj.setEmail(data.get("EMAIL"));
        userObj.setPassword(data.get("PASSWORD"));
        userObj.setFullname(data.get("FULL_NAME"));
        userObj.setGender(data.get("GENDER"));
        userObj.setBirth_date(data.get("BIRTH_DATE"));
        userObj.setProfile_pic(data.get("PROFILE_PIC"));
        userObj.setRole_id(Integer.parseInt(data.get("ROLE_ID") == null ? "-1" : data.get("ROLE_ID")));

        TreeMap<String, String> roleMap = new TreeMap<>();
        roleMap.put("ID", data.get("ROLE_ID"));
        roleMap.put("ROLE_NAME", data.get("ROLE_NAME"));
        RoleModel role = roleMapper(roleMap);
        userObj.setRole(role);
        return userObj;
    }

    private static RoleModel roleMapper(TreeMap<String, String> data) {
        RoleModel roleObj = new RoleModel();
        roleObj.setId(data.get("ID"));
        roleObj.setRole_name(data.get("ROLE_NAME"));
        return roleObj;
    }

    private static LecturerModel lecturerMapper(TreeMap<String, String> data) {
        LecturerModel lectObj = new LecturerModel();
        lectObj.setUser_id(Integer.parseInt(data.get("USER_ID") == null ? "-1" : data.get("USER_ID")));
        lectObj.setLect_id(Integer.parseInt(data.get("LECT_ID") == null ? "-1" : data.get("LECT_ID")));
        lectObj.setSupervisor_id(Integer.parseInt(data.get("SUPERVISOR_ID") == null || data.get("SUPERVISOR_ID").isEmpty() ? "-1" : data.get("SUPERVISOR_ID")));
        lectObj.setStart_date(FieldUtility.checkNullDate(data.get("START_DATE")));
        lectObj.setQualification(FieldUtility.checkNull(data.get("QUALIFICATION")));
        lectObj.setSalary(Double.parseDouble(data.get("SALARY") == null ? "0.00" : data.get("SALARY")));

        UserModel userObj = userMapper(data);
        lectObj.setUser(userObj);

        return lectObj;
    }

    private static StudentModel studentMapper(TreeMap<String, String> data) {
        StudentModel studObj = new StudentModel();
        studObj.setUser_id(Integer.parseInt(data.get("USER_ID") == null ? "-1" : data.get("USER_ID")));
        studObj.setStud_id(Integer.parseInt(data.get("STUD_ID") == null ? "-1" : data.get("STUD_ID")));
        studObj.setSemester(Integer.parseInt(data.get("SEMESTER") == null ? "-1" : data.get("SEMESTER")));
        studObj.setProgram(FieldUtility.checkNull(data.get("PROGRAM")));
        studObj.setIntake(FieldUtility.checkNull(data.get("INTAKE")));

        UserModel userObj = userMapper(data);
        studObj.setUser(userObj);
        return studObj;
    }

    private static CourseModel courseModel(TreeMap<String, String> data) {
        CourseModel courseObj = new CourseModel();

        if(data.containsKey("COURSE_ID")) {
            courseObj.setId(data.get("COURSE_ID"));
        }else{
            courseObj.setId(data.get("ID"));
        }

        courseObj.setCourse_code(data.get("COURSE_CODE"));
        courseObj.setCourse_name(data.get("COURSE_NAME"));
        courseObj.setColor(data.get("COLOR"));

        courseObj.setUser_id(Integer.parseInt(data.get("USER_ID") == null ? "-1" : data.get("USER_ID")));
        courseObj.setCredit_hour(Double.parseDouble(data.get("CREDIT_HOUR") == null ? "0.00" : data.get("CREDIT_HOUR")));
        courseObj.setDeleted(FieldUtility.checkNullDate(data.get("DELETED_AT")));

        if(data.containsKey("USERNAME")) {
            UserModel userObj = userMapper(data);
            courseObj.setUser(userObj);
        }
        return courseObj;
    }

    private static ClassModel classModel(TreeMap<String, String> data) {
        ClassModel classObj = new ClassModel();

        classObj.setId(FieldUtility.checkNull(data.get("ID")));
        classObj.setCourse_id(FieldUtility.checkNull(data.get("COURSE_ID")));
        classObj.setClass_desc(FieldUtility.checkNull(data.get("CLASS_DESC")));
        classObj.setClass_date(FieldUtility.checkNullDate(data.get("CLASS_DATE")));
        classObj.setStart_time(FieldUtility.checkNullDate(data.get("START_TIME")));
        classObj.setEnd_time(FieldUtility.checkNullDate(data.get("END_TIME")));
        classObj.setVenue(FieldUtility.checkNull(data.get("VENUE")));
        classObj.setDeleted_at(FieldUtility.checkNullDate(data.get("DELETED_AT")));

        if(data.containsKey("COURSE_CODE")) {
            CourseModel courseObj = courseModel(data);
            classObj.setCourse(courseObj);
        }
        return classObj;
    }

    private static AttendanceModel attendanceModel(TreeMap<String, String> data) {
        AttendanceModel attendanceModel = new AttendanceModel();

        attendanceModel.setId(data.get("ID"));
        attendanceModel.setClass_id(data.get("CLASS_ID"));
        attendanceModel.setStud_id(Integer.parseInt(data.get("STUD_ID")));
        attendanceModel.setAttend_date(data.get("ATTEND_DATE"));
        attendanceModel.setAttend_time(data.get("ATTEND_TIME"));
        attendanceModel.setStatus(data.get("STATUS"));

        if(data.containsKey("CLASS_DESC")) {
            ClassModel classModel = classModel(data);
            attendanceModel.setClassModel(classModel);
        }

        if(data.containsKey("STUD_ID")) {
            StudentModel studentModel = studentMapper(data);
            attendanceModel.setStudent(studentModel);
        }

        return attendanceModel;
    }
}
