package com.example.finalproject;

import java.io.Serializable;

public class myItemActivity implements Serializable {
    private String nameActivity;
    myItemActivity(){
        nameActivity="No Name";
    }
    myItemActivity(String namein)
    {
        nameActivity=namein;
    }
    public void setNameActivity(String nameActivity) {
        this.nameActivity = nameActivity;
    }
    public String getNameActivity()
    {
        return this.nameActivity;
    }
}
