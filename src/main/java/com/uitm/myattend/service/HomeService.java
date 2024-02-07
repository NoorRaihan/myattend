package com.uitm.myattend.service;

import com.uitm.myattend.model.*;
import com.uitm.myattend.utility.FieldUtility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class HomeService {

    private final UserService userService;
    private final ClassService classService;
    private final ResourceLoader resourceLoader;
    private final StudentService studentService;
    private final LecturerService lecturerService;
    private final AttendanceService attendanceService;
    private final CourseService courseService;

    public HomeService(UserService userService,
                       ResourceLoader resourceLoader,
                       ClassService classService,
                       StudentService studentService,
                       LecturerService lecturerService,
                       AttendanceService attendanceService,
                       CourseService courseService) {
        this.userService = userService;
        this.resourceLoader = resourceLoader;
        this.classService = classService;
        this.studentService = studentService;
        this.lecturerService = lecturerService;
        this.attendanceService = attendanceService;
        this.courseService = courseService;
    }

    public void index(HttpSession session, HttpServletRequest request) {
        try {
            CommonModel common = (CommonModel) session.getAttribute("common");
            UserModel userObj = userService.retrieveUserById(common.getUser().getId());

            request.setAttribute("userFullname", userObj.getFullname());
            request.setAttribute("userRolename", userObj.getRole().getRole_name());

            List<ClassModel> activeList = classService.retrieveActive(session);
            request.setAttribute("activeList", activeList);

            List<ClassModel> todayList = classService.retrieveToday(session);
            request.setAttribute("todayList", todayList);

            UserModel userProfile = userService.retrieveUserById(common.getUser().getId());
            request.setAttribute("userProfile", userProfile);

            if(userObj.getRole_id() == FieldUtility.STUDENT_ROLE) {
                StudentModel studentProfile = studentService.retrieveDetail(common.getUser().getId());
                request.setAttribute("studentProfile", studentProfile);
            }

            if(userObj.getRole_id() == FieldUtility.LECTURER_ROLE) {
                LecturerModel lecturerProfile = lecturerService.retrieveDetail(common.getUser().getId());
                if(lecturerProfile.getSupervisor_id() > 0) {
                    UserModel svModel = userService.retrieveUserById(lecturerProfile.getSupervisor_id());
                    lecturerProfile.setSupervisor(svModel);
                }
                request.setAttribute("lecturerProfile", lecturerProfile);
            }

            List<CourseModel> courseList = new ArrayList<>();
            if(common.getUser().getRole_id() == FieldUtility.LECTURER_ROLE) {
                courseList = courseService.retrieveCourseByLecturer(common.getUser().getId());
            }else if(common.getUser().getRole_id() == FieldUtility.STUDENT_ROLE) {
                courseList = courseService.retrieveRegisteredCourseStudent(common.getUser().getId());
            }else if(common.getUser().getRole_id() == FieldUtility.ADMIN_ROLE) {
                courseList = courseService.retrieveAll();
            }
            request.setAttribute("courses", courseList);

            request.setAttribute("perf", attendanceService.attendancePerformance(courseList,session));

            Resource resource = resourceLoader.getResource("classpath:");
            String fullPath = Paths.get(resource.getFile().toPath().toUri()).getParent().toString().replace("/target", userObj.getProfile_pic());
            request.setAttribute("profilePicture", FieldUtility.encodeFileBase64(fullPath));

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
