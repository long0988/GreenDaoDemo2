package com.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.BaseActivity.BaseActivity;
import com.greendaodemo2.R;

import java.util.Calendar;

/**
 * Created by qlshi on 2018/7/20.
 */

public class DateTimePickerActivity extends BaseActivity {
    private DatePicker mDatePicker;
    private TimePicker mTimePicker;
    private CalendarView mCalendarView;
    private int year;
    private int monthOfYear;
    private int dayOfMonth;
    private int minute;
    private int hourofday;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_datetime);
        mDatePicker = (DatePicker) findViewById(R.id.dPicker);
        mTimePicker = (TimePicker) findViewById(R.id.tPicker);
        mCalendarView = (CalendarView) findViewById(R.id.calendar);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        hourofday=calendar.get(Calendar.HOUR_OF_DAY);
        minute=calendar.getMaximum(Calendar.MINUTE);
        mDatePicker.init(year, monthOfYear, dayOfMonth, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Toast.makeText(DateTimePickerActivity.this, year + "-" + monthOfYear + "-" + dayOfMonth, Toast.LENGTH_SHORT).show();
            }
        });
        mTimePicker.setIs24HourView(true);
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Toast.makeText(DateTimePickerActivity.this, hourOfDay + "-" + minute, Toast.LENGTH_SHORT).show();
            }
        });
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(DateTimePickerActivity.this, year + "-" + month + "-" + dayOfMonth, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //显示日历
    public void showCalendar(View view) {
        DatePickerDialog dateDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Toast.makeText(DateTimePickerActivity.this, year + "-" + monthOfYear + "-" + dayOfMonth, Toast.LENGTH_SHORT).show();
            }
        }, year, monthOfYear, dayOfMonth);
        dateDialog.show();

    }

    public void showTimer(View view) {
        new TimePickerDialog(this,TimePickerDialog.THEME_TRADITIONAL, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            }
        }, hourofday,minute,true).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
