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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fydp.sean.smartshelf.Adaptors.EventAdaptor;
import com.fydp.sean.smartshelf.Adaptors.ZoneAdaptor;
import com.fydp.sean.smartshelf.Helpers.Utility;
import com.fydp.sean.smartshelf.MainActivity;
import com.fydp.sean.smartshelf.Models.EventModel;
import com.fydp.sean.smartshelf.Models.ZoneModel;
import com.fydp.sean.smartshelf.Helpers.OfflineData;
import com.fydp.sean.smartshelf.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sean on 2015-07-12.
 */
public class SummaryViewController extends Fragment{

    View rootView = null;
    ListView zoneListView;
    ZoneAdaptor zoneAdaptor;
    ArrayList<ZoneModel> zones = new ArrayList<ZoneModel>();
    ListView eventListView;
    EventAdaptor eventAdaptor;
    ArrayList<EventModel> events = new ArrayList<EventModel>();
    ListView weatherListView;
    String[] weatherValues;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d("Log", "IN SUMMARY VIEW CONTROLLER");

        ((MainActivity) getActivity()).setActionBarTitle("Summary");
        rootView = inflater.inflate(R.layout.view_summary, container, false);

        // Inital setup
        setup();

        // Setup zones list
        zoneListView = (ListView) rootView.findViewById(R.id.lowStockList);
        zoneAdaptor = new ZoneAdaptor(getActivity(), R.layout.subview_zone);
        zoneListView.setAdapter(zoneAdaptor);
        zones.clear();

        // Reminders
        eventListView = (ListView) rootView.findViewById(R.id.eventsSummaryList);
        eventAdaptor = new EventAdaptor(getActivity(), R.layout.subview_reminder);
        eventListView.setAdapter(eventAdaptor);
        events.clear();

        //Weather
        weatherListView = (ListView) rootView.findViewById(R.id.weatherListView);



        getData();
        setOnItemClick();
        populateZonesList();
        populateEventsList();
        populateWeatherList();


        return rootView;
    }

    public void setup()
    {
        if (Utility.offlineMode())
        {
            Log.d("Log", "Creating offline data");
            OfflineData.create();
        }
    }

    public void getData()
    {
        parseZonesResult(Utility.fetchData("getzones/1"));
        parseEventsResult(Utility.fetchData("getevents/1"));
    }

    private void parseZonesResult(String result)
    {
        Log.d("LOG", "Parsing zones result: " + result);

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

    private void parseEventsResult(String result)
    {
        Log.d("LOG", "Parsing events result: " + result);
        try
        {
            JSONArray JSONEvents = new JSONArray(result);

            for (int i = 0; i < JSONEvents.length(); i++)
            {
                JSONObject JSONEvent = JSONEvents.getJSONObject(i);
                EventModel event = new EventModel(JSONEvent.getInt("id"), JSONEvent.getInt("notifId"), JSONEvent.getInt("zoneId"), JSONEvent.getInt("baseId"), JSONEvent.getString("date"), JSONEvent.getString("time"), JSONEvent.getString("desc"), JSONEvent.getBoolean("repeatWeekly"), JSONEvent.getBoolean("repeatMonthly"), JSONEvent.getBoolean("repeatDaily"));
                events.add(event);
            }

        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void populateZonesList()
    {
        Log.d("Log", "Populating list");
        zoneAdaptor.clear();
        zoneAdaptor.notifyDataSetChanged();

        for (int i = 0; i < zones.size(); i++)
        {
            zoneAdaptor.add(zones.get(i));
        }
    }

    private void populateEventsList()
    {
        Log.d("Log", "Populating list");
        eventAdaptor.clear();
        eventAdaptor.notifyDataSetChanged();

        for (int i = 0; i < events.size(); i++)
        {
            eventAdaptor.add(events.get(i));
        }
    }

    private void populateWeatherList()
    {
        weatherValues = new String[] { "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, weatherValues);

        weatherListView.setAdapter(adapter);
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

        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                Fragment fragment = new EventEditController();
                Bundle args = new Bundle();

                //args.putInt("position", position);
                //args.putString("itemName", zones.get(position).getName());
                //args.putFloat("initialWeight", zones.get(position).getInitialWeight());

                fragment.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }
}
