package com.yamamz.attendanceapp.models;

import io.realm.RealmObject;

/**
 * Created by AMRI on 3/20/2017.
 */

public class Student extends RealmObject {
    private String fullName;
    private int age;
    private String email;
    private String mobileNumber;
    public Student(){
    }

    public Student(String fullName, int age, String email, String mobileNumber) {
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.mobileNumber = mobileNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
