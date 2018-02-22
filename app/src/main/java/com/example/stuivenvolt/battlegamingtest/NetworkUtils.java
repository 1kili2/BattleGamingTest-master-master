package com.example.stuivenvolt.battlegamingtest;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by i7-4770 on 22/02/2018.
 */

public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String CALENDER_BASE_URL =  "https://battle-gaming-agenda.firebaseio.com/agenda/eventos/2018/Marzo"; // Base URI for the Books API
    private static final String YEAR = "/"; // Parameter for the search string
    private static final String MONTH = "/"; // Parameter that limits search results

    static String getCalenderInfo(String queryString){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String calenderJSONString = null;

        try {
            Uri builtURI = Uri.parse(CALENDER_BASE_URL).buildUpon().build();
            URL requestURL = new URL(builtURI.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
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
            calenderJSONString = buffer.toString();
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
            Log.d(LOG_TAG, calenderJSONString);
            return calenderJSONString;
        }
    }
}
