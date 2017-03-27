package com.yamamz.attendanceapp.models;

import io.realm.RealmObject;

/**
 * Created by AMRI on 3/20/2017.
 */

public class Student extends RealmObject {

    private String fullName;
    private String address;
    private String email;
    private String mobileNumber;
    public Student(){

    }
    public Student(String fullName){
    this.fullName=fullName;
    }
    public Student(String fullName, String address, String email, String mobileNumber) {
        this.fullName = fullName;
        this.address = address;
        this.email = email;
        this.mobileNumber = mobileNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
