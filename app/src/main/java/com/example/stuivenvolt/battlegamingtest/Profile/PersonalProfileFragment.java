package com.example.stuivenvolt.battlegamingtest.Profile;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stuivenvolt.battlegamingtest.News.NewsFragment;
import com.example.stuivenvolt.battlegamingtest.Profile.Weapons_List.WeaponListFragment;
import com.example.stuivenvolt.battlegamingtest.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PersonalProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PersonalProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalProfileFragment extends android.app.Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    DatabaseReference myRef;
    FirebaseUser user;
    FirebaseAuth mAuth;
    private String[] fullName;
    private View view;
    boolean dataSet = false;
    private EditText profilePhone, profileName, profileSurname, profileNickName, profileMotto;
    private TextView profileGuild, profileAdress;
    Switch isSmith;
    Button armory;

    private OnFragmentInteractionListener mListener;

    public PersonalProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonalProfileFragment.
     */
    public static PersonalProfileFragment newInstance(String param1, String param2) {
        PersonalProfileFragment fragment = new PersonalProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle(getString(R.string.profile_title));
        myRef = FirebaseDatabase.getInstance().getReference("usuarios");
        view = inflater.inflate(R.layout.fragment_personal_profile, container, false);

        profilePhone = view.findViewById(R.id.profile_set_Phone_Number);
        setData("Phone Number", profilePhone);

        profileName = view.findViewById(R.id.profile_set_Name);
        setData("Name", profileName);

        profileSurname = view.findViewById(R.id.profile_set_Surname);
        setData("Name", profileSurname);

        EditText profileEmail = view.findViewById(R.id.profile_set_Email);
        profileEmail.setText(user.getEmail());

        profileNickName = view.findViewById(R.id.profile_set_Nickname);
        setData("NickName", profileNickName);

        profileMotto = view.findViewById(R.id.profile_Motto);
        setData("Motto", profileMotto);

        armory = view.findViewById(R.id.weapons);
        armory.setEnabled(false);

        isSmith = view.findViewById(R.id.isSmith);
        setData("IsSmith", isSmith);

        profileGuild = view.findViewById(R.id.profile_set_Guild);
        setData("Guild", profileGuild);

        profileAdress = view.findViewById(R.id.profile_set_Adress);
        setData("Adress", profileAdress);



        isSmith.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(isSmith.isChecked()){
                    armory.setEnabled(true);
                }else{
                    armory.setEnabled(false);
                }
            }
        });

        armory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Bundle bundle = new Bundle();
                bundle.putString("Email", user.getEmail().replace("."," "));
                android.app.FragmentManager fm = getFragmentManager();
                WeaponListFragment wlf = new WeaponListFragment();
                wlf.setArguments(bundle);
                fm.beginTransaction().replace(R.id.content_frame, wlf).addToBackStack("PersonalProfile").commit();
            }
        });
        if(isSmith.isChecked()){
            armory.setEnabled(true);
        }


        Button saveBTN = view.findViewById(R.id.btn_save_profile);
        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String name = profileName.getText().toString() + " " + profileSurname.getText().toString();
                String mail = user.getEmail().replace("."," ");
                DatabaseReference saveRef = FirebaseDatabase.getInstance().getReference("usuarios/"+mail);

                saveRef.child("Public").child("IsSmith").setValue(isSmith.isChecked());
                saveRef.child("Public").child("IsTrusted").setValue("false");
                saveRef.child("Public").child("Name").setValue(name);
                saveRef.child("Public").child("NickName").setValue(profileNickName.getText().toString());
                saveRef.child("Public").child("Rank").setValue("Member");
                saveRef.child("Public").child("Motto").setValue(profileMotto.getText().toString());

                saveRef.child("Private").child("Phone Number").setValue(profilePhone.getText().toString());
                saveRef.child("Private").child("Adress").setValue("The Viking Inn");

                Toast.makeText(getActivity(), R.string.successful_save, Toast.LENGTH_LONG).show();
                if(isSmith.isChecked()){
                    armory.setEnabled(true);
                }
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isSmith.isChecked()){
            armory.setEnabled(true);
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setData(final String child, final EditText field){
        final String mail = user.getEmail().replace("."," ");
        try {
            DatabaseReference profileRef = myRef.child(mail).child("Public").child(child);
            dataSet = false;
            profileRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String data = dataSnapshot.getValue(String.class);
                    if(data != null) {
                        if (field.equals(view.findViewById(R.id.profile_set_Name))) {
                            if(!data.equals(" ")) {
                                try {
                                    fullName = data.split(" ");
                                } catch (Throwable e) {
                                    fullName[0] = data;
                                    fullName[1] = " ";
                                }
                                field.setText(fullName[0]);
                            }
                        } else if (field.equals(view.findViewById(R.id.profile_set_Surname))) {
                            String surname = "";
                            try {
                                for (int i = 1; i < fullName.length; i++) {
                                    surname = surname + " " + fullName[i];
                                }
                            } catch (Throwable e) { Log.e(" public Profile " + child, "error: " + e); }
                            field.setText(surname.trim());
                        } else {
                            field.setText(data);
                            dataSet = true;
                        }
                    } else {
                        DatabaseReference profilePrivateRef = myRef.child(mail).child("Private").child(child);
                        profilePrivateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String data = dataSnapshot.getValue(String.class);
                                if (field.equals(view.findViewById(R.id.profile_set_Name))) {
                                    try {
                                        fullName = data.split(" ");
                                    } catch (Throwable e) {
                                        fullName[0] = data;
                                        fullName[1] = "";
                                    }
                                    field.setText(fullName[0]);
                                } else if (field.equals(view.findViewById(R.id.profile_set_Surname))) {
                                    String surname = "";
                                    try {
                                        for (int i = 1; i < fullName.length; i++) {
                                            surname = surname + " " + fullName[i];
                                        }
                                    } catch (Throwable e) { Log.e(" private Profile " + child, "error: " + e); }
                                    field.setText(surname.trim());
                                } else {
                                    field.setText(data);
                                    dataSet = true;
                                }
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
    }

    public void setData(final String child, final TextView field){
        final String mail = user.getEmail().replace("."," ");
        try {
            DatabaseReference profileRef = myRef.child(mail).child("Public").child(child);
            dataSet = false;
            profileRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String data = dataSnapshot.getValue(String.class);
                    if(data != null) {
                        field.setText(data);
                    } else {
                        DatabaseReference profilePrivateRef = myRef.child(mail).child("Private").child(child);
                        profilePrivateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String data = dataSnapshot.getValue(String.class);
                                field.setText(data);
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
    }

    private void setData(final String child, final Switch field){
        final String mail = user.getEmail().replace("."," ");
        try {
            DatabaseReference profileRef = myRef.child(mail).child("Public").child(child);
            dataSet = false;
            profileRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue() != null) {
                        field.setChecked(dataSnapshot.getValue(boolean.class));
                        Log.e("in public smith", ""+dataSnapshot.getValue(boolean.class));
                        dataSet = true;
                        if(dataSnapshot.getValue(boolean.class)==true){
                            armory.setEnabled(true);
                        }
                    } else {

                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) { }
            });
        }catch (Throwable e) { }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
