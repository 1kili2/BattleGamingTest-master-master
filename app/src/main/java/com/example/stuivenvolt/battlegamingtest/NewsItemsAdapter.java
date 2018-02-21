package com.example.stuivenvolt.battlegamingtest;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by i7-4770 on 17/02/2018.
 */

public class NewsItemsAdapter extends
        RecyclerView.Adapter<NewsItemsAdapter.WordViewHolder>  {
    private final LinkedList<String> mWordList;
    private LayoutInflater mInflater;
    private SharedPreferences prefs;
    private TextView newsTitle;

    class WordViewHolder extends RecyclerView.ViewHolder {
        public final TextView newsItemView;
        final NewsItemsAdapter nAdapter;

        public WordViewHolder(View itemView, NewsItemsAdapter adapter) {
            super(itemView);
            newsItemView = (TextView) itemView.findViewById(R.id.NewsTitle);
            this.nAdapter = adapter;
        }
    }
    @Override
    public NewsItemsAdapter.WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        prefs = parent.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        View mItemView = mInflater.inflate(R.layout.news_items, parent, false);
        newsTitle=(TextView) mItemView.findViewById(R.id.NewsTitle);
        newsTitle.setTextColor(getTextColor());
        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(NewsItemsAdapter.WordViewHolder holder, int position) {
        String mCurrent = mWordList.get(position);
        holder.newsItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }
    public NewsItemsAdapter(Context context, LinkedList<String> wordList) {
        mInflater = LayoutInflater.from(context);
        this.mWordList = wordList;
    }

    public int getTextColor(){
        int tColor = prefs.getInt("TColor", 0xff000000);
        return tColor;
    }
}