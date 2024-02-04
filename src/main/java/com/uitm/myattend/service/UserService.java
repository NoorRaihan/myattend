package com.uitm.myattend.service;

import com.uitm.myattend.mapper.MapperUtility;
import com.uitm.myattend.model.RoleModel;
import com.uitm.myattend.model.UserModel;
import com.uitm.myattend.repository.UserRepository;
import com.uitm.myattend.utility.FieldUtility;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpSession;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final Environment env;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final ResourceLoader resourceLoader;

    @Autowired
    public UserService(UserRepository userRepo, Environment env, PasswordEncoder passwordEncoder, RoleService roleService, ResourceLoader resourceLoader) {
        this.userRepo = userRepo;
        this.env = env;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.resourceLoader = resourceLoader;
    }

    public List<UserModel> retrieveAll(HttpSession session) {
        try {
            List<Map<String, String>> userList = userRepo.retrieveAllUser();

            List<UserModel> users = new ArrayList<>();
            for(Map<String, String> user : userList) {
                users.add((UserModel)MapperUtility.mapModel(UserModel.class, user));
            }
            return users;
        }catch (Exception e) {
            session.setAttribute("error", "Internal server error. Please contact admin for futher assistance");
            e.printStackTrace();
            return Collections.emptyList();
        }

    }


    public UserModel insert(Map<String, Object> body, MultipartFile file) throws SQLException {
        try {
            //create new instanceapp.whitelist.ext=png

            UserModel user = new UserModel();
            String bday = FieldUtility.getFormatted((String) body.get("birthdate"), "yyyy-MM-dd", "yyyyMMdd");
            String uid = FieldUtility.generateUUID().substring(0,8);

            user.setId(Integer.parseInt(uid));
            user.setEmail((String) body.get("email"));
            user.setUsername((String) body.get("username"));
            user.setFullname((String) body.get("fullname"));
            user.setPassword(encrytPassword((String) body.get("password")));
            user.setGender((String) body.get("gender"));
            user.setBirth_date(bday);

            if(file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()) {
                user.setProfile_pic(env.getProperty("app.imagefolder") + uid + ".png");
                if(!fileHandler(file, uid)) {
                    throw new Exception("Failed to save profile image");
                }
            }else{
                user.setProfile_pic(env.getProperty("app.defaultProfile"));
            }
            user.setRole_id(Integer.parseInt((String) body.get("role")));

            if(!userRepo.insert(user)) {
                throw new Exception("Failed to insert user data");
            }

            return user;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean delete(Map<String, Object> body) {
        return delete(body, -1);
    }

    public boolean delete(int uid) {
        return delete(null, uid);
    }

    public boolean delete(Map<String, Object> body, int uid) {
        try {
            if(body != null && uid == -1) {
                uid = Integer.parseInt((String) body.get("uid"));
            }

            if(!userRepo.delete(uid)) {
                throw new Exception("Failed to delete the user record");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String encrytPassword(String plain) {
        return passwordEncoder.encode(plain);
    }

    public List<Map<String, String>> retrieveToken(String token) {
        try {
            return userRepo.retrieveToken(token);
        }catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public UserModel retrieveUserByUsername(String username) {
        return retrieveUser(username, null, -1);
    }

    public UserModel retrieveUserByEmail(String email) {
        return retrieveUser(null, email, -1);
    }

    public UserModel retrieveUserById(int uid) {
        return retrieveUser(null, null, uid);
    }

    private UserModel retrieveUser(String username, String email, int uid) {
        try {
            List<Map<String, String>> userList = new ArrayList<>();
            if(username != null) {
                userList = userRepo.retrieveUserByUsername(username);
            }else if(email != null) {
                userList = userRepo.retrieveUserByEmail(email);
            }else if(uid != -1) {
                userList = userRepo.retrieveUserById(Integer.toString(uid));
            }

            if(userList.size() != 1) {
                throw new Exception("User retrieve error occured! UserList size: " + userList.size());
            }


            Map<String, String> userMap = userList.get(0);

            RoleModel roleObj = roleService.retrieve(Integer.parseInt(userMap.get("role_id")));
            userMap.put("ROLE_ID", roleObj.getId());
            userMap.put("ROLE_NAME", roleObj.getRole_name());
            UserModel userObj = (UserModel) MapperUtility.mapModel(UserModel.class, userMap);

            if(roleObj == null) {
                throw new Exception("Failed to retrieve role object");
            }
            userObj.setRole(roleObj);

            return userObj;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String initToken(int uid) {
        try {
            //init token
            String token = UUID.randomUUID().toString();

            //disable all old token
            if(!userRepo.updateToken(null, uid, 0)) {
                throw new Exception("Failed to invalidate old token");
            }

            //insert new token
            if(!userRepo.insertToken(token, uid)) {
                throw new Exception("Failed to insert new token");
            }

            return token;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean update(Map<String, Object> body, MultipartFile file) {
        try {
            UserModel user = new UserModel();
            String bday = FieldUtility.getFormatted((String) body.get("birthdate"), "yyyy-MM-dd", "yyyyMMdd");
            String uid = (String) body.get("uid");

            user.setId(Integer.parseInt(uid));
            user.setEmail((String) body.get("email"));
            user.setUsername((String) body.get("username"));
            user.setFullname((String) body.get("fullname"));

            String pass = (String) body.get("password");
            if(pass != null && !pass.isEmpty()) {
                user.setPassword(encrytPassword(pass));
            }

            user.setGender((String) body.get("gender"));
            user.setBirth_date(bday);

            if(file.getOriginalFilename() != null && !file.getOriginalFilename().isEmpty()) {
                user.setProfile_pic(env.getProperty("app.imagefolder") + uid + ".png");
                if(!fileHandler(file, (String) body.get("uid"))) {
                    throw new Exception("Failed to save profile image");
                }
            }
            user.setRole_id(Integer.parseInt((String) body.get("role")));

            if(!userRepo.update(user)) {
                throw new Exception("Failed to update user info");
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean fileHandler(MultipartFile file, String uid) {
        try {
            String location = env.getProperty("app.imagefolder");
            String newFilename = uid + ".png";
            Resource resource = resourceLoader.getResource("classpath:");
            String fullPath = Paths.get(resource.getFile().toPath().toUri()).getParent().toString().replace("/target", location);
            Path realPath = Paths.get(fullPath + newFilename);

            InputStream fileInputStream = file.getInputStream();
            String contentType = getContentType(file.getBytes(), newFilename);

            if(!contentType.equals("image/png")) {
                throw new Exception("Invalid image format");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, realPath, StandardCopyOption.REPLACE_EXISTING);
            }

            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getContentType(byte[] fileBytes, String filename) throws IOException {
        TikaConfig tikaConfig = TikaConfig.getDefaultConfig();
        Detector detector = tikaConfig.getDetector();
        TikaInputStream tikaInputStream = TikaInputStream.get(new ByteArrayInputStream(fileBytes));
        Metadata metadata = new Metadata();
        metadata.add(Metadata.CONTENT_TYPE, filename);
        return detector.detect(tikaInputStream, metadata).toString();
    }

}
