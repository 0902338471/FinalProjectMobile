package com.example.finalproject;

import java.util.Date;
import java.util.ArrayList;
public class ActivityDay {
    private MyDate myDate;
    private ArrayList<myItemActivity> daySchedule;
    public ActivityDay(){
        daySchedule=new ArrayList<>();
    }
    public void setDate(MyDate date) {
        this.myDate = date;
    }

    public void setDaySchedule(ArrayList<myItemActivity> daySchedule) {
        this.daySchedule = daySchedule;
    }
    public MyDate getDate()
    {
        return this.myDate;
    }
    public ArrayList<myItemActivity> getDaySchedule()
    {
        return this.daySchedule;
    }
    public int getSizeOfSchedule(){return daySchedule.size();}
    public void addingItemIntoDaySchedule(myItemActivity itemsin){daySchedule.add(daySchedule.size(),itemsin);}
    public void setItemInDaySchedule(int position,myItemActivity tmp){daySchedule.set(position,tmp);}
}
