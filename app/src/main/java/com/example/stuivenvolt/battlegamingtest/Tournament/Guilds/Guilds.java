package com.example.stuivenvolt.battlegamingtest.Tournament.Guilds;

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
import com.example.stuivenvolt.battlegamingtest.Calender.CalenderFragment;
import com.example.stuivenvolt.battlegamingtest.News.NewsFetcher;
import com.example.stuivenvolt.battlegamingtest.R;
import com.example.stuivenvolt.battlegamingtest.Tournament.CreateTournament;
import com.example.stuivenvolt.battlegamingtest.Tournament.Members.Members;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.ArrayList;
import java.util.List;



public class Guilds extends DialogFragment implements AdapterView.OnItemSelectedListener{


    /**
     * Created by i7-4770 on 01/03/2018.
     */

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


    public static com.example.stuivenvolt.battlegamingtest.Tournament.Guilds.Guilds newInstance(String param1, String param2) {
        com.example.stuivenvolt.battlegamingtest.Tournament.Guilds.Guilds fragment = new com.example.stuivenvolt.battlegamingtest.Tournament.Guilds.Guilds();
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
        View view = getActivity().getLayoutInflater().inflate(R.layout.guildlist, null);

        builder.setView(view);
        final AlertDialog alert = builder.create();

        RecyclerView mRecyclerView = view.findViewById(R.id.guild_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        new GuildsFetcher(mRecyclerView, getActivity()).execute();

        Button ok = view.findViewById(R.id.Done);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //whatever you want
                //Log.e("guild Text",guild.getText().toString());
                if(!test){
                    DialogFragment newFragment = new CreateTournament();
                    Bundle bundle = new Bundle();
                    bundle.putInt("Switch_Pos",getArguments().getInt("Switch_Pos"));
                    bundle.putInt("Score",getArguments().getInt("Score"));
                    bundle.putInt("Type",getArguments().getInt("Type"));
                    bundle.putStringArrayList("Participants", getArguments().getStringArrayList("Participants"));
                    bundle.putString("Guild", getArguments().getString("Guild"));
                    newFragment.setArguments(bundle);
                    newFragment.show(getFragmentManager(), "Boom");
                    alert.dismiss();
                }else {

                    final android.app.FragmentManager fm = (getActivity()).getFragmentManager();
                    fm.beginTransaction().replace(R.id.content_frame, new CalenderFragment()).commit();
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

