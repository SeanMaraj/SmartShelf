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
import android.widget.ListView;

import com.fydp.sean.smartshelf.Adaptors.EventAdaptor;
import com.fydp.sean.smartshelf.Helpers.Utility;
import com.fydp.sean.smartshelf.MainActivity;
import com.fydp.sean.smartshelf.Models.EventModel;
import com.fydp.sean.smartshelf.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sean on 2015-07-12.
 */
public class EventsViewController extends Fragment{

    View rootView = null;
    ListView eventListView;
    EventAdaptor adaptor;
    ArrayList<EventModel> events = new ArrayList<EventModel>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setActionBarTitle("Events");

        rootView = inflater.inflate(R.layout.view_events, container, false);
        eventListView = (ListView) rootView.findViewById(R.id.eventList);
        adaptor = new EventAdaptor(getActivity(), R.layout.event);
        eventListView.setAdapter(adaptor);

        events.clear();
        getData();
        setOnItemClick();
        populateList();


        return rootView;
    }

    private void getData()
    {
        parseResult(Utility.fetchData("getevents/1"));
    }

    private void parseResult(String result)
    {
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

    private void populateList()
    {
        Log.d("Log", "Populating list");
        adaptor.clear();
        adaptor.notifyDataSetChanged();

        for (int i = 0; i < events.size(); i++)
        {
            adaptor.add(events.get(i));
        }
    }

    private void setOnItemClick()
    {
        Log.d("Log", "Setting item click");

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

                ((MainActivity) getActivity()).setActionBarTitle("Edit Event");
            }
        });
    }
}
