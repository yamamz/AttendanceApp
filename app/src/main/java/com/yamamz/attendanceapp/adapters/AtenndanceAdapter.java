package com.yamamz.attendanceapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yamamz.attendanceapp.R;
import com.yamamz.attendanceapp.models.Attendance;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by AMRI on 3/20/2017.
 */



public class AtenndanceAdapter extends  RecyclerView.Adapter <AtenndanceAdapter.myViewHolder> {


    private attendanceChangeCallbacks mCallbacks;
    public interface attendanceChangeCallbacks {
     public void onRadioBuutonClicked(String titleKey,int index);

    }

    private List<Attendance> attendanceList;
    private Context context;

    private boolean inDeletionMode = false;
    private Set<Integer> countersToDelete = new HashSet<Integer>();

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

    public void setCallbacks(attendanceChangeCallbacks callbacks) {
        this.mCallbacks = callbacks;
    }

    public AtenndanceAdapter(Context context, List<Attendance> attendanceList) {
        this.context = context;
        this.attendanceList = attendanceList;
        setHasStableIds(false);
    }




  public   void enableDeletionMode(boolean enabled) {

        inDeletionMode = enabled;

        if (!enabled) {

            countersToDelete.clear();

        }

        notifyDataSetChanged();

    }

    Set<Integer> getCountersToDelete() {
        return countersToDelete;
    }


    public void clear() {
        int size = this.attendanceList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.attendanceList.remove(0);
            }
            this.notifyItemRangeRemoved(0, size);
        }
    }



    @Override

    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_student_attendance, parent, false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final Attendance attendance = attendanceList.get(position);
        holder.tv_name.setText(attendance.getStudent().getFullName());

       if (attendance.getStatus().equals("Leave")) {
           holder.rb_leave.setChecked(true);
       }
       if (attendance.getStatus().equals("Present")) {
           holder.rb_present.setChecked(true);
       }
       if (attendance.getStatus().equals("Absent")) {
           holder.rb_absent.setChecked(true);

   }
        Toast.makeText(context,String.valueOf(attendanceList.size()),Toast.LENGTH_SHORT).show();
       holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
               RadioButton radioButton=(RadioButton)  radioGroup.findViewById(i);


               if (mCallbacks != null) {
                   mCallbacks.onRadioBuutonClicked(radioButton.getText().toString(),position);
               }

           }
       });

    }



    @Override
    public int getItemCount() {
        return attendanceList.size();
    }
}
