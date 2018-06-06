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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class GuildListAdapter extends RecyclerView.Adapter<GuildListAdapter.myViewHolder> {
    String participant;
    List<String> listItem;
    Context context;
    String mail;

    public GuildListAdapter(List<String> passedListItem, Context ctx, String id) {
        this.listItem = passedListItem;
        context = ctx;
        mail = id;
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

                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("usuarios");
                    myRef.child(mail).child("Public").child("Guild").setValue(listItem.get(mPosition));

                    ((MainActivity) context).setGuild();

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
}

