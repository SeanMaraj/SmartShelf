package com.fydp.sean.smartshelf.Controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.fydp.sean.smartshelf.Adaptors.ReminderListAdaptor;
import com.fydp.sean.smartshelf.Helpers.Utility;
import com.fydp.sean.smartshelf.MainActivity;
import com.fydp.sean.smartshelf.Models.ReminderModel;
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
    ReminderListAdaptor adaptor;
    ArrayList<ReminderModel> events = new ArrayList<ReminderModel>();

    int listItemNumber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setActionBarTitle("Reminders");
        Utility.setCurrentFragment("remindersFragment");

        rootView = inflater.inflate(R.layout.view_events, container, false);
        eventListView = (ListView) rootView.findViewById(R.id.eventList);
        adaptor = new ReminderListAdaptor(getActivity(), R.layout.subview_reminder);
        eventListView.setAdapter(adaptor);

        // Register for context menus
        registerForContextMenu(eventListView);

        events.clear();
        getData();
        setOnItemClick();
        populateList();


        return rootView;
    }

    private void getData()
    {
        parseResult(Utility.fetchData("getallreminders"));
    }

    private void parseResult(String result)
    {
        try
        {
            JSONArray JSONEvents = new JSONArray(result);

            for (int i = 0; i < JSONEvents.length(); i++)
            {
                JSONObject JSONEvent = JSONEvents.getJSONObject(i);
                String date = Utility.getDate(JSONEvent.getString("date"));
                ReminderModel event = new ReminderModel(0, JSONEvent.getInt("notificationid"), JSONEvent.getInt("zoneid"), JSONEvent.getInt("baseid"), date, JSONEvent.getString("time"), JSONEvent.getString("description"), 0);
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
        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                listItemNumber = position;
                eventListView.showContextMenu();
            }
        });
    }

    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        menu.setHeaderTitle("Options");
        menu.add(Menu.NONE, 0, menu.NONE, "Delete Reminder");
    }

    public boolean onContextItemSelected(MenuItem item)
    {

        switch (item.getItemId()) {
            case 0:
                Log.d("LOG", "Dismiss Notification");
                deleteReminder(listItemNumber);
                break;
        }


        return super.onContextItemSelected(item);
    }

    private void deleteReminder(int n)
    {
        int notifId = events.get(n).getNotifId();
        events.remove(n);
        adaptor = new ReminderListAdaptor(getActivity(), R.layout.subview_reminder);
        eventListView.setAdapter(adaptor);

        Utility.sendData("deletenotification/" + notifId);
        populateList();
        Toast.makeText(getActivity(), "Reminder Deleted!", Toast.LENGTH_SHORT).show();
    }
}
