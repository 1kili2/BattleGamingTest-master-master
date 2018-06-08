package com.example.stuivenvolt.battlegamingtest.Tournament;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class Tournament implements Serializable {
    private String Creation, Guilds, Host, Name;
    private int Type, Win;
    List<String> Participants;
    List<Rounds> Rounds;

    public String getCreation(){return Creation;}

    public String getGuilds(){return Guilds;}

    public String getHost(){return Host;}

    public String getName(){return Name;}

    public int getType(){return Type;}

    public int getWin(){return Win;}

    public List<String> getParticipants(){return Participants;}

    public List<Rounds> getRounds(){return Rounds;}

    Tournament(String Creation, String Guilds, String Host, String Name, int Type, int Win, List<String> Participants, List<Rounds> Rounds){
        this.Creation=Creation;
        this.Guilds=Guilds;
        this.Host=Host;
        this.Name=Name;
        this.Type=Type;
        this.Win=Win;
        this.Participants=Participants;
        this.Rounds=Rounds;
        //validate();
    }

    private void writeObject(ObjectOutputStream o)
            throws IOException {

        o.writeObject(Creation);
        o.writeObject(Guilds);
        o.writeObject(Host);
        o.writeObject(Name);
        o.writeObject(Type);
        o.writeObject(Win);
        o.writeObject(Participants);
        o.writeObject(Rounds);
    }

    private void readObject(ObjectInputStream o)
            throws IOException, ClassNotFoundException {

        Creation = (String) o.readObject();
        Guilds = (String) o.readObject();
        Host = (String) o.readObject();
        Name = (String) o.readObject();
        Type = (int) o.readObject();
        Win = (int) o.readObject();
        Participants = (List<String>) o.readObject();
        Rounds = (List<com.example.stuivenvolt.battlegamingtest.Tournament.Rounds>) o.readObject();
        //validate();
    }

    /*private void validate(){
        if(Creation == null ||
                Creation.length() == 0 ||
                Guilds == null ||
                Guilds.length() == 0||
                Host == null ||
                Host.length() == 0||
                Name == null ||
                Name.length() == 0||
                Participants == null ||
                Rounds == null){

            throw new IllegalArgumentException();
        }
    }*/
}
