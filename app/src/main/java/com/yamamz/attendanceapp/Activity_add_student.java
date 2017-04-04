package com.yamamz.attendanceapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.yamamz.attendanceapp.adapters.StudentAdapter;
import com.yamamz.attendanceapp.models.Class_name;
import com.yamamz.attendanceapp.models.Student;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class Activity_add_student extends AppCompatActivity {
    private View positiveAction;
    private String className;
    private Realm realm;
    private StudentAdapter mAdapter;
    private RecyclerView recyclerView;
    private EditText name, address, mobile, email;
    private List<Student> student_names = new ArrayList<>();

    private int flagNameHasValue=0;
    private int flagAddressHasValue=0;
    private int flagMobileHasValue=0;
    private int flagEmailHasValue=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent startingIntent = getIntent();
        className = startingIntent.getStringExtra("className");
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R
                .id.collapsing_toolbar);

        Realm.init(this);
        realm = Realm.getDefaultInstance();


        collapsingToolbarLayout.setTitle(className);
        initView();
        setupRecyclerView();
        loadlocationsDatabase();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupWindowAnimations();
        }
        ImageView imageViewAtendance=(ImageView) findViewById(R.id.ic_attendance);

        imageViewAtendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOptionsCompat options=ActivityOptionsCompat.makeSceneTransitionAnimation
                        (Activity_add_student.this);

                Intent intent=new Intent(Activity_add_student.this,Attendance_Activity.class);
                intent.putExtra("class_name",className);
                startActivity(intent,options.toBundle());


            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                showCustomView();

            }
        });
    }


    public void showCustomView() {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(R.string.title)
                .customView(R.layout.fragment_form_add_student, true)
                .positiveText(R.string.positive)
                .negativeText(android.R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        addStudent(name.getText().toString(), address.getText().toString(), mobile.getText
                                ().toString(), email.getText().toString());
                    }
                }).build();

        positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
        //noinspection ConstantConditions
        name = (EditText) dialog.getCustomView().findViewById(R.id.input_name);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()>0) {
                    if (flagAddressHasValue == 1 && flagEmailHasValue == 1 && flagMobileHasValue == 1) {
                        positiveAction.setEnabled(s.toString().trim().length() > 0);
                    }
                    flagNameHasValue=1;
                }
                else{
                    flagNameHasValue=0;
                    positiveAction.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        address = (EditText) dialog.getCustomView().findViewById(R.id.input_address);
        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()>0) {
                    if (flagNameHasValue == 1 && flagEmailHasValue == 1 && flagMobileHasValue ==
                            1) {
                        positiveAction.setEnabled(s.toString().trim().length() > 0);
                    }
                    flagAddressHasValue=1;
                }
                else {
                    flagAddressHasValue=0;
                    positiveAction.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mobile = (EditText) dialog.getCustomView().findViewById(R.id.input_mobile);
        mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()>0) {
                    if (flagAddressHasValue == 1 && flagEmailHasValue == 1 && flagNameHasValue ==
                            1) {
                        positiveAction.setEnabled(s.toString().trim().length() > 0);
                    }
                    flagMobileHasValue=1;
                }
                else {
                    flagMobileHasValue=0;
                    positiveAction.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        email = (EditText) dialog.getCustomView().findViewById(R.id.input_email);
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()>0) {
                    if (flagAddressHasValue == 1 && flagNameHasValue == 1 && flagMobileHasValue ==
                            1) {
                        positiveAction.setEnabled(s.toString().trim().length() > 0);
                    }
                    flagEmailHasValue=1;
                }
                else {
                    flagEmailHasValue=0;
                    positiveAction.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        dialog.show();
        positiveAction.setEnabled(false); // disabled by default
    }

    public void loadlocationsDatabase() {
        if (student_names.size() > 0) {
            student_names.clear();
        }
        Class_name results = realm.where(Class_name.class).equalTo
                ("class_name", className).findFirst();

        for (int i = 0; i < results.getStudents().size(); i++) {
            Student student = new Student(results.getStudents().get(i).getFullName());
            student_names.add(student);

        }

        mAdapter.notifyDataSetChanged();

    }

    void addStudent(final String name, final String address, final String mobile, final String email) {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                final Student student = new Student(name, address, mobile, email);
                Class_name results = bgRealm.where(Class_name.class).equalTo("class_name", className).findFirst();
                results.getStudents().add(student);
                student_names.add(student);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

                mAdapter.notifyDataSetChanged();


            }

        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);


    }

    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Re-enter transition is executed when returning to this activity
            Slide slideTransition = new Slide();
            slideTransition.setSlideEdge(Gravity.RIGHT);
            slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
            getWindow().setReenterTransition(slideTransition);
            getWindow().setEnterTransition(slideTransition);
            getWindow().setExitTransition(slideTransition);
        }
    }

    private void setupRecyclerView() {
        mAdapter = new StudentAdapter(student_names, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new DeviderItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }

    public String getClassName() {

        return className;
    }
}
