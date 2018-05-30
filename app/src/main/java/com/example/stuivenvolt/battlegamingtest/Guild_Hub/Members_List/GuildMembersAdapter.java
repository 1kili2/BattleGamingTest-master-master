package com.example.stuivenvolt.battlegamingtest.Guild_Hub.Members_List;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stuivenvolt.battlegamingtest.R;

import java.util.List;

public class GuildMembersAdapter extends RecyclerView.Adapter<GuildMembersAdapter.myViewHolder> {
    String participant;
    List<String> listItem;

    public GuildMembersAdapter(List<String> passedListItem) {
        this.listItem = passedListItem;
        Log.e("member adapter", "lets see if he gets here");
    }

    @Override
    public GuildMembersAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tournament_participant, parent, false);
        Log.e("member adapter", "and here?");
        GuildMembersAdapter.myViewHolder holder = new GuildMembersAdapter.myViewHolder(itemView);
        return holder;
    }


    @Override
    public void onBindViewHolder(GuildMembersAdapter.myViewHolder holder, int position) {
        int itemNumber = position + 1;
        Log.e("member Name",listItem.get(position));
        holder.itemTextView.setText(listItem.get(position));
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView itemTextView;

        public myViewHolder(View view) {
            super(view);
            itemTextView = view.findViewById(R.id.guild_members);
        }
    }
}

