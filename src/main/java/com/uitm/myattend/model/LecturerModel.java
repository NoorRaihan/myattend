package com.uitm.myattend.model;

import com.uitm.myattend.utility.FieldUtility;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.text.DecimalFormat;
import java.text.ParseException;

@Component
@SessionScope
public class LecturerModel {

    private UserModel user;
    private int user_id;
    private int lect_id;
    private int supervisor_id = -1;
    private String start_date;
    private String qualification;
    private double salary;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getLect_id() {
        return lect_id;
    }

    public void setLect_id(int lect_id) {
        this.lect_id = lect_id;
    }

    public int getSupervisor_id() {
        return supervisor_id;
    }

    public void setSupervisor_id(int supervisor_id) {
        this.supervisor_id = supervisor_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public double getSalary() {
        return salary;
    }

    public String getSalaryDecimal() {
        DecimalFormat decFormat = new DecimalFormat("0.00");
        return decFormat.format(this.salary);
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getFormatStartDate() throws ParseException {
        return FieldUtility.getFormatted(this.start_date, "yyyy-MM-dd h:m:s", "dd/MM/yyyy");
    }

    public String getFormStartDate() throws ParseException {
        return FieldUtility.getFormatted(this.start_date, "yyyy-MM-dd h:m:s", "yyyy-MM-dd");
    }
}
