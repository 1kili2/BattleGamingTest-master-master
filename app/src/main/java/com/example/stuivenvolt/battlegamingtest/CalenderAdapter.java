package com.example.stuivenvolt.battlegamingtest;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by i7-4770 on 18/02/2018.
 */

public class CalenderAdapter extends
        RecyclerView.Adapter<CalenderAdapter.WordViewHolder>  {

    private LayoutInflater dInflater;
    private Context context;
    private TextView day,date,dateInfo;
    private SharedPreferences prefs;
    private String dayn;




    private final List<DayItems> dayList;

    class WordViewHolder extends RecyclerView.ViewHolder {
        public final TextView calenderDateView, calenderDateInfoView, calenderDayView;
        final CalenderAdapter cAdapter;


        public WordViewHolder(View itemView, CalenderAdapter adapter) {
            super(itemView);
            calenderDateView = (TextView) itemView.findViewById(R.id.Date);
            calenderDateInfoView = (TextView) itemView.findViewById(R.id.DateInfo);
            calenderDayView = (TextView) itemView.findViewById(R.id.Day);
            this.cAdapter = adapter;
        }
    }
    @Override
    public CalenderAdapter.WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        prefs = parent.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        View dItemView = dInflater.inflate(R.layout.calender_entries, parent, false);
        day=(TextView) dItemView.findViewById(R.id.Day);
        date=(TextView) dItemView.findViewById(R.id.Date);
        dateInfo=(TextView) dItemView.findViewById(R.id.DateInfo);
        day.setTextColor(getTextColor());
        date.setTextColor(getTextColor());
        dateInfo.setTextColor(getTextColor());

        return new WordViewHolder(dItemView, this);
    }

    @Override
    public void onBindViewHolder(CalenderAdapter.WordViewHolder holder, int position) {
        Log.e("onBindViewHolder", "In onbindviewholder");
        final DayItems dayItems = dayList.get(position);
        holder.calenderDateView.setText(dayItems.getDate());
        holder.calenderDateInfoView.setText(dayItems.getDayInfo());
        holder.calenderDayView.setText(dayItems.getDay());
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    public CalenderAdapter(Context context, List<DayItems> dayList) {
        dInflater = LayoutInflater.from(context);
        this.dayList = dayList;

    }
    public int getTextColor(){
        int tColor = prefs.getInt("TColor", 0xff000000);
        return tColor;
    }
    public void setInfo(String day){
    }
}
