package com.example.stuivenvolt.battlegamingtest.Guild_Hub.Members_List;

public class SimpleMembers {
    private String Name, Email, Rank;
    private boolean IsSmith;

    public String getName(){return Name;}

    public boolean getSmith(){return IsSmith;}

    public String getEmail(){return Email;}

    public String getRank(){return Rank;}

    SimpleMembers(String name, boolean isSmith, String mail, String rank){
        this.Name=name;
        this.IsSmith=isSmith;
        this.Email=mail;
        this.Rank=rank;
    }
}
