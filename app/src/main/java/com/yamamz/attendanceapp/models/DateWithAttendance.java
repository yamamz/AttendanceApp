package com.yamamz.attendanceapp.models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by AMRI on 3/20/2017.
 */

public class DateWithAttendance extends RealmObject {

    private String date;
    private String className;

    private RealmList<Attendance> attendances;

    public DateWithAttendance() {

    }

    public DateWithAttendance(String date, String className,RealmList<Attendance> attendances) {
        this.date = date;
        this.className = className;
        this.attendances=attendances;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public RealmList<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(RealmList<Attendance> attendances) {
        this.attendances = attendances;
    }
}
