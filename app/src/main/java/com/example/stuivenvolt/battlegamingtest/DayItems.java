package com.example.stuivenvolt.battlegamingtest;

import android.graphics.Color;

/**
 * Created by i7-4770 on 23/02/2018.
 */

public class DayItems {

    private String date, day, info;
    private int dayColor;

    public String getDate(){return date;}

    public String getDay(){return day;}

    public String getDayInfo(){return info;}

    public int getDayColor(){return dayColor;}

    public void setDayInfo(String info){
        this.info=info;
    }

    public DayItems(String date, String day, String info, int dayColor){
        this.date=date;
        this.day=day;
        this.info=info;
        this.dayColor=dayColor;
    }
}
