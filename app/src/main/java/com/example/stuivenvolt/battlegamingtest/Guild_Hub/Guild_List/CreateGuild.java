package com.example.stuivenvolt.battlegamingtest.Guild_Hub.Guild_List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stuivenvolt.battlegamingtest.Guild_Hub.GuildHubFragment;
import com.example.stuivenvolt.battlegamingtest.Profile.PersonalProfileFragment;
import com.example.stuivenvolt.battlegamingtest.Profile.Weapons_List.WeaponListFragment;
import com.example.stuivenvolt.battlegamingtest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Logger;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.webkit.ConsoleMessage.MessageLevel.LOG;
import static java.lang.Thread.sleep;


public class CreateGuild extends DialogFragment implements AdapterView.OnItemSelectedListener{


    /**
     * Created by i7-4770 on 01/03/2018.
     */

    boolean dataSet;
    String id, nickName, rank;
    boolean isSmith;
    private TextView error, name, logo, motto, location;
    String result;
    boolean result2;


    FirebaseUser user;
    FirebaseAuth mAuth;
    boolean printed = false;


    public static CreateGuild newInstance(String param1, String param2) {
        CreateGuild fragment = new CreateGuild();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        id = getArguments().getString("ID");
        Log.e("before setData","nName: "+nickName);
        setData("NickName");
        Log.e("after setData","nName: "+nickName);
        isSmith = setDataB("IsSmith");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.create_guild, null);

        name = view.findViewById(R.id.edit_guild_name);
        logo = view.findViewById(R.id.edit_guild_logo);
        motto = view.findViewById(R.id.guild_motto);
        location = view.findViewById(R.id.edit_guild_location);



        builder.setView(view);
        final AlertDialog alert = builder.create();



        Button create = view.findViewById(R.id.Done);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("gremios");

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        GenericTypeIndicator<List<Object>> t = new GenericTypeIndicator<List<Object>>() {
                        };
                        List messages = snapshot.getValue(t);
                        if (!printed) {
                            Log.e("in onClick","nName: "+nickName);
                            myRef.child("" + messages.size()).child("Name").setValue(name.getText().toString());
                            myRef.child("" + messages.size()).child("Logo").setValue(logo.getText().toString());

                            myRef.child("" + messages.size()).child("Motto").setValue(motto.getText().toString());
                            myRef.child("" + messages.size()).child("Location").setValue(location.getText().toString());
                            myRef.child("" + messages.size()).child("Members").child("0").child("Email").setValue(id);
                            myRef.child("" + messages.size()).child("Members").child("0").child("IsSmith").setValue(isSmith);
                            myRef.child("" + messages.size()).child("Members").child("0").child("Name").setValue(nickName, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                    if (databaseError != null) {
                                        Log.e("Data could not be saved", databaseError.getMessage());
                                    } else {
                                        Log.e("Data saved successfully", "saved");
                                    }
                                }
                            });
                            myRef.child("" + messages.size()).child("Members").child("0").child("Rank").setValue("Leader");

                            printed = true;
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

                DatabaseReference miRef = FirebaseDatabase.getInstance().getReference("usuarios");
                miRef.child(id).child("Public").child("Guild").setValue(name.getText().toString());
                miRef.child(id).child("Public").child("Rank").setValue("Leader");

                Bundle bundle = new Bundle();
                bundle.putString("Guild",name.getText().toString());
                android.app.FragmentManager fm = getFragmentManager();
                GuildHubFragment ghf = new GuildHubFragment();
                ghf.setArguments(bundle);
                fm.beginTransaction().replace(R.id.content_frame, ghf).commit();
                alert.dismiss();
            }
        });

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

    public void setData(final String child){
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("usuarios");
        final String mail = user.getEmail().replace("."," ");
        final String[] test = new String[1];


        DatabaseReference profileRef = myRef.child(mail).child("Public").child(child);
        dataSet = false;
        profileRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue(String.class);
                if(data != null) {
                    nickName = data;

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
        Log.e("before return", "test:" + test[0]);
        Log.e("before return", "result:" + result);
    }

    public Boolean setDataB(final String child){
        result=null;
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("usuarios");
        final String mail = user.getEmail().replace("."," ");

        try {
            DatabaseReference profileRef = myRef.child(mail).child("Public").child(child);
            dataSet = false;
            profileRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot != null){
                        result2 = dataSnapshot.getValue(boolean.class);
                        Log.e("setData", "data:" + result2);
                    } else {
                        DatabaseReference profilePrivateRef = myRef.child(mail).child("Private").child(child);
                        profilePrivateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                result2 = dataSnapshot.getValue(boolean.class);
                                Log.e("setData", "" + result2);
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) { }
                        });
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) { }
            });
        }catch (Throwable e) { }
        return result2;
    }

}

