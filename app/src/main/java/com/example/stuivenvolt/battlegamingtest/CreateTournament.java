package com.example.stuivenvolt.battlegamingtest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Created by i7-4770 on 01/03/2018.
 */

public class CreateTournament extends DialogFragment implements AdapterView.OnItemSelectedListener{
    Spinner score,type;
    private String mSpinnerLabel = "";
    private TextView guild,location,error;
    boolean inserted=false;


    public static CreateTournament newInstance(String param1, String param2) {
        CreateTournament fragment = new CreateTournament();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        LayoutInflater inflater = getActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.createtournament, null);
        error=(TextView) view.findViewById(R.id.ErrorMessage);
        error.setVisibility(View.GONE);
        guild=(TextView) view.findViewById(R.id.guildname);
        location=(TextView) view.findViewById(R.id.location);
        builder.setView(view);
        final AlertDialog alert = builder.create();
        Button cancel = (Button) view.findViewById(R.id.cancel_action);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        Button ok = (Button) view.findViewById(R.id.ok_action);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //whatever you want
                //Log.e("guild Text",guild.getText().toString());
                if(guild.getText().toString().equals("") || location.getText().toString().equals("")){
                    error.setVisibility(View.VISIBLE);

                }else {
                    final String hour = score.getSelectedItem().toString();
                    final String minute = type.getSelectedItem().toString();

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    Log.e("Month in create session", getArguments().getString("Month"));
                    final DatabaseReference myRef = database.getReference("agenda/eventos/2018/" + getArguments().getString("Month"));
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            GenericTypeIndicator<List<Object>> t = new GenericTypeIndicator<List<Object>>() {
                            };
                            Log.e("test", "" + t);
                            List messages = snapshot.getValue(t);

                            for (int i = 0; i < messages.size(); i++) {
                                String[] splitinfo = myRef.child(messages.get(i).toString()).getKey().split(",");
                                String[] ultimatesplit = splitinfo[splitinfo.length - 1].split("\\=|\\}");
                                Log.e("ultimateSplit date", ultimatesplit[1]);
                                Log.e("Arguments date", getArguments().getString("Date"));
                                if (getArguments().getString("Date").equals(ultimatesplit[1])) {
                                    myRef.child("" + i).child("Entrene").child(hour + ":" + minute).child(guild.getText().toString()).setValue(location.getText().toString());
                                    inserted = true;
                                    break;
                                }

                            }
                            if (inserted == false) {
                                myRef.child("" + messages.size()).child("Day").setValue(getArguments().getString("Date"));
                                myRef.child("" + messages.size()).child("Entrene").child(hour + ":" + minute).child(guild.getText().toString()).setValue(location.getText().toString());
                                inserted = true;
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
        score = (Spinner) view.findViewById(R.id.Score_Spinner);
        ArrayAdapter<CharSequence> houradapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.score, android.R.layout.simple_spinner_item);
        houradapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        if (score != null) {
            score.setAdapter(houradapter);
        }
        type = (Spinner) view.findViewById(R.id.Type_Spinner);
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
        mSpinnerLabel = adapterView.getItemAtPosition(i).toString();
        Log.e("Spinner Laber", mSpinnerLabel);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //Log.e(TAG, "onNothingSelected: ");
    }
}