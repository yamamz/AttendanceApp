package com.yamamz.attendanceapp.models;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import io.realm.Realm;

/**
 * Created by AMRI on 3/28/2017.
 */

public class DataHelper {


    private static successCallbacks mCallbacks;
    public interface successCallbacks {

        void onSuccessInsert();

    }
    public void setCallbacks(successCallbacks callbacks) {
        mCallbacks = callbacks;
    }

    public static void editItem(Realm realm,final long index,
                                final String titleKey) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Attendance attendance = realm.where(Attendance.class).equalTo("count", index)
                        .findFirst();
                attendance.setStatus(titleKey);

            }

        });

    }

    public static void addItems(Realm realm, final String className, final Date
            dateOfAttendance, final List<Attendance> attendanceList) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
                String date = DATE_FORMAT.format(dateOfAttendance);
                String attendateDatePrimary = className.concat(date);
                DateAttendance dateAttendance = new DateAttendance(dateOfAttendance,
                        className, attendateDatePrimary);
                realm.copyToRealmOrUpdate(dateAttendance);

                for (int i = 0; i < attendanceList.size(); i++) {
                    Attendance.addItems(realm, attendanceList.get(i).getStudent(),attendateDatePrimary,attendanceList.get(i).getStatus());
                }
            }
        });
    }


    public static void deleteItemAsync(Realm realm, final long id) {

        realm.executeTransactionAsync(new Realm.Transaction() {

            @Override

            public void execute(Realm realm) {

                Attendance.delete(realm, id);

            }

        });

    }

    public static void deleteItemClassAsync(Realm realm, final long id) {

        realm.executeTransactionAsync(new Realm.Transaction() {

            @Override

            public void execute(Realm realm) {

                Class_name.delete(realm, id);

            }

        });

    }

    public static void addClass(Realm realm, final String className) {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Class_name.create(realm, className);
            }
        });
    }


    public static void deleteItemsAsync(Realm realm, Collection<Integer> ids) {

        // Create an new array to avoid concurrency problem.

        final Integer[] idsToDelete = new Integer[ids.size()];

        ids.toArray(idsToDelete);

        realm.executeTransactionAsync(new Realm.Transaction() {

            @Override

            public void execute(Realm realm) {

                for (Integer id : idsToDelete) {


                }

            }

        });

    }

}
