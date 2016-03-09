package com.fydp.sean.smartshelf.Helpers;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Sean on 2015-07-21.
 */
public class DataFetcher extends AsyncTask<String, Void, String> {


    @Override
    protected String doInBackground(String... params) {

        BufferedReader reader = null;

        try{
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            return sb.toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}