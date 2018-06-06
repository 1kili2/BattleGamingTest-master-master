package com.example.stuivenvolt.battlegamingtest.Guild_Hub.Guild_List;

import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stuivenvolt.battlegamingtest.Profile.Weapons_List.Add_Weapon.AddWeapon;
import com.example.stuivenvolt.battlegamingtest.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GuildListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GuildListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuildListFragment extends android.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    DatabaseReference myRef;
    FirebaseUser user;
    FirebaseAuth mAuth;
    String mail;
    FloatingActionButton add;

    private OnFragmentInteractionListener mListener;

    public GuildListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GuildListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GuildListFragment newInstance(String param1, String param2) {
        GuildListFragment fragment = new GuildListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_guild_list, container, false);
        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        mail = user.getEmail().replace(".", " ");
        Log.e("Email", "mail: "+mail);

        add = view.findViewById(R.id.addWeapon);
        //add.setVisibility(View.GONE);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                DialogFragment newFragment = new CreateGuild();
                Bundle bundle = new Bundle();
                bundle.putString("ID",mail);
                /*bundle.putInt("Score",0);
                bundle.putInt("Type",0);
                bundle.putStringArrayList("Participants", null);
                bundle.putString("Guild", guild);*/
                newFragment.setArguments(bundle);
                newFragment.show(getFragmentManager(), "Boom");
            }
        });


        new GuildFetcher(mRecyclerView, getActivity(), mail).execute();
        return view;
    }

    /*@Override
    public void onResume() {
        super.onResume();
        if(getArguments().getString("Email").equals(user.getEmail().replace(".", " "))){
            add.setVisibility(View.VISIBLE);
        }
    }*/


    // TODO: Rename method, update argument and hook method into UI event
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
