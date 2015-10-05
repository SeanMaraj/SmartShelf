package com.fydp.sean.smartshelf.Controllers;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Sean on 2015-07-25.
 */
public class DataSender extends AsyncTask<String, Void, Void> {


    @Override
    protected Void doInBackground(String... params) {

        try{
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            return null;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
