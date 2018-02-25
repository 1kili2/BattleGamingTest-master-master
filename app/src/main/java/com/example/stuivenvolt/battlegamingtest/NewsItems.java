package com.example.stuivenvolt.battlegamingtest;

/**
 * Created by i7-4770 on 25/02/2018.
 */

public class NewsItems {

    private String image, title, info;

    //public String getImage(){return image;}

    public String getNewsTitle(){return title;}

    public String getNewsInfo(){return info;}

    public NewsItems(String ttl, String info){
        //this.image=img;
        this.title=ttl;
        this.info=info;
    }
}