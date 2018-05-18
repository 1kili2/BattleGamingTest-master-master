package com.example.stuivenvolt.battlegamingtest.Calender;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by i7-4770 on 22/02/2018.
 */

public class CalenderFetcher extends AsyncTask<Void, Void, String> {
    FirebaseAuth mAuth;
    FirebaseUser user;

    private boolean wait = true;
    private String idToken;
    private String month;
    private int monthNum,row1, dayn;
    @SuppressLint("StaticFieldLeak")
    private RecyclerView mRecyclerView;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private DayItems di;
    private String[][] firstday={{"Mon","Tue","Wed","Thu","Fri","Sat","Sun"},{"Lunes","Martes","Miercoles","Jueves","Viernes","Sabado","Domingo"}};


    CalenderFetcher(Integer mntnum, String mnt, String day, RecyclerView rv, Context ctx) {
        dayn=Integer.parseInt(day);
        mRecyclerView=rv;
        context=ctx;
        month=mnt;
        monthNum =mntnum;
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

    }




    private String getFirstDateOfMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, monthNum);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        DateFormat sdf = new SimpleDateFormat("EEEEEEEE");
        return sdf.format(cal.getTime());
    }

    @Override
    protected String doInBackground(Void... voids) {
        user.getIdToken(false)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            idToken = task.getResult().getToken();
                            Log.e("get token", "success");

                            wait = false;
                        } else {
                            // Handle error -> task.getException();
                            Log.e("get token", "fail");
                            wait = false;
                        }
                    }
                });
        while(wait == true){ }
        return NetworkUtils.getCalenderInfo(idToken);
    }

    protected void onPostExecute(String result) {
        List<DayItems> dayList = new ArrayList<>();
        super.onPostExecute(result);
        int test;
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
                String date;
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
                    String dayname = firstday[1][row1];
                    if(jo.getString("Day").equals(date)) {
                        di = new DayItems(jo.getString("Day"), dayname, jo.getString("Entrene"), Color.rgb(255,127,127),month);
                        Log.e("Day name in try",firstday[1][row1]);
                        break;
                    }else{
                        di = new DayItems(date, dayname,"",Color.rgb(255, 203, 99),month);
                        Log.e("Day name in try",firstday[1][row1]);
                    }
                }
                dayList.add(di);
            }
            CalenderAdapter cAdapter = new CalenderAdapter(context, dayList);
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
