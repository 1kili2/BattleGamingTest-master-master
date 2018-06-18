package com.example.stuivenvolt.battlegamingtest.Guild_Hub;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GuildDataFetcher  extends AsyncTask<Void, Void, String> {

    private TextView motto;
    private String guildName;
    private Context context;
    private ImageView guildLogo;
    private ArrayList<String> comics = new ArrayList<>();
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference myRef;
    String idToken;
    boolean wait = true;



    GuildDataFetcher(TextView guildMotto, Context ctx, ImageView img, String guild){
        motto =guildMotto;
        guildName =guild;
        context = ctx;
        guildLogo = img;

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    protected String doInBackground(Void... urls) {

        wait=true;
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

        try {
            URL url = new URL("https://battle-gaming-agenda.firebaseio.com/gremios.json?auth="+idToken);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
            finally{
                urlConnection.disconnect();
            }
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if(result == null) {
            result = "THERE WAS AN ERROR";
        }
        Log.e("INFO", result);
        int test, test2;
        try {
            JSONArray array = new JSONArray(result);
            test=array.length();
            Log.e("Array Length", "News fetcher: "+test);

            for (int x = 0; x < test ; x++) {
                JSONObject jo = array.getJSONObject(x);
                if(jo.getString("Name").equals(guildName)){
                    motto.setText(jo.getString("Motto"));
                    Log.e("Logo link", ""+jo.getString("Logo"));
                    Picasso.with(context).load(jo.getString("Logo")).into(guildLogo);
                }
            }

        } catch (JSONException je){
            je.printStackTrace();
            Toast toast = Toast.makeText(context, "No hay mas informacion", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
