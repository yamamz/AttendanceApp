package com.yamamz.attendanceapp.models;

import io.realm.RealmObject;

/**
 * Created by AMRI on 3/20/2017.
 */

public class Attendance extends RealmObject {

    private Student student;
    private String status;

    public Attendance() {

    }

    public Attendance(Student student, String status) {
        this.student =student;
        this.status = status;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
