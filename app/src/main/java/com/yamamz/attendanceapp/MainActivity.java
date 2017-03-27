package com.yamamz.attendanceapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.yamamz.attendanceapp.fragments.AttendanceFragment;
import com.yamamz.attendanceapp.fragments.CalendarFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private CalendarDay dateCal;
    private String CalenDaFrag;
    private Date date;
    private int FabClickFlag = 1;
    private CalendarDay saveIntanceCalendarDaY;

    public String getCalenDaFrag() {
        return CalenDaFrag;
    }

    public void setCalenDaFrag(String calenDaFrag) {
        CalenDaFrag = calenDaFrag;
    }

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private TextView dayWord;
    private TextView dayNumber;
    private MaterialCalendarView widget;
    private FloatingActionButton fab;
    private Animation slideDown, fadeIn, fadeOut, rotate;
    private String className;

    private String tagAttendanceFrag;

    public String getTagAttendanceFrag() {
        return tagAttendanceFrag;
    }

    public void setTagAttendanceFrag(String tagAttendanceFrag) {
        this.tagAttendanceFrag = tagAttendanceFrag;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent startingIntent = getIntent();
        className = startingIntent.getStringExtra("class_name");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupWindowAnimations();
        }

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(className);

        slideDown = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);
        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);

        fadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);

        rotate = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotate);


        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, new AttendanceFragment(),"attendanceTag")
                    .commit();
        }
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        final Date date = cal.getTime();
        String dayOfMonthStr = String.valueOf(dayOfMonth);
        dayWord = (TextView) findViewById(R.id.day);
        dayNumber = (TextView) findViewById(R.id.day1);
        dayWord.setText(new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()));
        dayNumber.setText(dayOfMonthStr);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        this.date=date;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                switch (FabClickFlag) {

                    case 1:




                       AttendanceFragment attendanceFragment = (AttendanceFragment)
                            MainActivity.this
                                .getSupportFragmentManager()
                                .findFragmentByTag(getTagAttendanceFrag());
                        attendanceFragment.SaveAttendance(className, date);

                      FragmentTransaction transaction1 = getSupportFragmentManager()
                            .beginTransaction();
                        transaction1.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_from_left);
                        transaction1.replace(R.id.content_main, new CalendarFragment());
                        transaction1.commit();
                        changeIconFabToCalendar();

                        break;

                    case 0:
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_from_left);
                        transaction.replace(R.id.content_main, new AttendanceFragment(),"attendanceTag");
                        transaction.commit();
                        changeIconFabToAttendance();
                        break;


                }

                fab.startAnimation(rotate);

            }
        });

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

    public void changeIconFabToCalendar() {

        fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_supervisor_account_white_48dp));
        FabClickFlag = 0;

    }

    public void changeIconFabToAttendance() {
        fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_date_range_white_48dp));
        FabClickFlag = 1;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_settings:
        }
        return super.onOptionsItemSelected(item);
    }

    public void SetDateOnitemChange(CalendarDay date) {
        int dayOfMonth = date.getCalendar().get(Calendar.DAY_OF_MONTH);
        String dayOfMonthStr = String.valueOf(dayOfMonth);
        dayWord.setText(new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getDate()));
        dayNumber.startAnimation(slideDown);
        dayWord.startAnimation(fadeOut);
        dayWord.startAnimation(fadeIn);
        if (dayOfMonthStr.length() == 2) {
            dayNumber.setText(dayOfMonthStr);
        } else {
            dayNumber.setText(String.format("0%s", dayOfMonthStr));
        }
        dateCal = date;
        this.date = date.getDate();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_from_left);
        transaction.replace(R.id.content_main, new AttendanceFragment(),"attendanceTag");
        transaction.commit();
        changeIconFabToAttendance();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            switch (FabClickFlag) {

                case 1:
                    finish();
                    changeIconFabToCalendar();
                    break;
                case 0:
                    FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                    transaction1.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_from_left, R.anim.enter_from_left, R.anim.exit_to_right);
                    transaction1.replace(R.id.content_main, new CalendarFragment());
                    transaction1.commit();
                    changeIconFabToAttendance();
                    break;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void SaveInstanceFrag(CalendarDay CalendarsaveIntance) {
        saveIntanceCalendarDaY = CalendarsaveIntance;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public String getClassName() {
        return className;
    }

    public Date getDate(){
        return date;
    }

    public CalendarDay getSaveInstanceFrag() {
        return saveIntanceCalendarDaY;
    }
}
