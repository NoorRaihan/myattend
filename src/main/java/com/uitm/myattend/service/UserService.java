package com.uitm.myattend.service;

import com.uitm.myattend.model.RoleModel;
import com.uitm.myattend.model.UserModel;
import com.uitm.myattend.repository.UserRepository;
import com.uitm.myattend.utility.FieldUtility;

import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final Environment env;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserService(UserRepository userRepo, Environment env, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepo = userRepo;
        this.env = env;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public List<Map<String, String>> retrieveAll() {
        return userRepo.retrieveAllUser();
    }


    public UserModel insert(Map<String, Object> body) throws SQLException {
        try {
            //create new instance
            UserModel user = new UserModel();
            String bday = FieldUtility.getFormatted((String) body.get("birthdate"), "yyyy-MM-dd", "yyyyMMdd");

            user.setId(Integer.parseInt(FieldUtility.generateUUID().substring(0,8)));
            user.setEmail((String) body.get("email"));
            user.setUsername((String) body.get("username"));
            user.setFullname((String) body.get("fullname"));
            user.setPassword(encrytPassword((String) body.get("password")));
            user.setGender((String) body.get("gender"));
            user.setBirth_date(bday);
            user.setProfile_pic(env.getProperty("app.defaultProfile"));
            user.setRole_id(Integer.parseInt((String) body.get("role")));

            userRepo.insert(user);
            return user;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delete(int uid) {
        try {
            if(!userRepo.delete(uid)) {
                throw new Exception("Failed to delete the user record");
            }
        }catch (Exception e) {
            e.printStackTrace();
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
                System.out.println("executed!");
                userList = userRepo.retrieveUserByEmail(email);
            }else if(uid != -1) {
                userList = userRepo.retrieveUserById(Integer.toString(uid));
            }

            if(userList.size() != 1) {
                throw new Exception("User retrieve error occured! UserList size: " + userList.size());
            }

            UserModel userObj = new UserModel();
            Map<String, String> userMap = userList.get(0);
            userObj.setId(Integer.parseInt(userMap.get("id")));
            userObj.setEmail(userMap.get("email"));
            userObj.setPassword(userMap.get("password"));
            userObj.setFullname(userMap.get("full_name"));
            userObj.setGender(userMap.get("gender"));
            userObj.setBirth_date(userMap.get("birth_date"));
            userObj.setProfile_pic(userMap.get("profile_pic"));
            userObj.setRole_id(Integer.parseInt(userMap.get("role_id")));

            RoleModel roleObj = roleService.retrieve(userObj.getRole_id());

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
}
