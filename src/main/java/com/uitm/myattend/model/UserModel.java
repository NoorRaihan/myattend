package com.uitm.myattend.model;

import com.uitm.myattend.utility.FieldUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;

@Component
@SessionScope
public class UserModel {

    private int id;
    private String email;
    private String username;
    private String fullname;
    private String password;
    private String gender;
    private String birth_date;
    private String profile_pic;
    private int role_id;
    private RoleModel role;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getFormatBirthDate() throws ParseException {
        return FieldUtility.getFormatted(this.birth_date, "yyyy-MM-dd h:m:s", "dd/MM/yyyy");
    }

    public String getFormBirthDate() throws ParseException {
        return FieldUtility.getFormatted(this.birth_date, "yyyy-MM-dd h:m:s", "yyyy-MM-dd");
    }
}
