package com.uitm.myattend.service;

import com.uitm.myattend.model.ClassModel;
import com.uitm.myattend.model.CommonModel;
import com.uitm.myattend.model.UserModel;
import com.uitm.myattend.utility.FieldUtility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;
import java.nio.file.Paths;
import java.util.List;

@Service
public class HomeService {

    private final UserService userService;
    private final ClassService classService;
    private final ResourceLoader resourceLoader;

    public HomeService(UserService userService, ResourceLoader resourceLoader, ClassService classService) {
        this.userService = userService;
        this.resourceLoader = resourceLoader;
        this.classService = classService;
    }

    public void index(HttpSession session, HttpServletRequest request) {
        try {
            CommonModel common = (CommonModel) session.getAttribute("common");
            UserModel userObj = userService.retrieveUserById(common.getUser().getId());

            request.setAttribute("userFullname", userObj.getFullname());
            request.setAttribute("userRolename", userObj.getRole().getRole_name());

            Resource resource = resourceLoader.getResource("classpath:");
            String fullPath = Paths.get(resource.getFile().toPath().toUri()).getParent().toString().replace("/target", userObj.getProfile_pic());

            request.setAttribute("profilePicture", FieldUtility.encodeFileBase64(fullPath));

            List<ClassModel> activeList = classService.retrieveActive();
            request.setAttribute("activeList", activeList);

            List<ClassModel> todayList = classService.retrieveToday();
            request.setAttribute("todayList", todayList);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
