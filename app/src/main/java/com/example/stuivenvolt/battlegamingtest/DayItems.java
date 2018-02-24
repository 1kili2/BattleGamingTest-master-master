package com.example.stuivenvolt.battlegamingtest;

/**
 * Created by i7-4770 on 23/02/2018.
 */

public class DayItems {

    private String date, day, info;

    public String getDate(){return date;}

    public String getDay(){return day;}

    public String getDayInfo(){return info;}

    public DayItems(String date, String day, String info){
        this.date=date;
        this.day=day;
        this.info=info;
    }
}
