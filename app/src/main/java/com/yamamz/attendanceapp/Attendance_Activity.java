package com.yamamz.attendanceapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.philliphsu.bottomsheetpickers.BottomSheetPickerDialog;
import com.philliphsu.bottomsheetpickers.date.DatePickerDialog;
import com.philliphsu.bottomsheetpickers.time.BottomSheetTimePickerDialog;
import com.yamamz.attendanceapp.fragments.AttendanceFragment;
import com.yamamz.attendanceapp.fragments.HistoryAttendanceFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Attendance_Activity extends AppCompatActivity implements
        BottomSheetTimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
          private static final String TAG = "Activity_attendance";


    private TextView dayWord;
    private TextView dayNumber;
    private Animation slideDown, fadeIn, fadeOut, rotate;
    private String className;
    private Date dateOfAttendance;
    private Date dateset;

    private FloatingActionButton fab;
    private int fabState = 0;

    private String tabAttendance;

    public String getTabAttendance() {
        return tabAttendance;
    }

    public void setTabAttendance(String tabAttendance) {
        this.tabAttendance = tabAttendance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Calendar calenDar = Calendar.getInstance();
        dateOfAttendance = calenDar.getTime();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

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



        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (fabState) {

                    case 0:

                        DialogFragment dialog = createDialog();
                        dialog.show(getSupportFragmentManager(), TAG);
                        break;
                    case 1:
                        AttendanceFragment attendanceFragment=(AttendanceFragment)Attendance_Activity.this
                                .getSupportFragmentManager()
                                .findFragmentByTag(getTabAttendance());
                        attendanceFragment.saveItems();


                        fabState = 0;
                        fab.startAnimation(rotate);
                        fab.getAnimation().setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {

                                fab.setImageDrawable(ContextCompat.getDrawable(Attendance_Activity.this, R
                                        .drawable
                                        .ic_date_range_white_48dp));

                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });


                        break;
                }


            }
        });


    }

    public String getClassName(){

        return className;
    }

   public void changeDrawable() {
        if (fabState == 0) {
            fab.startAnimation(rotate);
            fab.getAnimation().setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    fab.setImageDrawable(ContextCompat.getDrawable(Attendance_Activity.this, R
                            .drawable
                            .ic_playlist_add_check_white_36dp));
                    fabState = 1;

                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });


        }
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


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }



    private void setupViewPager(ViewPager viewPager) {

        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new AttendanceFragment(),"Attendance");
        adapter.addFragment(new HistoryAttendanceFragment(),"History");
        viewPager.setAdapter(adapter);

    }
    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        Adapter(FragmentManager fm) {
            super(fm);
        }

        void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

public Date getDate(){
    return dateOfAttendance;
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
        Date date = cal.getTime();
        dateset = cal.getTime();
        dateOfAttendance = cal.getTime();
                AttendanceFragment attendanceFragment=(AttendanceFragment)Attendance_Activity.this
                .getSupportFragmentManager()
                .findFragmentByTag(getTabAttendance());
                 attendanceFragment.addItems();



    }
}
