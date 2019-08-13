package com.example.finalproject;

import android.media.Image;

import java.io.Serializable;

public class myItemActivity implements Serializable {
    private String nameActivity;
    private Image avatar;
    private Image photoStatus;
    private String status;
    private MyDate dateInfoOfActivity;
    myItemActivity(){
        nameActivity="No Name";
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
    public void setAvatar(Image avatarin){this.avatar=avatarin;}
    public Image getAvatar(){return this.avatar;}
    public void setPhotoStatus(Image photoStatusin){this.photoStatus=photoStatusin;}
    public Image getPhotoStatus(){return this.photoStatus;}
    public void setStatus(String statusin){this.status=statusin;}
    public String getStatus(){return this.status;}
    public void setDateInfoOfActivity(MyDate datein){dateInfoOfActivity=datein;}
    public MyDate getDateInfoOfActivity(){return dateInfoOfActivity;}
}
