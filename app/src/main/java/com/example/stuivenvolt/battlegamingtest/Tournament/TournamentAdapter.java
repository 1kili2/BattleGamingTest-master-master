package com.example.stuivenvolt.battlegamingtest.Tournament;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stuivenvolt.battlegamingtest.Profile.Weapons_List.WeaponListFragment;
import com.example.stuivenvolt.battlegamingtest.R;

import java.util.List;

public class TournamentAdapter extends RecyclerView.Adapter<TournamentAdapter.myViewHolder> {
    String participant;
    List<String> listItem;
    Context context;

    public TournamentAdapter(List<String> passedListItem, Context ctx) {
        this.listItem = passedListItem;
        context = ctx;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tournament_item, parent, false);

        myViewHolder holder = new myViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        int itemNumber = position + 1;
        holder.itemTextView.setText("- " + listItem.get(position));
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView itemTextView;
        ConstraintLayout cl;

        public myViewHolder(View view) {
            super(view);
            itemTextView = view.findViewById(R.id.TournamentName);
            cl = view.findViewById(R.id.TournamentNameLayout);

            cl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int mPosition = getLayoutPosition();

                    Bundle bundle = new Bundle();
                    bundle.putString("Email","");
                    android.app.FragmentManager fm = ((Activity) context).getFragmentManager();
                    WeaponListFragment wlf = new WeaponListFragment();
                    wlf.setArguments(bundle);
                    fm.beginTransaction().replace(R.id.content_frame, wlf).commit();
                }
            });
        }
    }
}