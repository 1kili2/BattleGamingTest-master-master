package com.example.stuivenvolt.battlegamingtest.Profile.Weapons_List;


/**
 * Created by i7-4770 on 23/02/2018.
 */

public class Weapons {

    private String Name, Price, Desc;

    public String getName(){return Name;}

    public String getPrice(){return Price;}

    public String getDesc(){return Desc;}

    Weapons(String name, String price, String desc){
        this.Name=name;
        this.Price=price;
        this.Desc=desc;
    }
}
