package com.example.stuivenvolt.battlegamingtest.Tournament;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 * {@link ChampionshipCreatorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChampionshipCreatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChampionshipCreatorFragment extends android.app.Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference myRef;
    private String guild;

    private OnFragmentInteractionListener mListener;

    public ChampionshipCreatorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChampionshipCreatorFragment.
     */
    public static ChampionshipCreatorFragment newInstance(String param1, String param2) {
        ChampionshipCreatorFragment fragment = new ChampionshipCreatorFragment();
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
        myRef = FirebaseDatabase.getInstance().getReference("usuarios");
        setGuild();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_champioship_creator, container, false);


        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerChampionships);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        new TournamentsFetcher(mRecyclerView, getActivity()).execute();

        final FloatingActionButton addbtn = view.findViewById(R.id.addTournament);
        addbtn.setVisibility(View.GONE);
        addbtn.setColorFilter(getTextColor());
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                DialogFragment newFragment = new CreateTournament();
                Bundle bundle = new Bundle();
                bundle.putInt("Switch_Pos",0);
                bundle.putInt("Score",0);
                bundle.putInt("Type",0);
                bundle.putStringArrayList("Participants", null);
                bundle.putString("Guild", guild);
                newFragment.setArguments(bundle);
                newFragment.show(getFragmentManager(), "Boom");
            }
        });
        getTrusted(addbtn);
        return view;
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

    private void setGuild(){
        final String mail = user.getEmail().replace("."," ");
        DatabaseReference profileRef = myRef.child(mail).child("Public").child("Guild");
        profileRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                guild = dataSnapshot.getValue(String.class);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }
    public int getBackgroundColor(){
        Context context = getActivity();
        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return prefs.getInt("BGColor", 0xffffffff);
    }

    public int getTextColor(){
        Context context = getActivity();
        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return prefs.getInt("TColor", 0xff000000);
    }

    private void getTrusted(final FloatingActionButton addbtn) {
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
                        addbtn.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        } catch (Throwable e) {
        }
    }
}
