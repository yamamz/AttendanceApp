package com.yamamz.attendanceapp.models;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
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
    public static final String FIELD_COUNT = "count";
    private static AtomicInteger INTEGER_COUNTER = new AtomicInteger(0);
    private int count;

    public int getCount() {
        return count;
    }


    public Class_name() {

    }

    public String getCountString() {

        return Integer.toString(count);

    }



    //  create() & delete() needs to be called inside a transaction.

    static void create(Realm realm,String class_name) {

        Class_name counter = new Class_name(class_name,increment());
        realm.copyToRealmOrUpdate(counter);


    }


    static void delete(Realm realm, long id) {
       Class_name class_name = realm.where(Class_name.class).equalTo(FIELD_COUNT, id).findFirst();
        if (class_name != null) {
            class_name.deleteFromRealm();
        }

    }
    public Class_name(String class_name,int count) {
        this.class_name = class_name;
        this.count=count;
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

    private static int increment() {

        return INTEGER_COUNTER.getAndIncrement();

    }
}
