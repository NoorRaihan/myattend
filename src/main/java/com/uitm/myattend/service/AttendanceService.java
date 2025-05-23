package com.uitm.myattend.service;

import com.uitm.myattend.mapper.MapperUtility;
import com.uitm.myattend.model.*;
import com.uitm.myattend.repository.AttendanceRepository;
import com.uitm.myattend.utility.FieldUtility;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserService userService;
    private final StudentService studentService;
    private final ClassService classService;
    private final CourseService courseService;
    private final Environment env;
    private ResourceLoader resourceLoader;

    public AttendanceService(AttendanceRepository attendanceRepository,
                             Environment env,
                             ClassService classService,
                             UserService userService,
                             StudentService studentService,
                             ResourceLoader resourceLoader,
                             CourseService courseService) {
        this.attendanceRepository = attendanceRepository;
        this.userService = userService;
        this.studentService = studentService;
        this.env = env;
        this.classService = classService;
        this.resourceLoader =resourceLoader;
        this.courseService = courseService;
    }

    //insert new attendance
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

    //main function to check the attendance when student scan the qr
    //the pattern: myattend.{encryptedBASE64}.sessionID
    public boolean checkAttendance(Map<String, Object> body) throws Exception{
        String data = ((String) body.get("data"));
        String [] dataArr = data.split("\\.");

        if(dataArr.length != 3) {
            throw new Exception("Invalid request! Insufficient parameter");
        }

        //get the data
        String ind = dataArr[0];
        String b64 = dataArr[1];
        String sessId = dataArr[2];

        //check for the session validility
        String tmp = userService.retrieveToken(sessId).get(0).get("user_id");

        if(tmp == null) {
            throw new Exception("Invalid session request");
        }

        int uid = Integer.parseInt(tmp);

        if(!ind.equals("myattend")) {
            throw new Exception("Invalid indicator");
        }

        //retrieve the private key to process decryption
        String decrypted = FieldUtility.decryptStringBase64(env.getProperty("app.key"), b64);
        String [] decryptArr = decrypted.split("\\.");
        System.out.println("Decrypted BASE64: " + decrypted);
        //corrupted decryption means the data has been tampered with other key or no key at all
        if(decryptArr.length != 2) {
            throw new Exception("Invalid request");
        }
        String classId = decryptArr[0];
        long qrTms = Long.parseLong(decryptArr[1]);

        //validate class existing and time validality
        Map<String, Object> classMap = new HashMap<>();
        classMap.put("id", classId);
        ClassModel classModel = classService.retrieveDetail(classMap);

        if(classModel == null) {
            throw new Exception("Invalid class id!");
        }

        //retrieve the student info to check the enrollment
        StudentModel studentModel = studentService.retrieveDetailByCourse(classModel.getCourse_id(), uid);
        if(studentModel == null) {
            throw new Exception("Student does not enroll in the course");
        }

        //conversion of timestamp
        long currTms = Long.parseLong(FieldUtility.getCurrentTimestamp());
        long classStart = Long.parseLong(FieldUtility.getFormatted(classModel.getStart_time(), "yyyy-MM-dd h:m:s", "yyyyMMddHHmmssSSS"));
        long classEnd = Long.parseLong(FieldUtility.getFormatted(classModel.getEnd_time(), "yyyy-MM-dd h:m:s", "yyyyMMddHHmmssSSS"));

        //validate the class if not started yet
        if(currTms < classStart) {
            throw new Exception("Class has not started yet");
        }

        //validate if the class has ended
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

    //list attendance based on class id
    public List<AttendanceModel> retrieveAttendance(Map<String, Object> body) {
        try {
            String classId = (String) body.get("id");
            List<Map<String, String>> attList =  attendanceRepository.retrieveAttendance(classId);

            List<AttendanceModel> attModelList = new ArrayList<>();
            for(Map<String, String> data : attList) {
                AttendanceModel obj = (AttendanceModel) MapperUtility.mapModel(AttendanceModel.class, data);
//                Resource resource = resourceLoader.getResource("classpath:");
//                String fullPath = Paths.get(resource.getFile().toPath().toUri()).getParent().toString().replace("/target", obj.getStudent().getUser().getProfile_pic());
//                obj.getStudent().getUser().setProfile_pic(FieldUtility.encodeFileBase64(fullPath));
                attModelList.add(obj);
            }
            return attModelList;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    //testing function only
//    public List<AttendanceModel> retrieveAttended() {
//        try {
//            return Collections.emptyList();
//        }catch (Exception e) {
//            e.printStackTrace();
//            return Collections.emptyList();
//        }
//    }

    //calculate attendance performance based on course
    public Map<String, String> attendancePerformance(List<CourseModel> courseList, HttpSession session) {
        try {
            CommonModel commonModel = (CommonModel) session.getAttribute("common");

            Map<String, String> attMap = new HashMap<>();
            //if user is student then run performance calculation for student
            if(commonModel.getUser().getRole_id() == FieldUtility.STUDENT_ROLE) {
                for(CourseModel course : courseList) {
                    List<Map<String, String>> perfList = attendanceRepository.retrievePerformanceByStudent(course.getId(), commonModel.getUser().getId());
                    if(!perfList.isEmpty()) {
                        Map<String, String> perc = perfList.get(0);
                        attMap.put(course.getCourse_code(), perc.get("PERCENTAGE"));
                    }else{
                        attMap.put(course.getCourse_code(), "0");
                    }
                }
            }else{ //if lect or admin, run the generic attendance performance calculation
                for(CourseModel course : courseList) {
                    List<Map<String, String>> perfList = attendanceRepository.retrievePerformance(course.getId());
                    if(!perfList.isEmpty()) {
                        Map<String, String> perc = attendanceRepository.retrievePerformance(course.getId()).get(0);
                        attMap.put(course.getCourse_code(), perc.get("PERCENTAGE"));
                    }else{
                        attMap.put(course.getCourse_code(), "0");
                    }
                }
            }
            return attMap;
        }catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }
}
