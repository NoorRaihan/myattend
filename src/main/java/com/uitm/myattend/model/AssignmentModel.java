package com.uitm.myattend.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class AssignmentModel {
    
    private int assignment_id;
    private String session_id;
    private String course_id;
    private String assignment_header;
    private String assignment_desc;
    private int disabled_flag;
    private int bypass_time_flag;
    private String ori_filename;
    private String server_filename;
    private String file_path;
    private String started_at;
    private String ended_at;
    private String created_at;
    private String updated_at;
    private String deleted_at;
    private SemesterSessionModel session;
    private CourseModel course;
    
    // model start

    public SemesterSessionModel getSession() {
        return session;
    }

    public void setSession(SemesterSessionModel session) {
        this.session = session;
    }

    public CourseModel getCourse() {
        return course;
    }

    public void setCourse(CourseModel course) {
        this.course = course;
    }

    // model end

    // Getter and Setter for assignment_id
    public int getAssignment_id() {
        return assignment_id;
    }

    public void setAssignment_id(int assignment_id) {
        this.assignment_id = assignment_id;
    }

    // Getter and Setter for course_id
    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    // Getter and Setter for session_id
    public String getSessionId() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    // Getter and Setter for assignment_header
    public String getAssignment_header() {
        return assignment_header;
    }

    public void setAssignment_header(String assignment_header) {
        this.assignment_header = assignment_header;
    }

    // Getter and Setter for assignment_desc
    public String getAssignment_desc() {
        return assignment_desc;
    }

    public void setAssignment_desc(String assignment_desc) {
        this.assignment_desc = assignment_desc;
    }

    // Getter and Setter for disabled_flag
    public int isDisabled_flag() {
        return disabled_flag;
    }

    public void setDisabled_flag(int disabled_flag) {
        this.disabled_flag = disabled_flag;
    }

    // Getter and Setter for bypass_time_flag
    public int isBypass_time_flag() {
        return bypass_time_flag;
    }

    public void setBypass_time_flag(int bypass_time_flag) {
        this.bypass_time_flag = bypass_time_flag;
    }

    // Getter and Setter for ori_file_name
    public String getOri_filename() {
        return ori_filename;
    }

    public void setOri_filename(String ori_filename) {
        this.ori_filename = ori_filename;
    }

    // Getter and Setter for server_file_name
    public String getServer_filename() {
        return server_filename;
    }

    public void setServer_filename(String server_filename) {
        this.server_filename = server_filename;
    }

    // Getter and Setter for server_file_name
    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    // Getter and Setter for started_at
    public String getStarted_at() {
        return started_at;
    }

    public void setStarted_at(String started_at) {
        this.started_at = started_at;
    }

    // Getter and Setter for ended_at
    public String getEnded_at() {
        return ended_at;
    }

    public void setEnded_at(String ended_at) {
        this.ended_at = ended_at;
    }

    // Getter and Setter for created_at
    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    // Getter and Setter for updated_at
    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    // Getter and Setter for deleted_at
    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }
}
