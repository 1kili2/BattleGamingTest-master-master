package com.example.stuivenvolt.battlegamingtest.Tournament.Members;


import android.annotation.SuppressLint;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by i7-4770 on 25/02/2018.
 */

public class MembersFetcher extends AsyncTask<Void, Void, String> {

    private boolean wait = true;
    private String idToken;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String guild;
    ArrayList<String> bundle;
    ArrayList<String> emails;

    @SuppressLint("StaticFieldLeak")
    private RecyclerView mRecyclerView;
    @SuppressLint("StaticFieldLeak")
    private Context context;


    MembersFetcher(String str, RecyclerView rv, Context ctx, ArrayList<String> bndl, ArrayList<String> mails) {
        mRecyclerView=rv;
        context=ctx;
        guild = str;
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        bundle = bndl;
        emails=mails;
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
        return MembersConnection.getGuildsAuth(idToken);
    }

    protected void onPostExecute(String result) {
        Log.e("Empty string",""+result);
        List<String> guildList = new ArrayList<>();
        List<String> emailList = new ArrayList<>();
        super.onPostExecute(result);
        int array1, array2;
        try {
            JSONObject jsonO = new JSONObject(result);
            JSONArray array = jsonO.getJSONArray("gremios");
            array1=array.length();
            Log.e("Array Length", "News fetcher: "+array1);

            for (int x = 0; x < array1 ; x++) {
                JSONObject jo = array.getJSONObject(x);
                if(jo.getString("Name").equals(guild)){
                    JSONArray members = jo.getJSONArray("Members");
                    array2=members.length();
                    for (int y = 0; y < array2 ; y++) {
                        JSONObject jo2 = members.getJSONObject(y);
                        guildList.add(jo2.getString("Name"));
                        emailList.add(jo2.getString("Email"));
                    }
                }
            }


            MembersAdapter nAdapter = new MembersAdapter(guildList, bundle, emailList, emails);
            mRecyclerView.setAdapter(nAdapter);


        } catch (JSONException je){
            je.printStackTrace();
            Toast toast = Toast.makeText(context, "Error", Toast.LENGTH_SHORT);
            toast.show();

        }
    }
}
