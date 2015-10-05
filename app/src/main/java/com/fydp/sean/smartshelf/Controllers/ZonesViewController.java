package com.fydp.sean.smartshelf.Controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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

    boolean offline = true;

    View rootView = null;
    ListView zoneListView;
    Button addZone;
    ZoneAdaptor adaptor;

    ArrayList<Integer> zoneNumbers = new ArrayList<Integer>();
    ArrayList<String> itemNames = new ArrayList<String>();
    ArrayList<Float> initialWeights = new ArrayList<Float>();
    ArrayList<Float> currentWeights = new ArrayList<Float>();

    //FAKE DATA
    int[] zoneNumberss = {1,2,3,4};
    String[] itemNamess = {"Sugar", "Flour", "Bolts", "Nuts"};
    int[] percentagess = {25,8,80,50};
    int[] initialWeightss = {100, 200, 62, 400};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.view_zones, container, false);
        zoneListView = (ListView)rootView.findViewById(R.id.zoneList);
        addZone = (Button)rootView.findViewById(R.id.addZoneBtn);

        //getData();

        adaptor = new ZoneAdaptor(getActivity(), R.layout.row_zone);
        zoneListView.setAdapter(adaptor);

        setOnItemClick();
        populateList();

        return rootView;
    }

    private void populateList() {

        adaptor.clear();
        adaptor.notifyDataSetChanged();

        if (!offline)
        {
            for (int i=0; i<zoneNumberss.length; i++)
            {
                ZoneModel zone = new ZoneModel(zoneNumbers.get(i), itemNames.get(i), currentWeights.get(i), initialWeights.get(i));
                adaptor.add(zone);
            }
        }else{
            for (int i=0; i<zoneNumberss.length; i++)
            {
                ZoneModel zone = new ZoneModel(zoneNumberss[i], itemNamess[i], percentagess[i], initialWeightss[i]); //FAKE ZONE
                adaptor.add(zone);
            }
        }
    }

    private void setOnItemClick() {
        zoneListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Fragment fragment = new ZoneEditController();

                Bundle args = new Bundle();
                if (!offline)
                {
                    args.putInt("position", position);
                    args.putString("itemName", itemNames.get(position));
                    args.putFloat("initialWeight", initialWeights.get(position));
                }else{
                    args.putInt("position", position);
                    args.putString("itemName", itemNamess[position]);
                    args.putFloat("initialWeight", initialWeightss[position]);
                }


                fragment.setArguments(args);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit();

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
        zoneNumbers.clear();
        itemNames.clear();
        currentWeights.clear();
        initialWeights.clear();

        try{
            JSONArray zones = new JSONArray(result);

            for (int i=0; i<zones.length(); i++)
            {
                zoneNumbers.add(zones.getJSONObject(i).getInt("id"));
                itemNames.add(zones.getJSONObject(i).getString("desc"));
                initialWeights.add((float)(zones.getJSONObject(i).getDouble("initialweight")));
                currentWeights.add((float)zones.getJSONObject(i).getDouble("weight"));
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
