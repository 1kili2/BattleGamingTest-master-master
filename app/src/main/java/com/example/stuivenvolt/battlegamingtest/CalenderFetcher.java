package com.example.stuivenvolt.battlegamingtest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by i7-4770 on 22/02/2018.
 */

public class CalenderFetcher extends AsyncTask<Void, Void, String> {

    TextView mTextView,calenderDateView;
    String date,month,dayname;
    int monthnum,row1, dayn;
    private static final String LOG_TAG = CalenderFetcher.class.getSimpleName();
    private List<DayItems> dayList;
    private CalenderAdapter cAdapter;
    private RecyclerView mRecyclerView;
    private Context context;
    private DayItems di;
    String[][] firstday={{"Mon","Tue","Wed","Thu","Fri","Sat","Sun"},{"Lunes","Martes","Miercoles","Jueves","Viernes","Sabado","Domingo"}},weekday;
    ProgressDialog pd;


    public CalenderFetcher(Integer mntnum, String mnt, String day, RecyclerView rv, Context ctx) {
        dayn=Integer.parseInt(day);
        mRecyclerView=rv;
        context=ctx;
        month=mnt;
        monthnum=mntnum;

    }




    private String getFirstDateOfMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, monthnum);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        DateFormat sdf = new SimpleDateFormat("EEEEEEEE");
        return sdf.format(cal.getTime());
    }

    @Override
    protected String doInBackground(Void... voids) {

        return NetworkUtils.getCalenderInfo();
    }

    protected void onPostExecute(String result) {
        dayList = new ArrayList<>();
        super.onPostExecute(result);
        int test = 0;
        try {
            JSONObject jsonO = new JSONObject(result);
            JSONArray array = jsonO.getJSONArray(month);
            test=array.length();
            Log.e("First day of month", ""+getFirstDateOfMonth());
            for(int i=0;i<7;i++){
                Log.e("Day Array test", firstday[0][i]);
                if(firstday[0][i].equals(getFirstDateOfMonth())){
                    row1=i-1;
                    Log.e("Day Array test result", firstday[1][i]+"      "+row1+"      "+i);
                    break;
                }
            }



            Log.e("Array Length", "Calender fetcher: "+test);
            for (int i = 0; i < dayn; i++) {
                if (i < 9) {
                    date = "0" + (i + 1);
                } else {
                    date = "" + (i + 1);
                }
                row1++;
                if(row1==7){
                    row1=0;
                }
                for (int x = 0; x < array.length(); x++) {
                    JSONObject jo = array.getJSONObject(x);
                    dayname=firstday[1][row1];
                    if(jo.getString("Day").equals(date)) {
                        di = new DayItems(jo.getString("Day"), dayname, jo.getString("Entrene"), Color.rgb(255,127,127));
                        Log.e("Day name in try",firstday[1][row1]);
                        break;
                    }else{
                        di = new DayItems(date,dayname,"",Color.rgb(255, 203, 99));
                        Log.e("Day name in try",firstday[1][row1]);
                    }
                }
                dayList.add(di);
            }
            cAdapter = new CalenderAdapter(context,dayList);
            mRecyclerView.setAdapter(cAdapter);


        } catch (JSONException je){
            je.printStackTrace();
            Toast toast = Toast.makeText(context, "No hay mas informacion", Toast.LENGTH_SHORT);
            toast.show();
           // try {
           //     //JSONObject jsonObject = new JSONObject(result);
           //     JSONObject itemsArray = new JSONObject(result);
           //     Log.d(LOG_TAG,result);
//
           //         String day = ""+itemsArray;
           //         //String day=null;
           //         //JSONObject volumeInfo = book.getJSONObject("sessionInfo");
//
//
//
//
//
           //         if (day != null ){
           //             mTextView.setText(mTextView.getText()+" "+day);
//
           //         }
//
//
           //     if(mTextView.getText()==null) {
           //         mTextView.setText(dayn+"No Results Found");
           //     }
//
//
//
           // } catch (Exception ex){
           //     mTextView.setText(dayn+"No Results Found"+test);
           //     ex.printStackTrace();
           // }
        }
    }
}
