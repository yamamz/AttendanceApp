package com.yamamz.attendanceapp.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yamamz.attendanceapp.Attendance_Activity;
import com.yamamz.attendanceapp.R;
import com.yamamz.attendanceapp.adapters.AtenndanceAdapter;
import com.yamamz.attendanceapp.models.Attendance;
import com.yamamz.attendanceapp.models.Class_name;
import com.yamamz.attendanceapp.models.DataHelper;
import com.yamamz.attendanceapp.models.DateAttendance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttendanceFragment extends Fragment {


    private static final String TAG = "Activity_attendance";

    private RecyclerView recyclerView;

    private AtenndanceAdapter mAdapter;
    private Realm realm;
    private List<Attendance> attendanceList = new ArrayList<>();
    private View rootView;
    private LinearLayout emptyTextView;
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
        rootView= inflater.inflate(R.layout.fragment_attendance, container, false);
        emptyTextView=(LinearLayout) rootView.findViewById(R.id.empty);
        setupRecyclerView();
        loadDataFromRealm();

        String tag=getTag();
        ((Attendance_Activity)getActivity()).setTabAttendance(tag);


        return rootView;
    }


    private void setupRecyclerView() {

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new AtenndanceAdapter(getActivity(), attendanceList);
        recyclerView.setAdapter(mAdapter);


        if (mAdapter != null) {
            mAdapter.setCallbacks(new AtenndanceAdapter.attendanceChangeCallbacks() {
                @Override
                public void onRadioBuutonClicked(final String titleKey, final int index) {
                    ((Attendance_Activity)getActivity()).changeDrawable();
                    realm.beginTransaction();
                    attendanceList.get(index).setStatus(titleKey);
                    realm.commitTransaction();

                }
            });
        }
        checkAdapter();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public void loadDataFromRealm() {
        Date dateAttend=((Attendance_Activity)getActivity()).getDate();
        String className=((Attendance_Activity)getActivity()).getClassName();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
        String date = DATE_FORMAT.format(dateAttend);
        String attendateDatePrimary = className.concat(date);
        DateAttendance dateAttendanceQuery = realm.where(DateAttendance.class).equalTo("attendancePrimaryKey", attendateDatePrimary).findFirst();

   if(dateAttendanceQuery!=null){

   }

   else{
    attendanceList.clear();
    addItems();
   }
    }
  public   void addItems() {
        if(attendanceList.size()>0){

            attendanceList.clear();
        }

        String className=((Attendance_Activity)getActivity()).getClassName();
        Class_name resultsClassName = realm.where(Class_name.class).equalTo
                ("class_name", className).findFirst();
        for (int i = 0; i < resultsClassName.getStudents().size(); i++) {
            Attendance attendance = new Attendance(resultsClassName.getStudents().get(i), "Leave", i);
            attendanceList.add(attendance);
            mAdapter.notifyItemRangeInserted(0, resultsClassName.getStudents().size());


        }


    }

    void checkAdapter(){
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                checkRecyclerViewIsemplty();
            }
            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                checkRecyclerViewIsemplty();
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                checkRecyclerViewIsemplty();
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                checkRecyclerViewIsemplty();
            }
        });



    }


    public void saveItems() {
        Date dateAttend=((Attendance_Activity)getActivity()).getDate();
        String className=((Attendance_Activity)getActivity()).getClassName();
        DataHelper.addItems(realm, className, dateAttend, attendanceList);
    }




    private void checkRecyclerViewIsemplty(){
        if(mAdapter.getItemCount()==0){

            emptyTextView.setVisibility(View.VISIBLE);
        }
        else{

            emptyTextView.setVisibility(View.GONE);
        }


    }
}
