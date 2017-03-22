package com.yamamz.attendanceapp.models;

import io.realm.RealmObject;

/**
 * Created by AMRI on 3/20/2017.
 */

public class Attendance extends RealmObject {

    private String student;
    private String status;

    public Attendance() {

    }

    public Attendance(String student, String status) {
        this.student =student;
        this.status = status;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
