package com.example.stuivenvolt.battlegamingtest;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by i7-4770 on 25/02/2018.
 */


public class NewsConnection {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String CALENDER_BASE_URL =  "https://battle-gaming-agenda.firebaseio.com"; // Base URI for the Books API
    private static final String AGENDA = "/agenda/eventos";
    private static final String YEAR = "/2018";
    private static final String MONTH = "/Marzo";
    private static final String DAY = "/Days";
    private static final String JSON = ".json"; // Parameter for the search string

    static String getNewsItems(){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String newsJSONString = null;


        try {
            URL requestURL = new URL("https://battle-gaming-agenda.firebaseio.com/.json");

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            Log.e("inputstream","News connection established");
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
   /* Since it's JSON, adding a newline isn't necessary (it won't affect
      parsing) but it does make debugging a *lot* easier if you print out the
      completed buffer for debugging. */
                buffer.append(line + "\n");

            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            newsJSONString = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return newsJSONString;
        }
    }
}