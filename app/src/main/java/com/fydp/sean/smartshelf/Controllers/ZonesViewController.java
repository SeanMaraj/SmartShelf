package com.fydp.sean.smartshelf.Controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.fydp.sean.smartshelf.Adaptors.ZoneAdaptor;
import com.fydp.sean.smartshelf.DataFetcher;
import com.fydp.sean.smartshelf.Libraries.Utility;
import com.fydp.sean.smartshelf.Models.ZoneModel;
import com.fydp.sean.smartshelf.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Sean on 2015-07-12.
 */
public class ZonesViewController extends Fragment
{
    View rootView = null;
    ListView zoneListView;
    Button addZone;
    ZoneAdaptor adaptor;

    ArrayList<Integer> zoneNumbers = new ArrayList<Integer>();
    ArrayList<String> itemNames = new ArrayList<String>();
    ArrayList<Float> initialWeights = new ArrayList<Float>();
    ArrayList<Float> currentWeights = new ArrayList<Float>();

    //FAKE DATA
    int[] zoneNumberss = {1, 2, 3, 4};
    String[] itemNamess = {"Sugar", "Flour", "Bolts", "Nuts"};
    int[] percentagess = {25, 8, 80, 50};
    int[] initialWeightss = {100, 200, 62, 400};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.view_zones, container, false);
        zoneListView = (ListView) rootView.findViewById(R.id.zoneList);
        addZone = (Button) rootView.findViewById(R.id.addZoneBtn);
        adaptor = new ZoneAdaptor(getActivity(), R.layout.row_zone);
        zoneListView.setAdapter(adaptor);

        getData();
        setOnItemClick();
        populateList();

        return rootView;
    }

    private void populateList()
    {
        Log.d("Log", "Populating list");
        adaptor.clear();
        adaptor.notifyDataSetChanged();

        if (!Utility.offlineMode())
        {
            for (int i = 0; i < zoneNumberss.length; i++)
            {
                ZoneModel zone = new ZoneModel(zoneNumbers.get(i), itemNames.get(i), currentWeights.get(i), initialWeights.get(i));
                adaptor.add(zone);
            }
        } else
        {
            for (int i = 0; i < zoneNumberss.length; i++)
            {
                ZoneModel zone = new ZoneModel(zoneNumberss[i], itemNamess[i], percentagess[i], initialWeightss[i]); //FAKE ZONE
                adaptor.add(zone);
            }
        }
    }

    private void setOnItemClick()
    {
        Log.d("Log", "Setting item click");

        zoneListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                Fragment fragment = new ZoneEditController();

                Bundle args = new Bundle();
                if (!Utility.offlineMode())
                {
                    args.putInt("position", position);
                    args.putString("itemName", itemNames.get(position));
                    args.putFloat("initialWeight", initialWeights.get(position));
                } else
                {
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

    private void getData()
    {
        Utility.fetchData("getzones/1");
        /*
        if (!Utility.offlineMode())
        {
            Log.d("Log", "Getting data");
            String url = "http://99.235.222.196:5001//getzones/1";
            String result = "";
            DataFetcher df = new DataFetcher();

            try
            {
                result = df.execute(url).get();
            } catch (Exception e)
            {
                e.printStackTrace();
                Log.d("Error", "Error fetching data");
            }

            parseResult(result);
        } else
        {
            Log.d("Log", "Cannot fetch data since offline");
        }
        */
    }

    private void parseResult(String result)
    {
        zoneNumbers.clear();
        itemNames.clear();
        currentWeights.clear();
        initialWeights.clear();

        try
        {
            JSONArray zones = new JSONArray(result);

            for (int i = 0; i < zones.length(); i++)
            {
                zoneNumbers.add(zones.getJSONObject(i).getInt("id"));
                itemNames.add(zones.getJSONObject(i).getString("desc"));
                initialWeights.add((float) (zones.getJSONObject(i).getDouble("initialweight")));
                currentWeights.add((float) zones.getJSONObject(i).getDouble("weight"));
            }

        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
