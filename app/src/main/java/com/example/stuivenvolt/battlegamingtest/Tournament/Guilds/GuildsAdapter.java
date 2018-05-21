package com.example.stuivenvolt.battlegamingtest.Tournament.Guilds;




import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.stuivenvolt.battlegamingtest.News.NewsItemFragment;
import com.example.stuivenvolt.battlegamingtest.News.NewsItems;
import com.example.stuivenvolt.battlegamingtest.News.NewsItemsAdapter;
import com.example.stuivenvolt.battlegamingtest.R;

import java.util.List;

public class GuildsAdapter extends RecyclerView.Adapter<com.example.stuivenvolt.battlegamingtest.Tournament.Guilds.GuildsAdapter.myViewHolder> {
    String participant;
    List<String> listItem;
    private Context context;

    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView GuildName;
        final GuildsAdapter nAdapter;

        WordViewHolder(View itemView, GuildsAdapter adapter) {
            super(itemView);
            GuildName = itemView.findViewById(R.id.participant);
            this.nAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();
            String element = listItem.get(mPosition);
            Bundle bundle = new Bundle();
            bundle.putString("Guild",element);
            final android.app.FragmentManager fm = ((Activity) context).getFragmentManager();
            NewsItemFragment nif = new NewsItemFragment();
            nif.setArguments(bundle);
            fm.beginTransaction().replace(R.id.content_frame, nif).commit();
            //test=element.getDayInfo()+"Clicked!";
            //element.setDayInfo(test);
            //cAdapter.notifyDataSetChanged();
            Log.e("Adapter onClick", "test");
        }
    }

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

