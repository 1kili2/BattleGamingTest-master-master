package com.example.stuivenvolt.battlegamingtest.Guild_Hub.Guild_List;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stuivenvolt.battlegamingtest.Guild_Hub.GuildHubFragment;
import com.example.stuivenvolt.battlegamingtest.Profile.ViewProfileFragment;
import com.example.stuivenvolt.battlegamingtest.R;
import com.example.stuivenvolt.battlegamingtest.StartUp.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class GuildListAdapter extends RecyclerView.Adapter<GuildListAdapter.myViewHolder> {
    String participant, nickName, rank;
    List<String> listItem;
    Context context;
    String mail;
    boolean printed = false, dataSet, isSmith;
    DatabaseReference myRef2;
    FirebaseUser user;
    FirebaseAuth mAuth;

    public GuildListAdapter(List<String> passedListItem, Context ctx, String id) {
        this.listItem = passedListItem;
        context = ctx;
        mail = id;
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        setDataNick();
        setDataSmith();
        setDataRank();
    }

    @Override
    public GuildListAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.guild_list_item, parent, false);

        return new myViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        int itemNumber = position + 1;
        holder.guildName.setText(listItem.get(position));
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView guildName;
        ImageView join;

        public myViewHolder(View view) {
            super(view);
            guildName = view.findViewById(R.id.guilds);
            join = view.findViewById(R.id.join_guild);
            join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int mPosition = getLayoutPosition();

                    Log.e("Guild", "name: "+listItem.get(mPosition));
                    Log.e("Guild", "mail: "+mail);
                    Log.e("Guild", "position: "+mPosition);

                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("usuarios");
                    myRef.child(mail).child("Public").child("Guild").setValue(listItem.get(mPosition));

                    ((MainActivity) context).setGuild();

                    myRef2 = FirebaseDatabase.getInstance().getReference("gremios/"+mPosition+"/Members");
                    myRef2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            GenericTypeIndicator<List<Object>> t = new GenericTypeIndicator<List<Object>>() {
                            };
                            List messages = snapshot.getValue(t);
                            if (!printed) {
                                myRef2.child("" + messages.size()).child("Email").setValue(mail);
                                myRef2.child("" + messages.size()).child("Name").setValue(nickName);
                                myRef2.child("" + messages.size()).child("Rank").setValue(rank);
                                myRef2.child("" + messages.size()).child("IsSmith").setValue(isSmith);
                                printed = true;
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });

                    Bundle bundle = new Bundle();
                    bundle.putString("Guild",listItem.get(mPosition));
                    android.app.FragmentManager fm = ((Activity) context).getFragmentManager();
                    GuildHubFragment ghf = new GuildHubFragment();
                    ghf.setArguments(bundle);
                    fm.beginTransaction().replace(R.id.content_frame, ghf).commit();
                }
            });
        }
    }

    public void setDataNick(){
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("usuarios");
        final String mail = user.getEmail().replace("."," ");
        final String[] test = new String[1];


        DatabaseReference profileRef = myRef.child(mail).child("Public").child("NickName");
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
    }
    public void setDataSmith(){
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("usuarios");
        final String mail = user.getEmail().replace("."," ");
        final String[] test = new String[1];


        DatabaseReference profileRef = myRef.child(mail).child("Public").child("IsSmith");
        dataSet = false;
        profileRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean data = dataSnapshot.getValue(Boolean.class);
                if(data != null) {
                    isSmith = data;

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }
    public void setDataRank(){
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("usuarios");
        final String mail = user.getEmail().replace("."," ");
        final String[] test = new String[1];


        DatabaseReference profileRef = myRef.child(mail).child("Public").child("Rank");
        dataSet = false;
        profileRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue(String.class);
                if(data != null) {
                    rank = data;

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }
}

