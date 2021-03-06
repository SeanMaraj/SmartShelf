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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fydp.sean.smartshelf.Adaptors.ReminderListAdaptor;
import com.fydp.sean.smartshelf.Adaptors.ZoneAdaptor;
import com.fydp.sean.smartshelf.Helpers.Utility;
import com.fydp.sean.smartshelf.MainActivity;
import com.fydp.sean.smartshelf.Models.ReminderModel;
import com.fydp.sean.smartshelf.Models.ZoneModel;
import com.fydp.sean.smartshelf.Helpers.OfflineData;
import com.fydp.sean.smartshelf.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Sean on 2015-07-12.
 */
public class SummaryViewController extends Fragment{

    View rootView = null;
    ListView zoneListView;
    ZoneAdaptor zoneAdaptor;
    ArrayList<ZoneModel> zones = new ArrayList<ZoneModel>();
    ListView reminderListView;
    ReminderListAdaptor reminderAdaptor;
    ArrayList<ReminderModel> reminders = new ArrayList<ReminderModel>();
    ListView weatherListView;
    List<String> weatherValues = new Vector<String>();
    ImageView weatherImg;
    TextView tempText;
    TextView forecastText;
    List<Integer> activeWeatherNotifs = new ArrayList<Integer>();

    int listItemNumber;
    SelectedList selectedList;

    public enum SelectedList {
        ZONES,
        REMINDERS,
        WEATHER
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d("Log", "IN SUMMARY VIEW CONTROLLER");

        ((MainActivity) getActivity()).setActionBarTitle("Summary");
        Utility.setCurrentFragment("summaryFragment");
        rootView = inflater.inflate(R.layout.view_summary, container, false);

        // Inital setup
        setup(); //TODO: Move this!

        // Setup zones list
        zoneListView = (ListView) rootView.findViewById(R.id.lowStockList);
        zoneAdaptor = new ZoneAdaptor(getActivity(), R.layout.subview_zone);
        zoneListView.setAdapter(zoneAdaptor);
        zones.clear();

        // Reminders
        reminderListView = (ListView) rootView.findViewById(R.id.eventsSummaryList);
        reminderAdaptor = new ReminderListAdaptor(getActivity(), R.layout.subview_reminder);
        reminderListView.setAdapter(reminderAdaptor);
        reminders.clear();

        // Weather
        weatherListView = (ListView) rootView.findViewById(R.id.weatherListView);
        weatherImg = (ImageView)rootView.findViewById(R.id.weatherImg);
        tempText = (TextView)rootView.findViewById(R.id.tempText);
        forecastText = (TextView)rootView.findViewById(R.id.forecastText);


        // Register for context menus
        registerForContextMenu(zoneListView);
        registerForContextMenu(weatherListView);

        getData();
        setOnItemClick();
        setLongClick();
        populateZonesList();
        populateRemindersList();
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
        parseZonesResult(Utility.fetchData("getlowstock"));
        parseRemindersResult(Utility.fetchData("getupcomingreminders"));
        parseWeatherNotifsResult(Utility.fetchData("getactiveweather"));
        parseWeatherResult(Utility.fetchData("getcurrentweather"));
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
                ZoneModel zone = new ZoneModel(JSONZone.getInt("zoneid"), JSONZone.getString("message"), (float)(JSONZone.getDouble("weight")), (float)(JSONZone.getDouble("initialweight")), JSONZone.getInt("activenotificationid"), JSONZone.getInt("baseid"), JSONZone.getString("description"), true);
                zones.add(zone);
            }

        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void parseRemindersResult(String result)
    {
        Log.d("LOG", "Parsing reminders result: " + result);
        try
        {
            JSONArray JSONEvents = new JSONArray(result);

            for (int i = 0; i < JSONEvents.length(); i++)
            {
                JSONObject JSONEvent = JSONEvents.getJSONObject(i);
                String date = Utility.getDate(JSONEvent.getString("date"));
                ReminderModel event = new ReminderModel(0, 0, JSONEvent.getInt("zoneid"), 0, date, JSONEvent.getString("time"), JSONEvent.getString("description"), JSONEvent.getInt(("isactive")));
                reminders.add(event);
            }

        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void parseWeatherNotifsResult(String result)
    {
        Log.d("LOG", "Parsing weather notifications result: " + result);

        try
        {
            JSONArray JSONArray = new JSONArray(result);
            weatherValues.clear();

            for (int i = 0; i < JSONArray.length(); i++)
            {
                JSONObject o = JSONArray.getJSONObject(i);
                activeWeatherNotifs.add(o.getInt("id"));
                weatherValues.add(o.getString("message"));
            }

        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void parseWeatherResult(String result)
    {
        Log.d("LOG", "Parsing current weather result: " + result);

        try
        {
            JSONArray a = new JSONArray(result);
            JSONObject o = a.getJSONObject(0);
            setCurrentWeather(o.getString("status"), o.getString("temperature"));

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

    private void populateRemindersList()
    {
        Log.d("Log", "Populating list");
        reminderAdaptor.clear();
        reminderAdaptor.notifyDataSetChanged();

        for (int i = 0; i < reminders.size(); i++)
        {
            reminderAdaptor.add(reminders.get(i));
        }
    }

    private void populateWeatherList()
    {
        weatherListView.setAdapter(null);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.subview_notif_weather, R.id.weatherNotifText, weatherValues);

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
                Log.d("LOG", "Zone clicked: " + position);
                Utility.setCurrentFragment("zoneDetailFragment");

                Fragment fragment = new ZoneDetailController();
                Bundle args = new Bundle();
                args.putString("zoneName", zones.get(position).getDesc());
                args.putInt("zoneId", zones.get(position).getZoneId());
                args.putInt("baseId", zones.get(position).getBaseId());
                args.putFloat("initWeight", zones.get(position).getInitialWeight());
                args.putFloat("currentWeight", zones.get(position).getCurrentWeight());
                fragment.setArguments(args);


                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, "zoneDetailFragment")
                        .addToBackStack(null)
                        .commit();


            }
        });


        weatherListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                listItemNumber = position;
                selectedList = SelectedList.WEATHER;
                weatherListView.showContextMenu();
            }
        });

        reminderListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Log.d("LOG", "Reminder clicked: " + position);
                Utility.setCurrentFragment("remindersFragment");

                Fragment fragment = new EventsViewController();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, "remindersFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

    private void setLongClick()
    {
        zoneListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                listItemNumber = position;
                selectedList = SelectedList.ZONES;
                return false;
            }
        });


    }

    private void setCurrentWeather(String status, String temp)
    {
        switch (status)
        {
            case "clear":
                weatherImg.setImageResource(R.drawable.sunny);
                forecastText.setText("Clear");
                break;
            case "clouds":
                weatherImg.setImageResource(R.drawable.cloudy);
                forecastText.setText("Cloudy");
                break;
            case "sun":
                weatherImg.setImageResource(R.drawable.sunny);
                forecastText.setText("Sunny");
                break;
            case "rain":
                weatherImg.setImageResource(R.drawable.rainy);
                forecastText.setText("Rainy");
                break;
            case "fog":
                weatherImg.setImageResource(R.drawable.fog);
                forecastText.setText("Foggy");
                break;
            case "snow":
                weatherImg.setImageResource(R.drawable.snow);
                forecastText.setText("Snowy");
                break;
            default:
                weatherImg.setImageResource(R.drawable.sunny);
                forecastText.setText("Clear");
        }

        ;
        tempText.setText(Math.round(Double.parseDouble(temp)) + "°C");
    }


    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        menu.setHeaderTitle("Options");
        menu.add(Menu.NONE, 0, menu.NONE, "Dismiss Notification");
    }

    public boolean onContextItemSelected(MenuItem item)
    {
        if (selectedList == SelectedList.ZONES)
        {
            switch (item.getItemId()) {
                case 0:
                    Log.d("LOG", "Dismiss Notification");
                    dismissZoneNotification(listItemNumber);
                    break;
                case 1:
                    Log.d("LOG", "Case 1");
                    break;
            }
        }

        else if (selectedList == SelectedList.WEATHER)
        {
            switch (item.getItemId()) {
                case 0:
                    Log.d("LOG", "Dismiss Notification");
                    dismissWeatherNotification(listItemNumber);
                    break;
                case 1:
                    Log.d("LOG", "Case 1");
                    break;
            }
        }



        return super.onContextItemSelected(item);
    }

    private void dismissZoneNotification(int n)
    {

        int activeNotifId = zones.get(n).getActiveNotifId();
        Utility.sendData("deleteactivenotification/" + activeNotifId);

        zones.remove(n);
        zoneAdaptor = new ZoneAdaptor(getActivity(), R.layout.subview_zone);
        zoneListView.setAdapter(zoneAdaptor);
        populateZonesList();

        Toast.makeText(getActivity(), "Notification Dismissed", Toast.LENGTH_SHORT).show();
    }

    private void dismissWeatherNotification(int n)
    {

        int activeNotifId = activeWeatherNotifs.get(n);
        Log.d("LOG", "" + activeNotifId);
        Utility.sendData("deleteactivenotification/" + activeNotifId);

        weatherValues.remove(n);
        populateWeatherList();

        Toast.makeText(getActivity(), "Notification Dismissed", Toast.LENGTH_SHORT).show();
    }
}