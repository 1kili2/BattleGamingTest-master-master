package com.example.stuivenvolt.battlegamingtest;


/**
 * Created by i7-4770 on 23/02/2018.
 */

public class DayItems {

    private String date, day, info, month;
    private int dayColor;

    public String getDate(){return date;}

    public String getDay(){return day;}

    public String getDayInfo(){return info;}

    public int getDayColor(){return dayColor;}

    public String getMonth(){return month;}

    public void setDayInfo(String info){
        this.info=info;
    }

    DayItems(String date, String day, String info, int dayColor, String month){
        this.date=date;
        this.day=day;
        this.info=info;
        this.dayColor=dayColor;
        this.month=month;
    }
}
