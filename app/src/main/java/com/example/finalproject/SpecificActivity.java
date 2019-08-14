package com.example.finalproject;

import android.media.Image;

import java.io.Serializable;
import java.util.ArrayList;

public class SpecificActivity implements Serializable {
    private ArrayList<Image> myImageActivity;
    private String myDescripActivity;

    public void setMyDescripActivity(String myDescripActivity) {
        this.myDescripActivity = myDescripActivity;
    }

    public void setMyImageActivity(ArrayList<Image> myImageActivity) {
        this.myImageActivity = myImageActivity;
    }
    public ArrayList<Image> getMyImageActivity()
    {
        return this.myImageActivity;
    }
    public String getMyDescripActivity()
    {
        return this.myDescripActivity;
    }
}
