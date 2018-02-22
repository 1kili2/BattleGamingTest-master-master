package com.example.stuivenvolt.battlegamingtest;

import android.os.AsyncTask;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by i7-4770 on 22/02/2018.
 */

public class CalenderFetcher extends AsyncTask<Void, Void, String> {

    TextView mTextView;

    public CalenderFetcher(TextView tv) {
        mTextView = tv;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return NetworkUtils.getCalenderInfo("Marzo");
    }

    protected void onPostExecute(String result) {
        mTextView.setText(result);
    }
}
