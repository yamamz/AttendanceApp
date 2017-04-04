package com.yamamz.attendanceapp.models;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by AMRI on 3/20/2017.
 */

public class Attendance extends RealmObject {
    public static final String FIELD_COUNT = "count";


    private static AtomicInteger INTEGER_COUNTER = new AtomicInteger(0);

    private Student student;
    private String status;
    @PrimaryKey
    private int count;
    public int getCount() {
        return count;
    }
    public String getCountString() {
        return Integer.toString(count);
    }

    public  static void create(Realm realm, String attendancePrimaryKey) {

        DateAttendance parent = realm.where(DateAttendance.class).equalTo("attendancePrimaryKey",attendancePrimaryKey
                ).findFirst();

        RealmList<Attendance> counters = parent.getAttendanceRealmList();

        Attendance counter = realm.createObject(Attendance.class, increment());
        counters.add(counter);
    }

    public Attendance() {

    }

    public Attendance(Student student, String status,int count) {
        this.student =student;
        this.status = status;
        this.count=count;
    }

    static void delete(Realm realm, long id) {
       Attendance attendance = realm.where(Attendance.class).equalTo(FIELD_COUNT, id).findFirst();
        if (attendance != null) {
            attendance.deleteFromRealm();
        }

    }

    static void addItems(Realm realm,Student student,String attendateDatePrimary,String status){

        final DateAttendance results = realm.where(DateAttendance.class).equalTo
                ("attendancePrimaryKey", attendateDatePrimary).findFirst();
            RealmList<Attendance> attendances=results.getAttendanceRealmList();
          Attendance attendance=new Attendance(student,status,increment());
            attendances.add(attendance);


    }

   public static int increment() {
        return INTEGER_COUNTER.getAndIncrement();
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
