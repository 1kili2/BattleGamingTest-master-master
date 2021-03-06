package com.example.stuivenvolt.battlegamingtest.Tournament;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.stuivenvolt.battlegamingtest.R;
import com.example.stuivenvolt.battlegamingtest.Tournament.Guilds.Guilds;
import com.example.stuivenvolt.battlegamingtest.Tournament.Members.Members;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * Created by i7-4770 on 01/03/2018.
 */

public class CreateTournament extends DialogFragment implements AdapterView.OnItemSelectedListener{
    Spinner score,type;
    Switch guilds;
    String guild;
    private TextView error;
    boolean inserted=false;
    RecyclerView participants;
    RecyclerView.Adapter part_adap;
    FirebaseUser user;
    FirebaseAuth mAuth;
    List<String> participantsList = new ArrayList<>();
    boolean printed = false, test = true;
    int switch_pos = 0;
    List<String> guildNames = null;
    List<String> guildMails = null;
    int setScore = 0;
    int setType = 0;
    List<Rounds> rounds = new ArrayList<>();
    String Creation, Guilds, Host, Name;
    int tipo, win;
    boolean isGuilds;
    Tournament tm;




    public static CreateTournament newInstance(String param1, String param2) {
        CreateTournament fragment = new CreateTournament();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction

        if(getArguments().get("Switch_Pos") != null){
            switch_pos = getArguments().getInt("Switch_Pos");
        }
        if(getArguments().getStringArrayList("Participants") != null){
            guildNames = getArguments().getStringArrayList("Participants");
        }
        if(getArguments().getInt("Score") != 0){
            setScore = getArguments().getInt("Score");
        }
        if(getArguments().getInt("Type") != 0){
            setType = getArguments().getInt("Type");
        }
        if(getArguments().getStringArrayList("ParticipantEmails") != null){
            guildMails = getArguments().getStringArrayList("ParticipantEmails");
            createRounds();
        }


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.createtournament, null);

        error = view.findViewById(R.id.ErrorMessage);
        error.setVisibility(View.GONE);
        builder.setView(view);
        final AlertDialog alert = builder.create();

        guild = getGuild();
        Log.e("after function", "guild: "+guild);
        guilds = view.findViewById(R.id.Guilds_Players_switch);
        score = view.findViewById(R.id.Score_Spinner);
        type = view.findViewById(R.id.Type_Spinner);

        participants = view.findViewById(R.id.tournament_participants);
        participants.setVisibility(View.GONE);

        //Setting the layout and Adapter for RecyclerView
        participants.setLayoutManager(new LinearLayoutManager(getActivity()));
        part_adap = new ParticipantsAdapter(participantsList);
        participants.setAdapter(part_adap);

        if(switch_pos==0){
            guilds.setEnabled(false);
            getTrusted(guilds);
        }else if(switch_pos==1){
            guilds.setChecked(false);
            guilds.setEnabled(false);
        }else if(switch_pos==2){
            guilds.setChecked(true);
            guilds.setEnabled(false);
        }



        if(guildNames!=null){
            for(int i=0;i<guildNames.size();i++) {
                participantsList.add(guildNames.get(i));
                part_adap.notifyItemInserted(participantsList.size() - 1);
                participants.setVisibility(View.VISIBLE);
            }
        }


        final String mail = user.getEmail().replace(".", " ");
        final String[] guild = new String[1];
        try{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference("usuarios");
            DatabaseReference profileRef = myRef.child(mail).child("Public").child("Guild");
            profileRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String data = dataSnapshot.getValue(String.class);
                    guild[0] = data;
                    Log.e("test", "guild: "+data);
                    Log.e("test", "guild: "+guild[0]);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } catch (Throwable e) {
        }

        final int[] numpart = {1};
        Button add = view.findViewById(R.id.add_participant);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int one = (int) score.getSelectedItemId();
                int two = (int) type.getSelectedItemId();
                ArrayList<String> three = (ArrayList<String>) participantsList;
                if(guilds.isChecked()){
                    DialogFragment newFragment = new Guilds();
                    Bundle bundle = new Bundle();
                    bundle.putInt("Switch_Pos",2);
                    bundle.putInt("Score",one);
                    bundle.putInt("Type",two);
                    bundle.putStringArrayList("Participants", three);
                    newFragment.setArguments(bundle);
                    newFragment.show(getFragmentManager(), "Boom");
                    alert.dismiss();
                }else{
                    DialogFragment newFragment = new Members();
                    Bundle bundle = new Bundle();
                    bundle.putInt("Switch_Pos",1);
                    bundle.putInt("Score",one);
                    bundle.putInt("Type",two);
                    bundle.putStringArrayList("Participants", three);
                    bundle.putString("Guild", guild[0]);
                    newFragment.setArguments(bundle);
                    newFragment.show(getFragmentManager(), "Boom");
                    alert.dismiss();
                }
            }
        });

        Button cancel = view.findViewById(R.id.cancel_action);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        Button ok = view.findViewById(R.id.ok_action);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //whatever you want
                //Log.e("guild Text",guild.getText().toString());

                tm = null;

                if(!test){
                    error.setVisibility(View.VISIBLE);

                }else {

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference myRef = database.getReference("eventos");

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            GenericTypeIndicator<List<Object>> t = new GenericTypeIndicator<List<Object>>() {
                            };
                            List messages = snapshot.getValue(t);
                            if(!printed) {
                                Name = messages.size() + " - " + guild[0] + " - " + getDate();
                                myRef.child("" + messages.size()).child("Name").setValue(Name);

                                Creation = getDate();
                                myRef.child("" + messages.size()).child("Creation").setValue(Creation);

                                isGuilds = guilds.isChecked();
                                myRef.child("" + messages.size()).child("Guilds").setValue(guilds.isChecked());

                                Host = guild[0];
                                myRef.child("" + messages.size()).child("Hosting Guild").setValue(guild[0]);

                                for (int i = 0; i < participantsList.size(); i++) {
                                    if(guilds.isChecked()){
                                        myRef.child("" + messages.size()).child("Participants").child("" + i).setValue(guildNames.get(i));
                                    }else {
                                        myRef.child("" + messages.size()).child("Participants").child("" + i).setValue(guildMails.get(i));
                                    }
                                }
                                for (int i = 0; i < rounds.size(); i++) {
                                    myRef.child("" + messages.size()).child("Rounds").child("" + i).setValue(rounds.get(i));
                                    Log.e("round", ""+i+rounds.get(i).getP1());
                                }

                                tipo = type.getSelectedItemPosition();
                                myRef.child("" + messages.size()).child("Type").setValue(tipo);

                                win = score.getSelectedItemPosition()+1;
                                myRef.child("" + messages.size()).child("Win Score").setValue(win);

                                tm = new Tournament(Creation,Guilds,Host,Name,tipo,win,guildMails,rounds);

                                android.app.FragmentManager fm = getFragmentManager();
                                ChampionshipFragment cf = new ChampionshipFragment();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("Tournament", tm);
                                cf.setArguments(bundle);
                                fm.beginTransaction().replace(R.id.content_frame, cf).commit();

                                printed = true;
                            }
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });

                    try {
                        sleep(1300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    alert.dismiss();
                }
            }
        });

        ArrayAdapter<CharSequence> houradapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.score, android.R.layout.simple_spinner_item);
        houradapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        if (score != null) {
            score.setAdapter(houradapter);
        }

        ArrayAdapter<CharSequence> minuteadapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.type, android.R.layout.simple_spinner_item);
        minuteadapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        if (type != null) {
            type.setAdapter(minuteadapter);
        }
        // Create the AlertDialog object and return it
        return alert;
    }

    @Override
    public void onResume() {
        super.onResume();
        score.setSelection(setScore);
        Log.e("setscore", "" + setScore +" "+ score.getItemAtPosition(setScore));
        type.setSelection(setType);
        Log.e("setType", "" + setType +" "+ type.getItemAtPosition(setType));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String mSpinnerLabel = adapterView.getItemAtPosition(i).toString();
        Log.e("Spinner Laber", mSpinnerLabel);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //Log.e(TAG, "onNothingSelected: ");
    }

    private String getDate(){
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
    }
    private String getGuild() {
        final String mail = user.getEmail().replace(".", " ");
        final String[] guild = new String[1];
        try{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference("usuarios");
            DatabaseReference profileRef = myRef.child(mail).child("Public").child("Guild");
            profileRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String data = dataSnapshot.getValue(String.class);
                    guild[0] = data;
                    Log.e("test", "guild: "+data);
                    Log.e("test", "guild: "+guild[0]);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } catch (Throwable e) {
        }
        Log.e("end of getguild", "guild: "+guild[0]);
        return guild[0];
    }

    private void getTrusted(final Switch guild_Switch) {
        final String mail = user.getEmail().replace(".", " ");
        try{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference("usuarios");
            DatabaseReference profileRef = myRef.child(mail).child("Public").child("Rank");
            profileRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String data = dataSnapshot.getValue(String.class);
                    if(data.equals("Trusted")||data.equals("Leader")){
                        guild_Switch.setEnabled(true);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } catch (Throwable e) {
        }
    }



    private void createRounds(){
        String used = new String();
        Random rand = new Random();

        Rounds round;
        int counter=0, max;
        if(guildMails.size()%2 == 0){
            max = guildMails.size()/2;
        }else{
            max = (guildMails.size()/2)+1;
        }
        Log.e("max size", ""+max);
        while(counter < max){
            int r1 = rand.nextInt(guildMails.size());
            int r2 = rand.nextInt(guildMails.size());
            if(used.equals("") && r1 != r2) {
                round = new Rounds(guildMails.get(r1), guildMails.get(r2), "0-0", getDate());
                rounds.add(round);
                used = used + r1 + "" + r2;
                counter++;
            }else if(used.equals("")){
                break;
            }else if (used.contains(""+r1) && used.contains(""+r2) && r1 != r2) {
                round = new Rounds(guildMails.get(r1), guildMails.get(r2), "0-0", getDate());
                rounds.add(round);
                used = used + r1 + "" + r2;
                counter++;
            }else if(guildMails.size()%2 != 0 && used.length()+1 == guildMails.size() && !used.contains(""+r1)){
                round = new Rounds(guildMails.get(r1), "", "0-0", getDate());
                rounds.add(round);
                used = used + r1;
                counter++;

            }
        }
    }
}