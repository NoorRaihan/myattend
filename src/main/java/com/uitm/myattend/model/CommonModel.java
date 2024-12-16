package com.uitm.myattend.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class CommonModel {

    private String token;
    private UserModel userModel;
    private String profilePicture;
    private SemesterSessionModel sessionModel;

    public void setSessionModel(SemesterSessionModel sessionModel) {
        this.sessionModel = sessionModel;
    }

    public SemesterSessionModel getSessionModel() {
        return sessionModel;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public UserModel getUser() {
        return userModel;
    }

    public void setUser(UserModel userModel) {
        this.userModel = userModel;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}


