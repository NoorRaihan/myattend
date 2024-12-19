package com.uitm.myattend.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;

@Component
@SessionScope
@Data
public class SemesterSessionModel {
    private String id;
    private String sessionName;
    private boolean isUsed;
}
