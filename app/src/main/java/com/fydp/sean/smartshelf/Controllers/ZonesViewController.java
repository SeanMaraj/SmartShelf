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
import com.fydp.sean.smartshelf.Helpers.Utility;
import com.fydp.sean.smartshelf.Models.ZoneModel;
import com.fydp.sean.smartshelf.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    ArrayList<ZoneModel> zones = new ArrayList<ZoneModel>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.view_zones, container, false);
        zoneListView = (ListView) rootView.findViewById(R.id.zoneList);
        addZone = (Button) rootView.findViewById(R.id.addZoneBtn);
        adaptor = new ZoneAdaptor(getActivity(), R.layout.row_zone);
        zoneListView.setAdapter(adaptor);

        zones.clear();
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

        for (int i = 0; i < zones.size(); i++)
        {
            adaptor.add(zones.get(i));
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

                args.putInt("position", position);
                args.putString("itemName", zones.get(position).getName());
                args.putFloat("initialWeight", zones.get(position).getInitialWeight());

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
        parseResult(Utility.fetchData("getzones/1"));
    }

    private void parseResult(String result)
    {
        try
        {
            JSONArray JSONZones = new JSONArray(result);

            for (int i = 0; i < JSONZones.length(); i++)
            {
                JSONObject JSONZone = JSONZones.getJSONObject(i);
                ZoneModel zone = new ZoneModel(JSONZone.getInt("id"), JSONZone.getString("desc"), (float)(JSONZone.getDouble("weight")), (float)(JSONZone.getDouble("initialweight")));
                zones.add(zone);
            }

        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
