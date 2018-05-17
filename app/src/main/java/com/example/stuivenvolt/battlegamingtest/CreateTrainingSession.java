package com.example.stuivenvolt.battlegamingtest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
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

public class CreateTrainingSession extends DialogFragment implements AdapterView.OnItemSelectedListener{
    Spinner hours,minutes;
    private TextView guild,location,error;
    boolean inserted=false;


    public static CreateTrainingSession newInstance(String param1, String param2) {
        CreateTrainingSession fragment = new CreateTrainingSession();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.createtraining, null);
        error = view.findViewById(R.id.ErrorMessage);
        error.setVisibility(View.GONE);
        guild = view.findViewById(R.id.guildname);
        location = view.findViewById(R.id.location);
        builder.setView(view);
        final AlertDialog alert = builder.create();
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
                if(guild.getText().toString().equals("") || location.getText().toString().equals("")){
                    error.setVisibility(View.VISIBLE);

                }else {
                    final String hour = hours.getSelectedItem().toString();
                    final String minute = minutes.getSelectedItem().toString();

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
        hours = view.findViewById(R.id.Hour_Spinner);
        ArrayAdapter<CharSequence> houradapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.hours, android.R.layout.simple_spinner_item);
        houradapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        if (hours != null) {
            hours.setAdapter(houradapter);
        }
        minutes = view.findViewById(R.id.Minute_Spinner);
        ArrayAdapter<CharSequence> minuteadapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.minutes, android.R.layout.simple_spinner_item);
        minuteadapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        if (minutes != null) {
            minutes.setAdapter(minuteadapter);
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
}