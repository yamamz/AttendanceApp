package com.yamamz.attendanceapp.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.yamamz.attendanceapp.MainActivity;
import com.yamamz.attendanceapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {


    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private CalendarDay calendarDay;
    private MaterialCalendarView widget;
    private View RootView;

    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String myTag = getTag();
        ((MainActivity) getActivity()).setCalenDaFrag(myTag);

        RootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        initViews();


        widget.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                // check if weekday is sunday
                return day.getCalendar().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
            }

            @Override
            public void decorate(DayViewFacade view) {
                // add red foreground span
                view.addSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.FirsDayofAweek)));
            }
        });
        return RootView;

    }

    void initViews() {
        widget = (MaterialCalendarView) RootView.findViewById(R.id.calendarView);

        try {
            calendarDay = ((MainActivity) getActivity()).getSaveInstanceFrag();
        } catch (Exception ignored) {

        }

        if (calendarDay == null) {
            final Calendar instance = Calendar.getInstance();
            widget.setSelectedDate(instance.getTime());


        } else {

            widget.setSelectedDate(calendarDay.getDate());
        }
        widget.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                ((MainActivity) getActivity()).SetDateOnitemChange(date);
                calendarDay = date;

            }


        });


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (calendarDay != null) {
            ((MainActivity) getActivity()).SaveInstanceFrag(calendarDay);
            // Toast.makeText(getActivity(),calendarDay.toString(),Toast.LENGTH_LONG).show();
        }
    }
}
