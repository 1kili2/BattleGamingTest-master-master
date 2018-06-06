package com.example.stuivenvolt.battlegamingtest.Guild_Hub.Guild_List;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stuivenvolt.battlegamingtest.R;

import java.util.List;

public class GuildListAdapter extends RecyclerView.Adapter<GuildListAdapter.myViewHolder> {
    String participant;
    List<String> listItem;

    public GuildListAdapter(List<String> passedListItem) {
        this.listItem = passedListItem;
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
        Log.e("textview Name","The ID is: "+holder.guildName.getId());
        holder.guildName.setText(listItem.get(position));
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView guildName;

        public myViewHolder(View view) {
            super(view);
            guildName = view.findViewById(R.id.guilds);
        }
    }
}

