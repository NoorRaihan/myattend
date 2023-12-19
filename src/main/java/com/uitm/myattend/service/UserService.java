package com.uitm.myattend.service;

import com.uitm.myattend.model.UserModel;
import com.uitm.myattend.repository.UserRepository;
import com.uitm.myattend.utility.FieldUtility;

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
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final Environment env;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepo, Environment env, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.env = env;
        this.passwordEncoder = passwordEncoder;
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

    public String encrytPassword(String plain) {
        return passwordEncoder.encode(plain);
    }

}
