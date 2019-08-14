package com.example.finalproject;

import java.io.Serializable;

public class MyDate implements Serializable {
    private String month;
    private String dayOfMonth;
    private String year;
    public MyDate(){}
    public MyDate(String monthin,String dayin,String yearin)
    {
        month=monthin;
        dayOfMonth=dayin;
        year=yearin;
    }
    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(String year) {
        this.year = year;
    }
    public String getMonth(){
        return month;
    }
    public String getDayOfMonth()
    {
        return dayOfMonth;
    }
    public String getYear()
    {
        return year;
    }
}
