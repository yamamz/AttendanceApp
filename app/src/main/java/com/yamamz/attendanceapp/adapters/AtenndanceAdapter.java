package com.yamamz.attendanceapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yamamz.attendanceapp.R;
import com.yamamz.attendanceapp.models.Attendance;

import java.util.List;

/**
 * Created by AMRI on 3/20/2017.
 */

public class AtenndanceAdapter extends RecyclerView.Adapter<AtenndanceAdapter.myViewHolder> {


    private List<Attendance> attendanceList;
    private Context context;

    class myViewHolder extends RecyclerView.ViewHolder {

        private RadioGroup radioGroup;
        private TextView tv_name;
        private RadioButton rb_leave;
        private RadioButton rb_present;
        private RadioButton rb_absent;

        myViewHolder(final View itemView) {
            super(itemView);

            radioGroup = (RadioGroup) itemView.findViewById(R.id.RG_attendance_status);
            rb_leave=(RadioButton) itemView.findViewById(R.id.leave);
            rb_present=(RadioButton) itemView.findViewById(R.id.present);
            rb_absent=(RadioButton) itemView.findViewById(R.id.absent);
            tv_name = (TextView) itemView.findViewById(R.id.name);


        }
    }


    public AtenndanceAdapter(Context context, List<Attendance> attendanceList) {
        this.context = context;
        this.attendanceList = attendanceList;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_student_attendance, parent, false);
        return new myViewHolder(itemView);
    }

    private RadioButton lastCheckedRB = null;
    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        final Attendance attendance = attendanceList.get(position);
        holder.tv_name.setText(attendance.getStudent());
        if (attendance.getStatus().equals("absent")){
            holder.rb_absent.setChecked(true);
        }

    }


    @Override
    public int getItemCount() {
        return attendanceList.size();
    }
}
