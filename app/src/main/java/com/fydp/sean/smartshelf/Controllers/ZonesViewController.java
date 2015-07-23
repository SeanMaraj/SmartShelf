package com.fydp.sean.smartshelf.Controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fydp.sean.smartshelf.Adaptors.ZoneAdaptor;
import com.fydp.sean.smartshelf.DataFetcher;
import com.fydp.sean.smartshelf.Models.ZoneModel;
import com.fydp.sean.smartshelf.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Sean on 2015-07-12.
 */
public class ZonesViewController extends Fragment {

    View rootView = null;
    ListView zoneListView;
    ZoneAdaptor adaptor;

    ArrayList<Integer> zoneNumbers = new ArrayList<Integer>();
    ArrayList<String> itemNames = new ArrayList<String>();
    ArrayList<Integer> percentages = new ArrayList<Integer>();
    ArrayList<Integer> initialWeights = new ArrayList<Integer>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.view_zones, container, false);
        zoneListView = (ListView)rootView.findViewById(R.id.zoneList);

/*
        int[] zoneNumberss = {1,2,3,4};
        String[] itemNames = {"Sugar", "Flour", "Bolts", "Nuts"};
        int[] percentages = {25,8,80,50};
        int[] initialWeights = {100, 200, 62, 400};
*/

        getData();

        adaptor = new ZoneAdaptor(getActivity(), R.layout.row_zone);
        zoneListView.setAdapter(adaptor);
        setOnItemClick();
        populateList();

        return rootView;
    }

    private void populateList() {
        for (int i=0; i<zoneNumbers.size(); i++)
        {
            //ZoneModel zone = new ZoneModel(zoneNumberss[i], itemNames[i], percentages[i], initialWeights[i]);
            ZoneModel zone = new ZoneModel(zoneNumbers.get(i), itemNames.get(i), percentages.get(i), initialWeights.get(i));
            adaptor.add(zone);
        }
    }

    private void setOnItemClick() {
        zoneListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void getData() {
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

        parseResult(result);
    }

    private void parseResult(String result) {
        try{
            JSONArray zones = new JSONArray(result);

            for (int i=0; i<zones.length(); i++)
            {
                int currentWeight = zones.getJSONObject(i).getInt("weight");
                int initialWeight = zones.getJSONObject(i).getInt("initialweight");
                int percentage = getPercentage(currentWeight, initialWeight);

                zoneNumbers.add(zones.getJSONObject(i).getInt("id"));
                itemNames.add(zones.getJSONObject(i).getString("desc"));
                initialWeights.add(currentWeight);
                percentages.add(percentage);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private int getPercentage(int currentWeight, int initialWeight)
    {
        if (initialWeight == 0) { return 0; }

        if (currentWeight >= initialWeight)
        {
            return 100;
        }
        else
        {
            return (int)(((float)currentWeight/(float)initialWeight)*100);
        }
    }

}
