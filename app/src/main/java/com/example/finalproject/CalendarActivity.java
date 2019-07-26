package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

public class CalendarActivity extends AppCompatActivity {
    CalendarView simpleCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        simpleCalendarView=(CalendarView)findViewById(R.id.simpleCalendarView);
        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                callingSpecificActivity(calendarView,year,month,dayOfMonth);
                //Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void callingSpecificActivity(CalendarView calendarView, int year, int month, int dayOfMonth) {
        Intent i=new Intent(this,SpecificDate.class);
        i.putExtra("CurrentDate",String.valueOf(dayOfMonth));
        i.putExtra("CurrentYear",String.valueOf(year));
        i.putExtra("CurrentMonth",String.valueOf(month));
        startActivity(i);
    }

}
