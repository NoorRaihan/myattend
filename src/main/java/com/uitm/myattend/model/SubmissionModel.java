package com.uitm.myattend.model;

import java.text.ParseException;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.uitm.myattend.utility.FieldUtility;

@Component
@SessionScope
public class SubmissionModel {
    
    private int submission_id;
    private String student_id;
    private int assignment_id;
    private String status;
    private String submission_text;
    private int submission_mark;
    private String ori_filename;
    private String server_filename;
    private String file_path;
    private String created_at;
    private String updated_at;
    private int mark_by;
    private StudentModel student;
    private AssignmentModel assignment;
    private LecturerModel lecturer;
    
    // model start

    public StudentModel getStudent() {
        return student;
    }

    public void setStudent(StudentModel student) {
        this.student = student;
    }

    public AssignmentModel getAssignment() {
        return assignment;
    }

    public void setAssignment(AssignmentModel assignment) {
        this.assignment = assignment;
    }

    public LecturerModel getMarkBy() {
        return lecturer;
    }

    public void setMarkBy(LecturerModel lecturer) {
        this.lecturer = lecturer;
    }

    // model end

    // Getter and Setter for assignment_id
    public int getSubmission_id() {
        return submission_id;
    }

    public void setSubmission_id(int submission_id) {
        this.submission_id = submission_id;
    }

    // Getter and Setter for student_id
    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    // Getter and Setter for assignment_id
    public int getAssignment_id() {
        return assignment_id;
    }

    public void setAssignment_id(int assignment_id) {
        this.assignment_id = assignment_id;
    }

    // Getter and Setter for status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getter and Setter for submission_text
    public String getSubmission_text() {
        return submission_text;
    }

    public void setSubmission_text(String submission_text) {
        this.submission_text = submission_text;
    }

    // Getter and Setter for submission_mark
    public int getSubmission_mark() {
        return submission_mark;
    }

    public void setSubmission_mark(int submission_mark) {
        this.submission_mark = submission_mark;
    }

    // Getter and Setter for mark_by
    public int getMark_by() {
        return mark_by;
    }

    public void setMark_by(int mark_by) {
        this.mark_by = mark_by;
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

    // Getter and Setter for created_at
    public String getCreated_at() throws ParseException {
        return created_at;
        // return FieldUtility.getFormatted(this.created_at, "yyyy-MM-dd h:m:s", "yyyy-MM-dd");
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    // Getter and Setter for updated_at
    public String getUpdated_at() throws ParseException {
        return updated_at;
        // return FieldUtility.getFormatted(this.updated_at, "yyyy-MM-dd h:m:s", "yyyy-MM-dd");
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    // // Getter and Setter for deleted_at
    // public String getDeleted_at() throws ParseException {
    //     return deleted_at;
    //     // return FieldUtility.getFormatted(this.deleted_at, "yyyy-MM-dd h:m:s", "yyyy-MM-dd");
    // }

    // public void setDeleted_at(String deleted_at) {
    //     this.deleted_at = deleted_at;
    // }
}
