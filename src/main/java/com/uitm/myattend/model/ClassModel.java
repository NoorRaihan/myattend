package com.uitm.myattend.model;

import com.uitm.myattend.utility.FieldUtility;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.text.ParseException;

@Component
@SessionScope
public class ClassModel {

    private String id;
    private CourseModel course;
    private String course_id;
    private String class_desc;
    private String class_date;
    private String start_time;
    private String end_time;
    private String venue;
    private String deleted_at;
    private SemesterSessionModel sessionModel;

    public void setSessionModel(SemesterSessionModel sessionModel) {
        this.sessionModel = sessionModel;
    }

    public SemesterSessionModel getSessionModel() {
        return sessionModel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CourseModel getCourse() {
        return course;
    }

    public void setCourse(CourseModel course) {
        this.course = course;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getClass_desc() {
        return class_desc;
    }

    public void setClass_desc(String class_desc) {
        this.class_desc = class_desc;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getClass_date() {
        return class_date;
    }

    public void setClass_date(String class_date) {
        this.class_date = class_date;
    }

    public String getFormClassDate() throws ParseException {
        return FieldUtility.getFormatted(this.class_date, "yyyy-MM-dd h:m:s", "yyyy-MM-dd");
    }

    public String getFormattedClassDate() throws ParseException {
        return FieldUtility.getFormatted(this.class_date, "yyyy-MM-dd h:m:s", "dd/MM/yyyy");
    }

    public String getFormStartTime() throws ParseException {
        return FieldUtility.getFormatted(this.start_time, "yyyy-MM-dd h:m:s", "HH:mm");
    }

    public String getFormEndTime() throws ParseException {
        return FieldUtility.getFormatted(this.end_time, "yyyy-MM-dd h:m:s", "HH:mm");
    }
}
