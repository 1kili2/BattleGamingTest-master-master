package com.example.stuivenvolt.battlegamingtest.Tournament;

public class Rounds {

    private String player1, player2, score, date;

    public String getP1(){return player1;}

    public String getP2(){return player2;}

    public String getScore(){return score;}

    public String getDate(){return date;}

    Rounds(String Player1, String Player2, String Score, String Date){
        this.player1=Player1;
        this.player2=Player2;
        this.score=Score;
        this.date=Date;
    }
}
