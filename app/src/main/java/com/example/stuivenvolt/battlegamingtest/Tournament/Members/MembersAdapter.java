package com.example.stuivenvolt.battlegamingtest.Tournament.Members;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.stuivenvolt.battlegamingtest.R;

import java.util.ArrayList;
import java.util.List;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.myViewHolder> {
    String participant;
    List<String> listItem;
    ArrayList<String> bundle;
    List<String> emailList;
    ArrayList<String> mails;

    public MembersAdapter(List<String> passedListItem, ArrayList<String> bndl, List<String> passedEmails, ArrayList<String> list) {
        this.listItem = passedListItem;
        bundle = bndl;
        emailList = passedEmails;
        mails=list;

    }

    @Override
    public MembersAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tournament_participant, parent, false);
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

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView itemTextView;
        RelativeLayout layout;

        public myViewHolder(View view) {
            super(view);
            itemTextView = view.findViewById(R.id.participant);
            itemTextView.setOnClickListener(this);
            layout = view.findViewById(R.id.participant_container);
        }

        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();
            String element = listItem.get(mPosition);
            bundle.add(element);
            mails.add(emailList.get(mPosition));
            Log.e("Adapter onClick", "test "+ element);
            layout.setBackgroundColor(Color.parseColor("#ff9400"));
            itemTextView.setOnClickListener(null);
        }
    }
}

