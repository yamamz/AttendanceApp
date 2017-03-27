package com.yamamz.attendanceapp.models;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by AMRI on 3/26/2017.
 */

public class DateAttendance extends RealmObject {
    @PrimaryKey
    private String attendancePrimaryKey;
   private Date date;
    private String class_name;
    private RealmList<Attendance> attendanceRealmList;

    public DateAttendance() {
    }

    public DateAttendance(Date date, String class_name,String attendancePrimaryKey) {
        this.date = date;
        this.class_name = class_name;
        this.attendancePrimaryKey=attendancePrimaryKey;
    }

    public DateAttendance(Date date, RealmList<Attendance> attendanceRealmList, String class_name) {
        this.date = date;
        this.attendanceRealmList = attendanceRealmList;
        this.class_name=class_name;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public RealmList<Attendance> getAttendanceRealmList() {
        return attendanceRealmList;
    }

    public void setAttendanceRealmList(RealmList<Attendance> attendanceRealmList) {
        this.attendanceRealmList = attendanceRealmList;
    }

    public String getAttendancePrimaryKey() {
        return attendancePrimaryKey;
    }

    public void setAttendancePrimaryKey(String attendancePrimaryKey) {
        this.attendancePrimaryKey = attendancePrimaryKey;
    }
}
