package com.yamamz.attendanceapp.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by AMRI on 3/23/2017.
 */

public class Class_name extends RealmObject {
    @PrimaryKey
    private String class_name;
    private RealmList<Student> students;
    public Class_name() {

    }
    public Class_name(String class_name) {
        this.class_name = class_name;
    }

    public Class_name(String class_name, RealmList<Student> students){

        this.class_name=class_name;
        this.students=students;
    }

    public RealmList<Student> getStudents() {
        return students;
    }

    public void setStudents(RealmList<Student> students) {
        this.students = students;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }
}
