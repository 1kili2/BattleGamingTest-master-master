package com.example.stuivenvolt.battlegamingtest;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by i7-4770 on 22/02/2018.
 */

public class CalenderFetcher extends AsyncTask<Void, Void, String> {

    TextView mTextView,calenderDateView;
    String dayn,date,month;
    private static final String LOG_TAG = CalenderFetcher.class.getSimpleName();
    private List<DayItems> dayList;
    private CalenderAdapter cAdapter;
    private RecyclerView mRecyclerView;
    private Context context;
    private DayItems di;
    //final ProgressDialog pd=new ProgressDialog(context);


    public CalenderFetcher(String mnt, String day, RecyclerView rv, Context ctx) {
        dayn=day;
        mRecyclerView=rv;
        context=ctx;
        month=mnt;
    }

    @Override
    protected String doInBackground(Void... voids) {
        //pd.setMessage("Loading...");
        //pd.show();
        return NetworkUtils.getCalenderInfo(month,dayn);
    }

    protected void onPostExecute(String result) {
        dayList = new ArrayList<>();
        super.onPostExecute(result);
        int test = 0;
       //pd.dismiss();
        try {
            JSONObject jsonO = new JSONObject(result);
            JSONArray array = jsonO.getJSONArray(month);
            test=array.length();
            Log.e(LOG_TAG,result);
            Log.e("Month", month);
            Log.e("Array Length", ""+test);
            for (int i = 0; i < 28; i++) {
                if (i < 9) {
                    date = "0" + (i + 1);
                } else {
                    date = "" + (i + 1);
                }
                for (int x = 0; x < array.length(); x++) {
                    JSONObject jo = array.getJSONObject(x);
                    if(jo.getString("Day").equals(date)) {
                        di = new DayItems(jo.getString("Day"),"Sabado",jo.getString("Entrene"));
                        break;
                    }else{
                        di = new DayItems(date,"Lunes","");
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
