package com.uitm.myattend.service;

import com.uitm.myattend.model.AttendanceModel;
import com.uitm.myattend.model.ClassModel;
import com.uitm.myattend.model.StudentModel;
import com.uitm.myattend.model.UserModel;
import com.uitm.myattend.repository.AttendanceRepository;
import com.uitm.myattend.utility.FieldUtility;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserService userService;
    private final StudentService studentService;
    private final ClassService classService;
    private final Environment env;

    public AttendanceService(AttendanceRepository attendanceRepository, Environment env, ClassService classService, UserService userService, StudentService studentService) {
        this.attendanceRepository = attendanceRepository;
        this.userService = userService;
        this.studentService = studentService;
        this.env = env;
        this.classService = classService;
    }

    public boolean insert(AttendanceModel attendance) {
        try {

            if(!attendanceRepository.insert(attendance)) {
                throw new Exception("Failed to process an attendance");
            }

            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkAttendance(Map<String, Object> body) throws Exception{
        String data = ((String) body.get("data"));
        String [] dataArr = data.split("\\.");

        if(dataArr.length != 3) {
            throw new Exception("Invalid request! Insufficient parameter");
        }

        String ind = dataArr[0];
        String b64 = dataArr[1];
        String sessId = dataArr[2];

        String tmp = userService.retrieveToken(sessId).get(0).get("user_id");

        if(tmp == null) {
            throw new Exception("Invalid session request");
        }

        int uid = Integer.parseInt(tmp);

        if(!ind.equals("myattend")) {
            throw new Exception("Invalid indicator");
        }

        String decrypted = FieldUtility.decryptStringBase64(env.getProperty("app.key"), b64);
        String [] decryptArr = decrypted.split("\\.");
        String classId = decryptArr[0];
        long qrTms = Long.parseLong(decryptArr[1]);

        //validate class existing and time validality
        Map<String, Object> classMap = new HashMap<>();
        classMap.put("id", classId);
        ClassModel classModel = classService.retrieveDetail(classMap);

        if(classModel == null) {
            throw new Exception("Invalid class id!");
        }

        StudentModel studentModel = studentService.retrieveDetailByCourse(classModel.getCourse_id(), uid);
        if(studentModel == null) {
            throw new Exception("Student does not enroll in the course");
        }

        long currTms = Long.parseLong(FieldUtility.getCurrentTimestamp());
        long classStart = Long.parseLong(FieldUtility.getFormatted(classModel.getStart_time(), "yyyy-MM-dd h:m:s", "yyyyMMddHHmmssSSS"));
        long classEnd = Long.parseLong(FieldUtility.getFormatted(classModel.getEnd_time(), "yyyy-MM-dd h:m:s", "yyyyMMddHHmmssSSS"));

        if(currTms < classStart) {
            throw new Exception("Class has not started yet");
        }

        if(currTms > classEnd) {
            throw new Exception("Class attendance already expired");
        }

        //each qr only valid for 1 minute
        if(currTms > (qrTms + 100000)) {
            throw new Exception("Qr ID has expired");
        }

        //mark as attend the class
        if(!attendanceRepository.update(classId, uid, "C")) {
            throw new Exception("Failed to update the attendance");
        }
        return true;
    }
}
