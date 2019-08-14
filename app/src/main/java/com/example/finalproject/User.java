package com.example.finalproject;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

public class User implements Serializable {
    private ArrayList<ActivityDay> userCalendar;
    private String UserID;
    private String UserPassword;
    private String UserEmail;
    public User(){

    }
    public User(String emailin,String userIDin,String UserPasswordIn,ArrayList<ActivityDay> userCalendarin)
    {
        this.UserEmail=emailin;
        this.UserID=userIDin;
        this.UserPassword=UserPasswordIn;
        this.userCalendar=userCalendarin;
    }
    public User(String emailin,String userIDin,String UserPasswordIn)
    {
        this.UserEmail=emailin;
        this.UserID=userIDin;
        this.UserPassword=UserPasswordIn;
        userCalendar=new ArrayList<>();
        for(int i=0;i<31*13;i++)
        {
            ActivityDay tmp=new ActivityDay();
            userCalendar.add(tmp);
        }
    }
    public String getEmail(){return UserEmail;}
    public String getUserID(){return UserID;}
    public String getUserPassword(){return UserPassword;}
    public ArrayList<ActivityDay> getuserCalendar(){return userCalendar;}
    public void setList(ArrayList<ActivityDay> listin){this.userCalendar=listin;}
    public void activateSpecificDate(int index,int dayofMonth,int month)
    {
        ActivityDay tmp=userCalendar.get(index);
        MyDate selectedDate=new MyDate(String.valueOf(month),String.valueOf(dayofMonth),"2019");
        tmp.setDate(selectedDate);
        tmp.activateDate();
        userCalendar.set(index,tmp);
    }
    public void UpdateCalendar(int index,ActivityDay item){userCalendar.set(index,item);}
    public void setEmail(String mailin){UserEmail=mailin;}
    public void setUserID(String IDin){UserID=IDin;}
    public void setUserPassword(String Passin){UserPassword=Passin;}
    //testing only

}
