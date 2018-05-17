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
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.List;

/**
 * Created by i7-4770 on 18/02/2018.
 */

public class CalenderAdapter extends
        RecyclerView.Adapter<CalenderAdapter.WordViewHolder>  {

    private LayoutInflater dInflater;
    private Context context;
    private SharedPreferences prefs;


    private final List<DayItems> dayList;

    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView calenderDateView, calenderDateInfoView, calenderDayView;

        final CalenderAdapter cAdapter;


        WordViewHolder(View itemView, CalenderAdapter adapter) {
            super(itemView);
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            calenderDateView = itemView.findViewById(R.id.Date);
            calenderDateInfoView = itemView.findViewById(R.id.DateInfo);
            calenderDayView = itemView.findViewById(R.id.Day);
            this.cAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();
            DayItems element = dayList.get(mPosition);
            Bundle bundle = new Bundle();
            bundle.putString("Date",element.getDate());
            bundle.putString("Day",element.getDay());
            bundle.putString("DayInfo",element.getDayInfo());
            bundle.putInt("DayColor",element.getDayColor());
            bundle.putString("Month",element.getMonth());
            final android.app.FragmentManager fm = ((Activity) context).getFragmentManager();
            DateFragment df = new DateFragment();
            df.setArguments(bundle);
            fm.beginTransaction().replace(R.id.content_frame, df).commit();
            //test=element.getDayInfo()+"Clicked!";
            //element.setDayInfo(test);
            //cAdapter.notifyDataSetChanged();
            Log.e("Adapter onClick", "test");
        }
    }
    @Override
    public CalenderAdapter.WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        prefs = parent.getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        View dItemView = dInflater.inflate(R.layout.calender_entries, parent, false);
        TextView day = dItemView.findViewById(R.id.Day);
        TextView date = dItemView.findViewById(R.id.Date);
        TextView dateInfo = dItemView.findViewById(R.id.DateInfo);
        day.setTextColor(getTextColor());
        date.setTextColor(getTextColor());
        dateInfo.setTextColor(getTextColor());
        //String color = getArguments().getString("Color");
        context=parent.getContext();

        return new WordViewHolder(dItemView, this);
    }

    @Override
    public void onBindViewHolder(CalenderAdapter.WordViewHolder holder, int position) {
        final DayItems dayItems = dayList.get(position);
        holder.calenderDateView.setText(dayItems.getDate());
        String[] splitinfo=dayItems.getDayInfo().split("\\{|\\}|\\,");
        String joininfo="test";
        for(int i=0;i<splitinfo.length;i++){
            if(joininfo.equals("test")) {
                joininfo = splitinfo[i];
            }else{
                joininfo = joininfo + splitinfo[i] + "\n";
            }
        }
        joininfo.replace("\""," ");
        holder.calenderDateInfoView.setText(joininfo);
        holder.calenderDayView.setBackgroundColor(dayItems.getDayColor());
        holder.calenderDayView.setText(dayItems.getDay());
        Log.e("Current day",dayItems.getDay());
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    CalenderAdapter(Context context, List<DayItems> dayList) {
        dInflater = LayoutInflater.from(context);
        this.dayList = dayList;

    }
    public int getTextColor(){
        return prefs.getInt("TColor", 0xff000000);
    }
}
