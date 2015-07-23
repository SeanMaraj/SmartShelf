package com.fydp.sean.smartshelf.Controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fydp.sean.smartshelf.DataFetcher;
import com.fydp.sean.smartshelf.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Sean on 2015-07-12.
 */
public class WeatherViewController extends Fragment {

    View rootView = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.view_weather, container, false);

        getData();

        return rootView;
    }

    private void getData() {

        TextView view = (TextView)rootView.findViewById(R.id.weatherText);
        //String url = "http://ip.jsontest.com/";
        String url = "http://99.236.1.225:5001/getzones/1";
        String result = "";

        DataFetcher df = new DataFetcher();

        try {
            result = df.execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        //result = "[{\"id\": 1, \"baseid\": 1, \"type\": \"scale\", \"weight\": \"0.000000\", \"units\": \"grams\"}, {\"id\": 2, \"baseid\": 1, \"type\": \"scale\", \"weight\": \"0.000000\", \"units\": \"grams\"}, {\"id\": 3, \"baseid\": 1, \"type\": \"scale\", \"weight\": \"0.132450\", \"units\": \"grams\"}, {\"id\": 4, \"baseid\": 1, \"type\": \"scale\", \"weight\": \"0.000000\", \"units\": \"grams\"}]";

        parseResult(result);

        view.setText(result);
    }


    private void parseResult(String result) {
        try{
            JSONArray zones = new JSONArray(result);
            ArrayList<Integer> ids = new ArrayList<Integer>();

            for (int i=0; i<zones.length(); i++)
            {
                ids.add(zones.getJSONObject(i).getInt("id"));
            }


        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
