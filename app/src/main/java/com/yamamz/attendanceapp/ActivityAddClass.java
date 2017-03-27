package com.yamamz.attendanceapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import com.afollestad.materialdialogs.MaterialDialog;
import com.yamamz.attendanceapp.adapters.ClassAdapter;
import com.yamamz.attendanceapp.models.Class_name;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.Sort;

public class ActivityAddClass extends AppCompatActivity {

    private Realm realm;
    private CharSequence filename;
    private ClassAdapter mAdapter;
    private RecyclerView recyclerView;
    private List<Class_name> class_names = new ArrayList<>();
private  FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupWindowAnimations();
        }

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        setupRecyclerView();
 fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new MaterialDialog.Builder(ActivityAddClass.this).title(R.string.input).inputType
                        (InputType.TYPE_CLASS_TEXT).input(R.string.input_hint, R.string.input_prefill, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                filename = input;

                                if (!filename.toString().equals("")) {
                                    addClass();

                                }



                            }

                        }






                ).show();

            }
        });

        loadlocationsDatabase();

    }

    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            // Re-enter transition is executed when returning to this activity
            Slide slideTransition = new Slide();
        slideTransition.setSlideEdge(Gravity.LEFT);
        slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
        getWindow().setReenterTransition(slideTransition);
        getWindow().setExitTransition(slideTransition);
    }
    }


    private void setupRecyclerView() {

        mAdapter = new ClassAdapter(this, class_names);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new DeviderItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }


    public void loadlocationsDatabase(){
        if (class_names.size()>0){
            class_names.clear();
        }
        for (Class_name saveClassName : realm.where(Class_name.class).findAllSorted("class_name",
                Sort.ASCENDING)) {
            Class_name save = new Class_name(saveClassName.getClass_name());
            class_names.add(save);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    void addClass() {

        if (filename != null) {
            final Class_name class_name = new Class_name(filename.toString());
            class_names.add(class_name);
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm bgRealm) {
                    bgRealm.copyToRealmOrUpdate(class_name);

                  }},new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {

                    mAdapter.notifyDataSetChanged();
                }
            });

           
        }

    }

    public void maketransition(String className){



            ActivityOptionsCompat options=ActivityOptionsCompat.makeSceneTransitionAnimation(this);
            Intent intent=new Intent(this, Activity_add_student.class);
            intent.putExtra("className",className);
            startActivity(intent,options.toBundle());
        }

}

