package com.example.stuivenvolt.battlegamingtest.Profile.Weapons_List.Add_Weapon;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stuivenvolt.battlegamingtest.Profile.Weapons_List.WeaponListFragment;
import com.example.stuivenvolt.battlegamingtest.R;
import com.example.stuivenvolt.battlegamingtest.StartUp.MainActivity;
import com.example.stuivenvolt.battlegamingtest.Tournament.CreateTournament;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;


public class AddWeapon extends DialogFragment implements AdapterView.OnItemSelectedListener{


    /**
     * Created by i7-4770 on 01/03/2018.
     */

    String id;
    private TextView error, name, price, desc;
    FirebaseUser user;
    FirebaseAuth mAuth;
    boolean printed = false;


    public static AddWeapon newInstance(String param1, String param2) {
        AddWeapon fragment = new AddWeapon();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.add_weapon, null);
        name = view.findViewById(R.id.edit_weapon_name);
        price = view.findViewById(R.id.edit_weapon_price);
        desc = view.findViewById(R.id.weapon_description);

        builder.setView(view);
        final AlertDialog alert = builder.create();



        Button ok = view.findViewById(R.id.Done);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("usuarios/"+id+"/Weapon List");

                if(name.getText()==null || price.getText()==null || desc.getText()==null){
                    Toast.makeText(getActivity(), R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
                }else {
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            GenericTypeIndicator<List<Object>> t = new GenericTypeIndicator<List<Object>>() {
                            };
                            List messages = snapshot.getValue(t);
                            if (!printed) {

                                myRef.child("" + messages.size()).child("Name").setValue(name.getText().toString());
                                myRef.child("" + messages.size()).child("Price").setValue(price.getText().toString());
                                myRef.child("" + messages.size()).child("Description").setValue(desc.getText().toString());

                                printed = true;
                            }
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });

                    final android.app.FragmentManager fm = getFragmentManager();
                    fm.beginTransaction().replace(R.id.content_frame, new WeaponListFragment()).commit();
                    alert.dismiss();
                }
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


}

