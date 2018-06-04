package com.example.stuivenvolt.battlegamingtest.Profile.Members_List;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stuivenvolt.battlegamingtest.R;

import java.util.List;

public class WeaponListAdapter extends RecyclerView.Adapter<WeaponListAdapter.myViewHolder> {
    String participant;
    List<String> listItem;

    public WeaponListAdapter(List<String> passedListItem) {
        this.listItem = passedListItem;
    }

    @Override
    public WeaponListAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.guild_members, parent, false);

        return new myViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        int itemNumber = position + 1;
        Log.e("textview Name","The ID is: "+holder.itemTextView.getId());
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
            itemTextView = view.findViewById(R.id.members);
        }
    }
}

