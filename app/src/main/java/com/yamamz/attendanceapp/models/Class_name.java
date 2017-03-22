package com.yamamz.attendanceapp.models;

import io.realm.RealmObject;

/**
 * Created by AMRI on 3/23/2017.
 */

public class Class_name extends RealmObject {
    private String class_name;
    public Class_name() {

    }
    public Class_name(String class_name) {
        this.class_name = class_name;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }
}
