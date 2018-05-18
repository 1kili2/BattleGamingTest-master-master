package com.example.stuivenvolt.battlegamingtest.Tournament.Guilds;




import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.stuivenvolt.battlegamingtest.R;

import java.util.List;

public class GuildsAdapter extends RecyclerView.Adapter<com.example.stuivenvolt.battlegamingtest.Tournament.Guilds.GuildsAdapter.myViewHolder> {
    String participant;
    List<String> listItem;

    public GuildsAdapter(List<String> passedListItem) {
        this.listItem = passedListItem;
    }

    @Override
    public com.example.stuivenvolt.battlegamingtest.Tournament.Guilds.GuildsAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tournament_participant, parent, false);

        com.example.stuivenvolt.battlegamingtest.Tournament.Guilds.GuildsAdapter.myViewHolder holder = new com.example.stuivenvolt.battlegamingtest.Tournament.Guilds.GuildsAdapter.myViewHolder(itemView);
        return holder;
    }


    @Override
    public void onBindViewHolder(com.example.stuivenvolt.battlegamingtest.Tournament.Guilds.GuildsAdapter.myViewHolder holder, int position) {
        int itemNumber = position + 1;
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
            itemTextView = view.findViewById(R.id.participant);
        }
    }
}

