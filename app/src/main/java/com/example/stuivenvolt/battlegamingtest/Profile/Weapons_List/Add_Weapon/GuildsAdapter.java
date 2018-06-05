package com.example.stuivenvolt.battlegamingtest.Profile.Weapons_List.Add_Weapon;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stuivenvolt.battlegamingtest.R;

import java.util.ArrayList;
import java.util.List;

public class GuildsAdapter extends RecyclerView.Adapter<GuildsAdapter.myViewHolder> {
    List<String> listItem;
    ArrayList<String> bundle;


    public GuildsAdapter(List<String> passedListItem, ArrayList<String> bndl) {
        this.listItem = passedListItem;
        bundle = bndl;
    }

    @Override
    public GuildsAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.guild_members, parent, false);

        GuildsAdapter.myViewHolder holder = new GuildsAdapter.myViewHolder(itemView);
        return holder;
    }


    @Override
    public void onBindViewHolder(GuildsAdapter.myViewHolder holder, int position) {
        int itemNumber = position + 1;
        Log.e("Adapter setText", "test");
        holder.itemTextView.setText(listItem.get(position));
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView itemTextView;

        public myViewHolder(View view) {
            super(view);
            itemTextView = view.findViewById(R.id.members);
            itemTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();
            String element = listItem.get(mPosition);
            bundle.add(element);
            Log.e("Adapter onClick", "test "+ element);
            itemTextView.setOnClickListener(null);
        }
    }
}

