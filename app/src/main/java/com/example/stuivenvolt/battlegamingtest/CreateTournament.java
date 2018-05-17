package com.example.stuivenvolt.battlegamingtest;

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
import android.widget.Toast;

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

import static java.lang.Thread.sleep;

/**
 * Created by i7-4770 on 01/03/2018.
 */

public class CreateTournament extends DialogFragment implements AdapterView.OnItemSelectedListener{
    Spinner score,type;
    Switch guilds;
    private TextView error;
    boolean inserted=false;
    RecyclerView participants;
    RecyclerView.Adapter part_adap;
    FirebaseUser user;
    FirebaseAuth mAuth;
    List<String> participantsList = new ArrayList<>();
    boolean printed = false, test = true;


    public static CreateTournament newInstance(String param1, String param2) {
        CreateTournament fragment = new CreateTournament();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.createtournament, null);

        error = view.findViewById(R.id.ErrorMessage);
        error.setVisibility(View.GONE);
        builder.setView(view);
        final AlertDialog alert = builder.create();

        guilds = view.findViewById(R.id.Guilds_Players_switch);
        score = view.findViewById(R.id.Score_Spinner);
        type = view.findViewById(R.id.Type_Spinner);

        participants = view.findViewById(R.id.tournament_participants);
        participants.setVisibility(View.GONE);

        //Setting the layout and Adapter for RecyclerView
        participants.setLayoutManager(new LinearLayoutManager(getActivity()));
        part_adap = new ParticipantsAdapter(participantsList);
        participants.setAdapter(part_adap);

        final int[] numpart = {1};
        Button add = view.findViewById(R.id.add_participant);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    guilds.setEnabled(false);
                    participantsList.add("participant" + numpart[0]);
                    part_adap.notifyItemInserted(participantsList.size() - 1);
                    participants.setVisibility(View.VISIBLE);
                    participants.setMinimumHeight(20*numpart[0]);
                    numpart[0]++;
                } catch(NumberFormatException e) {
                    Toast.makeText(getActivity(), "The field is empty",
                            Toast.LENGTH_SHORT).show();
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
                if(!test){
                    error.setVisibility(View.VISIBLE);

                }else {
                    final String hour = score.getSelectedItem().toString();
                    final String minute = type.getSelectedItem().toString();

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference myRef = database.getReference("eventos");

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            GenericTypeIndicator<List<Object>> t = new GenericTypeIndicator<List<Object>>() {
                            };
                            List messages = snapshot.getValue(t);
                            if(!printed) {
                                myRef.child("" + messages.size()).child("Creation").setValue(getDate());
                                myRef.child("" + messages.size()).child("Hosting Guild").setValue(getGuild());
                                for (int i = 0; i < participantsList.size(); i++) {
                                    myRef.child("" + messages.size()).child("Participants").child("" + i).setValue(participantsList.get(i));
                                }
                                myRef.child("" + messages.size()).child("Type").setValue(type.getSelectedItemPosition());
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
                    final android.app.FragmentManager fm = (getActivity()).getFragmentManager();
                    fm.beginTransaction().replace(R.id.content_frame, new CalenderFragment()).commit();
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
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } catch (Throwable e) {
        }
        return guild[0];
    }
}