package com.uitm.myattend.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class CommonModel {

    private String token;
    private UserModel userModel;

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


