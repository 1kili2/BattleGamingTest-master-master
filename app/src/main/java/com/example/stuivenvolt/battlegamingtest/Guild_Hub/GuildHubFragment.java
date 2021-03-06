package com.example.stuivenvolt.battlegamingtest.Guild_Hub;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stuivenvolt.battlegamingtest.Calender.DateFragment;
import com.example.stuivenvolt.battlegamingtest.Guild_Hub.Members_List.GuildMembersFragment;
import com.example.stuivenvolt.battlegamingtest.R;
import com.example.stuivenvolt.battlegamingtest.Tournament.CreateTournament;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GuildHubFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GuildHubFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuildHubFragment extends android.app.Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference myRef;
    private String guild;
    TextView guildName, guildMotto;
    ImageView guildLogo;
    boolean dataSet;




    private OnFragmentInteractionListener mListener;

    public GuildHubFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GuildHubFragment.
     */
    public static GuildHubFragment newInstance(String param1, String param2) {
        GuildHubFragment fragment = new GuildHubFragment();
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
        getActivity().setTitle(getString(R.string.guild_title));
        View view = inflater.inflate(R.layout.fragment_guild_hub, container, false);
        guild = getArguments().getString("Guild");

        guildName = view.findViewById(R.id.guild_name);
        guildMotto = view.findViewById(R.id.guild_motto);
        guildLogo = view.findViewById(R.id.guild_logo);
        Button messageBoard = view.findViewById(R.id.access_Message_Board);
        messageBoard.setEnabled(false);
        Button battles = view.findViewById(R.id.access_Battles);
        battles.setEnabled(false);

        guildName.setText(guild);

        new GuildDataFetcher(guildMotto, getActivity(), guildLogo, guild).execute();



        final Button membersList = view.findViewById(R.id.access_members);
        membersList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final android.app.FragmentManager fm = (getActivity()).getFragmentManager();
                GuildMembersFragment gmf = new GuildMembersFragment();
                Bundle bundle = new Bundle();
                bundle.putString("Guild", guild);
                gmf.setArguments(bundle);
                fm.beginTransaction().replace(R.id.content_frame, gmf).addToBackStack("MembersList").commit();
                getActivity().setTitle(getString(R.string.members));
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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


}
