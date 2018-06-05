package com.example.stuivenvolt.battlegamingtest.Guild_Hub.Members_List;


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

import com.example.stuivenvolt.battlegamingtest.News.NewsItemFragment;
import com.example.stuivenvolt.battlegamingtest.News.NewsItems;
import com.example.stuivenvolt.battlegamingtest.Profile.ViewProfileFragment;
import com.example.stuivenvolt.battlegamingtest.Profile.Weapons_List.WeaponListFragment;
import com.example.stuivenvolt.battlegamingtest.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static java.lang.Thread.sleep;

public class GuildMembersAdapter extends RecyclerView.Adapter<GuildMembersAdapter.myViewHolder> {
    String participant;
    List<SimpleMembers> listItem;
    Context context;

    public GuildMembersAdapter(List<SimpleMembers> passedListItem, Context ctx) {
        this.listItem = passedListItem;
        context = ctx;
    }

    @Override
    public GuildMembersAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.guild_members, parent, false);

        return new myViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        int itemNumber = position + 1;
        SimpleMembers sm = listItem.get(position);
        Log.e("member Name",sm.getName());
        holder.Name.setText(sm.getName());
        holder.Smith.setVisibility(View.GONE);
        if(sm.getSmith()){
            holder.Smith.setVisibility(View.VISIBLE);
        }
        holder.Leader.setVisibility(View.GONE);
        if(sm.getRank().equals("Leader")){
            holder.Leader.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView Name;
        ImageView Smith, Leader, Info;

        public myViewHolder(View view) {
            super(view);
            Name = view.findViewById(R.id.members);
            Smith = view.findViewById(R.id.Smith);
            Leader = view.findViewById(R.id.Leader);
            Info = view.findViewById(R.id.Info);
            Info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int mPosition = getLayoutPosition();
                    SimpleMembers element = listItem.get(mPosition);
                    Bundle bundle = new Bundle();
                    bundle.putString("Email",element.getEmail());
                    android.app.FragmentManager fm = ((Activity) context).getFragmentManager();
                    ViewProfileFragment vpf = new ViewProfileFragment();
                    vpf.setArguments(bundle);
                    fm.beginTransaction().replace(R.id.content_frame, vpf).commit();
                }
            });

            Smith.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int mPosition = getLayoutPosition();
                    SimpleMembers element = listItem.get(mPosition);
                    Bundle bundle = new Bundle();
                    bundle.putString("Email",element.getEmail());
                    android.app.FragmentManager fm = ((Activity) context).getFragmentManager();
                    WeaponListFragment wlf = new WeaponListFragment();
                    wlf.setArguments(bundle);
                    fm.beginTransaction().replace(R.id.content_frame, wlf).commit();
                }
            });
        }
    }
}

