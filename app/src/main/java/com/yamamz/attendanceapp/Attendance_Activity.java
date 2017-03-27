package com.yamamz.attendanceapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.philliphsu.bottomsheetpickers.BottomSheetPickerDialog;
import com.philliphsu.bottomsheetpickers.date.DatePickerDialog;
import com.philliphsu.bottomsheetpickers.time.BottomSheetTimePickerDialog;
import com.yamamz.attendanceapp.adapters.AtenndanceAdapter;
import com.yamamz.attendanceapp.models.Attendance;
import com.yamamz.attendanceapp.models.Class_name;
import com.yamamz.attendanceapp.models.DateAttendance;
import com.yamamz.attendanceapp.models.Student;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;

public class Attendance_Activity extends AppCompatActivity implements
        BottomSheetTimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private static final boolean USE_BUILDERS = false;
    private static final String TAG = "Activity_attendance";

    private RecyclerView recyclerView;
    private View RootView;
    private AtenndanceAdapter mAdapter;
    private Realm realm;
    private List<Attendance> attendanceList = new ArrayList<>();
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private TextView dayWord;
    private TextView dayNumber;
    private FloatingActionButton fab;
    private Animation slideDown, fadeIn, fadeOut, rotate;
    private String className;
    private Date dateOfAttendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        Intent startingIntent = getIntent();
        className = startingIntent.getStringExtra("class_name");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupWindowAnimations();
        }

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(className);

        setDateLogo();

        slideDown = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);
        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);
        rotate = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotate);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        setupRecyclerView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment dialog = createDialog();
                dialog.show(getSupportFragmentManager(), TAG);



            }
        });

        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
       loadadateFromRealm(date);
    }

    private DialogFragment createDialog() {

        return createDialogWithSetters();

    }


    private DialogFragment createDialogWithSetters() {
        BottomSheetPickerDialog.Builder builder = null;
        BottomSheetPickerDialog dialog = null;

        Calendar now = Calendar.getInstance();
        dialog = DatePickerDialog.newInstance(
                Attendance_Activity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));

        DatePickerDialog dateDialog = (DatePickerDialog) dialog;
        //  dateDialog.setMinDate(now);
        Calendar max = Calendar.getInstance();
        max.add(Calendar.YEAR, 10);
        dateDialog.setMaxDate(max);
        dateDialog.setYearRange(1970, 2032);
        dateDialog.setHeaderTextColorSelected(0xFFFF4081);
        dateDialog.setHeaderTextColorUnselected(0x4AFF4081);
        dateDialog.setDayOfWeekHeaderTextColorSelected(0xFFFF4081);
        dateDialog.setDayOfWeekHeaderTextColorUnselected(0x4AFF4081);
        dialog.setThemeDark(true);

        return dialog;

    }

    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Re-enter transition is executed when returning to this activity
            Slide slideTransition = new Slide();
            slideTransition.setSlideEdge(Gravity.LEFT);
            slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
            getWindow().setReenterTransition(slideTransition);
            getWindow().setEnterTransition(slideTransition);
            getWindow().setExitTransition(slideTransition);
        }
    }

    public void SetDateOnitemChange(Calendar date) {
        int dayOfMonth = date.get(Calendar.DAY_OF_MONTH);
        String dayOfMonthStr = String.valueOf(dayOfMonth);
        dayWord.setText(new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()));
        dayNumber.startAnimation(slideDown);
        dayWord.startAnimation(fadeOut);
        dayWord.startAnimation(fadeIn);
        if (dayOfMonthStr.length() == 2) {
            dayNumber.setText(dayOfMonthStr);
        } else {
            dayNumber.setText(String.format("0%s", dayOfMonthStr));
        }
    }

    void setDateLogo() {
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        final Date date = cal.getTime();
        String dayOfMonthStr = String.valueOf(dayOfMonth);
        dayWord = (TextView) findViewById(R.id.day);
        dayNumber = (TextView) findViewById(R.id.day1);
        dayWord.setText(new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()));
        dayNumber.setText(dayOfMonthStr);
    }

    private void setupRecyclerView() {
        mAdapter = new AtenndanceAdapter(Attendance_Activity.this, attendanceList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }


    public void loadadateFromRealm(Date dateAttend) {

        if (attendanceList.size() > 0) {
            attendanceList.clear();

        }


        String classname = className;


        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
        String dateFormat = DATE_FORMAT.format(dateAttend);
        String attendateDatePrimary = classname.concat(dateFormat);


        final DateAttendance results = realm.where(DateAttendance.class).equalTo
                ("attendancePrimaryKey", attendateDatePrimary).findFirst();
        try {
            for (int i = 0; i < results.getAttendanceRealmList().size(); i++) {
                Attendance attendance = new Attendance(results.getAttendanceRealmList()
                        .get(i).getStudent(),
                        results.getAttendanceRealmList().get(i).getStatus());
                attendanceList.add(attendance);
            }
            mAdapter.notifyDataSetChanged();
        } catch (Exception ignored) {

        }

        if (results==null) {
            Toast.makeText(this,"Ok",Toast.LENGTH_LONG).show();
                loadlocationsDatabase();

        }

        mAdapter.notifyDataSetChanged();

    }

    public void loadlocationsDatabase() {
        if (attendanceList.size() > 0) {
            attendanceList.clear();
        }

        Class_name results = realm.where(Class_name.class).equalTo
                ("class_name", className).findFirst();

        for (int i = 0; i < results.getStudents().size(); i++) {
            Student student = new Student(results.getStudents().get(i).getFullName());
            Attendance attendance = new Attendance(student,
                    "Leave");
            attendanceList.add(attendance);
            mAdapter.notifyDataSetChanged();


        }
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
        Toast.makeText(this,"Success",Toast.LENGTH_LONG).show();
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
    public void onTimeSet(ViewGroup viewGroup, int hourOfDay, int minute) {

    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = new java.util.GregorianCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SetDateOnitemChange(cal);
        Date date=cal.getTime();
        loadadateFromRealm(date);

    }
}
