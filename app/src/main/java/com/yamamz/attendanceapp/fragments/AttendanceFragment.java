package com.yamamz.attendanceapp.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.yamamz.attendanceapp.MainActivity;
import com.yamamz.attendanceapp.R;
import com.yamamz.attendanceapp.adapters.AtenndanceAdapter;
import com.yamamz.attendanceapp.models.Attendance;
import com.yamamz.attendanceapp.models.Class_name;
import com.yamamz.attendanceapp.models.DateAttendance;
import com.yamamz.attendanceapp.models.Student;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;


public class AttendanceFragment extends Fragment {
    private RecyclerView recyclerView;
    private View RootView;
    private AtenndanceAdapter mAdapter;
    private Realm realm;
    private List<Attendance> attendanceList = new ArrayList<>();
    private List<DateAttendance> dateAttendanceList=new ArrayList<>();
    public AttendanceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(getActivity());
        realm = Realm.getDefaultInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        String myTag =getTag();
        ((MainActivity) getActivity()).setTagAttendanceFrag(myTag);


        RootView=inflater.inflate(R.layout.fragment_attendance, container, false);




        recyclerView = (RecyclerView) RootView.findViewById(R.id.recyclerView);
        setupRecyclerView();
        loadadateFromRealm();


        return RootView;

    }

    public void SaveAttendance(final String className, final Date dateOfAttendance){

        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
        String date = DATE_FORMAT.format(dateOfAttendance);
        String attendateDatePrimary = className.concat(date);

realm.beginTransaction();
        final DateAttendance dateAttendance = new DateAttendance(dateOfAttendance,
                className, attendateDatePrimary);
        realm.copyToRealmOrUpdate(dateAttendance);

        final DateAttendance results = realm.where(DateAttendance.class).equalTo
                ("attendancePrimaryKey",attendateDatePrimary).findFirst();


        for (int i = 0; i < attendanceList.size(); i++) {
            Attendance attendance = new Attendance(attendanceList.get(i).getStudent(),
                    attendanceList.get(i).getStatus());
            results.getAttendanceRealmList().add(attendance);
        }

        Toast.makeText(getActivity(),"Success",Toast.LENGTH_LONG).show();
        mAdapter.notifyDataSetChanged();
realm.commitTransaction();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {




            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {



            }

        });


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();

    }

    private void setupRecyclerView() {
        mAdapter = new AtenndanceAdapter(recyclerView.getContext(), attendanceList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
         recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }

    public  void  loadadateFromRealm(){
        if(attendanceList.size()>0){
            attendanceList.clear();
        }
        String class_name=((MainActivity)getActivity()).getClassName();
        Date date=((MainActivity)getActivity()).getDate();

        Toast.makeText(getActivity(),class_name,
                Toast.LENGTH_LONG).show();

        Toast.makeText(getActivity(),String.valueOf(date),
                Toast.LENGTH_LONG).show();

        String classname=((MainActivity)getActivity()).getClassName();
        Date dateAttend=((MainActivity)getActivity()).getDate();

        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
        String dateFormat = DATE_FORMAT.format(dateAttend);
        String attendateDatePrimary=classname.concat(dateFormat);



        final DateAttendance results = realm.where(DateAttendance.class).equalTo
                ("attendancePrimaryKey",attendateDatePrimary).findFirst();
        try {
            for (int i = 0; i < results.getAttendanceRealmList().size(); i++) {
                Attendance attendance = new Attendance(results.getAttendanceRealmList()
                        .get(i).getStudent(),
                        results.getAttendanceRealmList().get(i).getStatus());
                        attendanceList.add(attendance);
            }
            mAdapter.notifyDataSetChanged();
        }
        catch (Exception ignored){

        }

        if(attendanceList.size()<=0){
        loadlocationsDatabase();
        }

        mAdapter.notifyDataSetChanged();




}
    public void loadlocationsDatabase() {
        if (attendanceList.size() > 0) {
            attendanceList.clear();
        }

        String className = ((MainActivity) getActivity()).getClassName();
        Class_name results = realm.where(Class_name.class).equalTo
                ("class_name", className).findFirst();

        for (int i = 0; i < results.getStudents().size(); i++) {
            Student student = new Student(results.getStudents().get(i).getFullName());
            Attendance attendance = new Attendance(student,
                    "leave");
            attendanceList.add(attendance);


        }
    }

}

