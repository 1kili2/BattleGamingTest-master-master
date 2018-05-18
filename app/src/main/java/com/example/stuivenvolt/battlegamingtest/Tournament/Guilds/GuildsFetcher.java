package com.example.stuivenvolt.battlegamingtest.Tournament.Guilds;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.stuivenvolt.battlegamingtest.News.NewsConnection;
import com.example.stuivenvolt.battlegamingtest.News.NewsItems;
import com.example.stuivenvolt.battlegamingtest.News.NewsItemsAdapter;
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

public class GuildsFetcher extends AsyncTask<Void, Void, String> {

    private boolean wait = true;
    private String idToken;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String date,month;
    @SuppressLint("StaticFieldLeak")
    private RecyclerView mRecyclerView;
    @SuppressLint("StaticFieldLeak")
    private Context context;


    GuildsFetcher(RecyclerView rv, Context ctx) {
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
        return GuildsConnection.getGuildsAuth(idToken);
    }

    protected void onPostExecute(String result) {
        Log.e("Empty string",""+result);
        List<String> guildList = new ArrayList<>();
        super.onPostExecute(result);
        int test;
        try {
            JSONObject jsonO = new JSONObject(result);
            JSONArray array = jsonO.getJSONArray("gremios");
            test=array.length();
            Log.e("Array Length", "News fetcher: "+test);

            for (int x = 0; x < test ; x++) {
                JSONObject jo = array.getJSONObject(x);
                guildList.add(jo.getString("Name"));
            }


            GuildsAdapter nAdapter = new GuildsAdapter(guildList);
            mRecyclerView.setAdapter(nAdapter);


        } catch (JSONException je){
            je.printStackTrace();
            Toast toast = Toast.makeText(context, "Error", Toast.LENGTH_SHORT);
            toast.show();

        }
    }
}
