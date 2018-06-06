package com.example.stuivenvolt.battlegamingtest.Guild_Hub.Guild_List;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.stuivenvolt.battlegamingtest.Profile.Weapons_List.Weapons;
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

public class GuildFetcher extends AsyncTask<Void, Void, String> {

    private boolean wait = true;
    private String idToken;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String guild, id;
    @SuppressLint("StaticFieldLeak")
    private RecyclerView mRecyclerView;
    @SuppressLint("StaticFieldLeak")
    private Context context;


    GuildFetcher(RecyclerView rv, Context ctx, String mail) {
        mRecyclerView=rv;
        context=ctx;
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        id=mail;

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
        return GuildConnection.getGuildsAuth(idToken, id);
    }

    protected void onPostExecute(String result) {
        Log.e("Empty string",""+result);
        List<String> guildList = new ArrayList<>();
        Weapons weapon;
        super.onPostExecute(result);
        int array1;
        try {
            JSONObject jsonO = new JSONObject(result);
            JSONArray array = jsonO.getJSONArray("gremios");
            array1=array.length();

            for (int x = 0; x < array1 ; x++) {
                JSONObject jo = array.getJSONObject(x);
                guildList.add(jo.getString("Name"));
            }


            GuildListAdapter nAdapter = new GuildListAdapter(guildList);
            mRecyclerView.setAdapter(nAdapter);


        } catch (JSONException je){
            je.printStackTrace();
            Toast toast = Toast.makeText(context, je.toString(), Toast.LENGTH_SHORT);
            Log.e("json error", je.toString());
            toast.show();

        }
    }
}
