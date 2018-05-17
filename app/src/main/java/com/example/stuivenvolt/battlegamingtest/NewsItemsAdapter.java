package com.example.stuivenvolt.battlegamingtest;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.Collections;
import java.util.List;

/**
 * Created by i7-4770 on 17/02/2018.
 */

public class NewsItemsAdapter extends
        RecyclerView.Adapter<NewsItemsAdapter.WordViewHolder>  {
    private final List<NewsItems> newsList;
    private LayoutInflater mInflater;
    private SharedPreferences prefs;
    private Context context;

    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView newsItemView;
        final ImageView newsImage;
        final NewsItemsAdapter nAdapter;

        WordViewHolder(View itemView, NewsItemsAdapter adapter) {
            super(itemView);
            newsItemView = itemView.findViewById(R.id.NewsTitle);
            newsImage = itemView.findViewById(R.id.newsFoto);
            this.nAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();
            NewsItems element = newsList.get(mPosition);
            Bundle bundle = new Bundle();
            bundle.putString("Title",element.getNewsTitle());
            bundle.putString("Article",element.getNewsInfo());
            bundle.putString("Image",element.getImage());
            bundle.putString("Date",element.getDate());
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
    @Override
    public NewsItemsAdapter.WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        prefs = parent.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        View mItemView = mInflater.inflate(R.layout.news_items, parent, false);
        TextView newsTitle = mItemView.findViewById(R.id.NewsTitle);
        newsTitle.setTextColor(getTextColor());
        context=parent.getContext();
        return new WordViewHolder(mItemView, this);
    }

    private void getView(int position, View convertView) {
        ImageView view = (ImageView) convertView;
        if (view == null) {
            view = new ImageView(context);
        }
        final NewsItems newsItems = newsList.get(position);
        Picasso.with(context).load(newsItems.getImage()).into(view);
    }

    @Override
    public void onBindViewHolder(NewsItemsAdapter.WordViewHolder holder, int position) {
        final NewsItems newsItems = newsList.get(position);
        holder.newsItemView.setText(newsItems.getNewsTitle());
        Log.e("News Title",newsItems.getNewsTitle());
        getView(position, holder.newsImage);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    NewsItemsAdapter(Context context, List<NewsItems> newsList) {
        mInflater = LayoutInflater.from(context);
        Collections.reverse(this.newsList = newsList);
    }

    public int getTextColor(){
        return prefs.getInt("TColor", 0xff000000);
    }
}