package com.yamamz.attendanceapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yamamz.attendanceapp.R;
import com.yamamz.attendanceapp.models.Class_name;

import java.util.List;

/**
 * Created by AMRI on 3/23/2017.
 */

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.myViewHolder> {

    private List<Class_name> class_names;
    private Context context;

    public ClassAdapter( Context context,List<Class_name> class_names) {
        this.class_names = class_names;
        this.context = context;
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView Tv_className;
        public myViewHolder(View itemView) {
            super(itemView);
            Tv_className = (TextView) itemView.findViewById(R.id.tv_className);
        }
    }
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_list_item, parent, false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        final Class_name class_name = class_names.get(position);
        holder.Tv_className.setText(class_name.getClass_name());

    }

    @Override
    public int getItemCount() {
        return class_names.size();
    }


}
