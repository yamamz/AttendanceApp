package com.yamamz.attendanceapp.fragments;


        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v7.widget.DefaultItemAnimator;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import com.yamamz.attendanceapp.R;
        import com.yamamz.attendanceapp.adapters.AtenndanceAdapter;
        import com.yamamz.attendanceapp.models.Attendance;

        import java.util.ArrayList;
        import java.util.List;


public class AttendanceFragment extends Fragment {
    private RecyclerView recyclerView;
    private View RootView;
    private AtenndanceAdapter mAdapter;
    private List<Attendance> attendanceList = new ArrayList<>();
    public AttendanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        RootView=inflater.inflate(R.layout.fragment_attendance, container, false);
        recyclerView = (RecyclerView) RootView.findViewById(R.id.recyclerView);
        setupRecyclerView();
        loadAttendance();
        return RootView;

    }

    private void setupRecyclerView() {
        mAdapter = new AtenndanceAdapter(recyclerView.getContext(), attendanceList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
         recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }

   void loadAttendance(){

       for(int i=1; i<=10; i++){

          Attendance attendance = new Attendance("Yamamz","absent");
           attendanceList.add(attendance);
       }
       mAdapter.notifyDataSetChanged();

    }


}
