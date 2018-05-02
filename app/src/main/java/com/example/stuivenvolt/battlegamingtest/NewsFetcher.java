package com.example.stuivenvolt.battlegamingtest;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by i7-4770 on 25/02/2018.
 */

public class NewsFetcher extends AsyncTask<Void, Void, String> {

    private boolean wait = true;
    private String idToken, stringNewsItems;
    FirebaseAuth mAuth;
    FirebaseUser user;
    TextView mTextView,calenderDateView;
    String dayn,date,month;
    private static final String LOG_TAG = CalenderFetcher.class.getSimpleName();
    private List<NewsItems> newsList;
    private NewsItemsAdapter nAdapter;
    private RecyclerView mRecyclerView;
    private Context context;
    private NewsItems ni;


    public NewsFetcher(RecyclerView rv, Context ctx) {
        mRecyclerView=rv;
        context=ctx;
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

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
        stringNewsItems = NewsConnection.getNewsItemsAuth(idToken);
        return stringNewsItems;
    }

    protected void onPostExecute(String result) {
        Log.e("Empty string",""+result);
        newsList = new ArrayList<>();
        super.onPostExecute(result);
        int test = 0;
        try {
            JSONObject jsonO = new JSONObject(result);
            JSONArray array = jsonO.getJSONArray("noticias");
            test=array.length();
            Log.e("Array Length", "News fetcher: "+test);

            for (int x = 0; x < test ; x++) {
                JSONObject jo = array.getJSONObject(x);
                ni = new NewsItems(jo.getString("Title"),jo.getString("Article"),jo.getString("Image"),jo.getString("Date"));
                newsList.add(ni);
            }


            nAdapter = new NewsItemsAdapter(context, newsList);
            mRecyclerView.setAdapter(nAdapter);


        } catch (JSONException je){
            je.printStackTrace();
            Toast toast = Toast.makeText(context, "No hay mas informacion", Toast.LENGTH_SHORT);
            toast.show();
            // try {
            //     //JSONObject jsonObject = new JSONObject(result);
            //     JSONObject itemsArray = new JSONObject(result);
            //     Log.d(LOG_TAG,result);

            //         String day = ""+itemsArray;
            //         //String day=null;
            //         //JSONObject volumeInfo = book.getJSONObject("sessionInfo");





            //         if (day != null ){
            //             mTextView.setText(mTextView.getText()+" "+day);

            //         }


            //     if(mTextView.getText()==null) {
            //         mTextView.setText(dayn+"No Results Found");
            //     }



            // } catch (Exception ex){
            //     mTextView.setText(dayn+"No Results Found"+test);
            //     ex.printStackTrace();
            // }
        }
    }
}