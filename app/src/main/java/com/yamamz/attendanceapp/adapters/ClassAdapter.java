package com.yamamz.attendanceapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yamamz.attendanceapp.ActivityAddClass;
import com.yamamz.attendanceapp.R;
import com.yamamz.attendanceapp.models.Class_name;

import java.util.List;

/**
 * Created by AMRI on 3/23/2017.
 */

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.myViewHolder> {

    private List<Class_name> class_names;
    private Context context;
    private int mBackground;


    public ClassAdapter( Context context,List<Class_name> class_names) {
        this.class_names = class_names;
        this.context = context;
        TypedValue mTypedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView Tv_className;
        final View mView;
        private CardView cardView;
        private TextView initial;
        public myViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
cardView=(CardView) itemView.findViewById(R.id.class_card);
initial=(TextView) itemView.findViewById(R.id.initial);
            Tv_className = (TextView) itemView.findViewById(R.id.tv_className);
        }
    }
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_list_item, parent, false);
        itemView.setBackgroundResource(mBackground);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        final Class_name class_name = class_names.get(position);
        holder.Tv_className.setText(class_name.getClass_name());

        @SuppressLint("Recycle") TypedArray circles = context.getResources().obtainTypedArray(R
                .array.circle_images);
        int colorRandom = (int) (Math.random() * circles.length());


        String name= holder.Tv_className.getText().toString();
        if (!name.equals("")) {
            holder.initial.setText(name.substring(0, 1));
        }
        holder.initial.setBackgroundResource((circles.getResourceId(colorRandom, R.drawable
                .circle)));


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((ActivityAddClass) context).maketransition(class_name.getClass_name());

              //  ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(context,
               //         "change_bound",)

            }
        });

    //    int[] androidColors = context.getResources().getIntArray(R.array.randomColor);
   //    int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
  //    holder.constraintLayout.setBackgroundColor(randomAndroidColor);

    }

    @Override
    public int getItemCount() {
        return class_names.size();
    }


}
