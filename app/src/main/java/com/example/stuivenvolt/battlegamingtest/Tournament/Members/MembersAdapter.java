package com.example.stuivenvolt.battlegamingtest.Tournament.Members;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stuivenvolt.battlegamingtest.R;

import java.util.List;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.myViewHolder> {
    String participant;
    List<String> listItem;

    public MembersAdapter(List<String> passedListItem) {
        this.listItem = passedListItem;
    }

    @Override
    public MembersAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.guild_members, parent, false);
        MembersAdapter.myViewHolder holder = new MembersAdapter.myViewHolder(itemView);
        return holder;
    }


    @Override
    public void onBindViewHolder(MembersAdapter.myViewHolder holder, int position) {
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
            itemTextView = view.findViewById(R.id.members);
        }
    }
}

