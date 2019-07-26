package com.example.finalproject;

import java.util.Date;
import java.util.ArrayList;
public class ActivityDay {
    private MyDate myDate;
    private ArrayList<SpecificActivity> daySchedule;
    public ActivityDay(){}
    public void setDate(MyDate date) {
        this.myDate = date;
    }

    public void setDaySchedule(ArrayList<SpecificActivity> daySchedule) {
        this.daySchedule = daySchedule;
    }
    public MyDate getDate()
    {
        return this.myDate;
    }
    public ArrayList<SpecificActivity> getDaySchedule()
    {
        return this.daySchedule;
    }

}
