package com.yamamz.attendanceapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yamamz.attendanceapp.R;
import com.yamamz.attendanceapp.models.Student;

import java.util.List;

/**
 * Created by AMRI on 3/26/2017.
 */

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.myViewHolder> {

    private List<Student> studentList;
    private Context context;

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .student_list_item, parent, false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        final Student studen_name = studentList.get(position);
        holder.name.setText(studen_name.getFullName());

        @SuppressLint("Recycle")
        TypedArray circles = context.getResources().obtainTypedArray(R
                .array.circle_images);
        int colorRandom = (int) (Math.random() * circles.length());


        String name= holder.name.getText().toString();
        if (!name.equals("")) {
            holder.initial.setText(name.substring(0, 1));
        }
        holder.initial.setBackgroundResource((circles.getResourceId(colorRandom, R.drawable
                .circle)));

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView initial, name;
        private CardView cardView;

        public myViewHolder(View itemView) {
            super(itemView);
            initial = (TextView) itemView.findViewById(R.id.initial);
            name = (TextView) itemView.findViewById(R.id.tv_student);
        }
    }

    public StudentAdapter(List<Student> studentList, Context context) {
        this.studentList = studentList;
        this.context = context;
    }
}
