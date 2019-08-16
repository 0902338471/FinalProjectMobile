package com.example.finalproject;

import android.media.Image;

import java.io.Serializable;

public class myItemActivity implements Serializable {
    private String nameActivity;
    private String avatarURL;
    private String photoURL;
    private String status;
    private MyDate dateInfoOfActivity;
    private String ActiID;
    //private String dateInfoInString;
    myItemActivity(){
        nameActivity="No Name";
    }
    myItemActivity(String IDin,String namein,String statusin,MyDate datein,String photoURL)
    {
        this.ActiID=IDin;
        this.nameActivity=namein;
        //this.avatarURL=avatarURL;
        this.photoURL=photoURL;
        this.status=statusin;
        this.dateInfoOfActivity=datein;
    }
    myItemActivity(String namein,String status,MyDate datein)
    {
        nameActivity=namein;
        this.status=status;
        dateInfoOfActivity=datein;
    }
    public void setNameActivity(String nameActivity) {
        this.nameActivity = nameActivity;
    }
    public String getNameActivity()
    {
        return this.nameActivity;
    }
    public void setAvatar(String avatarin){this.avatarURL=avatarin;}
    public String getAvatar(){return this.avatarURL;}
    public void setPhotoStatus(String photoStatusin){this.photoURL=photoStatusin;}
    public String getPhotoStatus(){return this.photoURL;}
    public void setStatus(String statusin){this.status=statusin;}
    public String getStatus(){return this.status;}
    public void setDateInfoOfActivity(MyDate datein){dateInfoOfActivity=datein;}
    public MyDate getDateInfoOfActivity(){return dateInfoOfActivity;}
    public void setActiID(String IDin){this.ActiID=IDin;}
    public String getID(){return ActiID;}
    public String getStringDate(){return dateInfoOfActivity.getDayOfMonth()+'/'+dateInfoOfActivity.getMonth()+'/'+dateInfoOfActivity.getYear();}
}
