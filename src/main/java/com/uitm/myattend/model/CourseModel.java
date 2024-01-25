package com.uitm.myattend.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class CourseModel {

    @Autowired
    private Environment env;
    private UserModel user;
    private int user_id;
    private String id;
    private String course_code;
    private String course_name;
    private double credit_hour;
    private String color;
    private String deleted;
    private String colorConfig;

    public String getDeleted() {
        if(this.deleted.equals("0000-00-00 00:00:00")) {
            return null;
        }else {
            return deleted;
        }
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public double getCredit_hour() {
        return credit_hour;
    }

    public void setCredit_hour(double credit_hour) {
        this.credit_hour = credit_hour;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setColorConfig(String colorConfig){
        this.colorConfig = colorConfig;
    }

    public String getColorConfig() {
        return colorConfig;
    }
}
