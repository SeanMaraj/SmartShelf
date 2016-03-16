package com.fydp.sean.smartshelf.Controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.fydp.sean.smartshelf.Adaptors.ReminderListAdaptor;
import com.fydp.sean.smartshelf.Adaptors.StockNotifListAdaptor;
import com.fydp.sean.smartshelf.Adaptors.WeatherNotifListAdaptor;
import com.fydp.sean.smartshelf.Helpers.Utility;
import com.fydp.sean.smartshelf.MainActivity;
import com.fydp.sean.smartshelf.Models.ReminderModel;
import com.fydp.sean.smartshelf.Models.StockNotifModel;
import com.fydp.sean.smartshelf.Models.WeatherNotifModel;
import com.fydp.sean.smartshelf.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sean on 2016-03-10.
 */
public class ZoneDetailController extends Fragment
{
    View rootView = null;
    Button addNotifBtn;
    EditText zoneNameEdit;
    TextView zoneIdText;
    TextView baseIdText;
    TextView zoneNumberText;
    ListView stockList;
    ListView reminderList;
    ListView weatherList;
    Button saveBtn;

    ArrayList<StockNotifModel> stockNotifs = new ArrayList<StockNotifModel>();
    ArrayList<ReminderModel> reminders = new ArrayList<ReminderModel>();
    ArrayList<WeatherNotifModel> weatherNotifs = new ArrayList<WeatherNotifModel>();
    StockNotifListAdaptor stockNotifAdaptor;
    ReminderListAdaptor reminderAdaptor;
    WeatherNotifListAdaptor weatherNotifAdaptor;

    int zoneId = 1;
    int baseId = 1;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ((MainActivity) getActivity()).setActionBarTitle("Zone Detail");
        rootView = inflater.inflate(R.layout.view_zone_detail, container, false);

        addNotifBtn = (Button)rootView.findViewById(R.id.addNotifBtn);
        zoneNameEdit = (EditText)rootView.findViewById(R.id.zoneNameEdit);
        zoneIdText = (TextView)rootView.findViewById(R.id.zoneNumberText);
        baseIdText = (TextView)rootView.findViewById(R.id.baseNumberText);
        zoneNumberText = (TextView)rootView.findViewById(R.id.zoneNumberText);
        stockList = (ListView)rootView.findViewById(R.id.stockList);
        reminderList = (ListView)rootView.findViewById(R.id.reminderList);
        weatherList = (ListView)rootView.findViewById(R.id.weatherList);
        saveBtn = (Button)rootView.findViewById(R.id.saveBtn);

        //Set zone info
        baseId = Integer.parseInt(getArguments().get("baseId").toString());
        zoneId = Integer.parseInt(getArguments().get("zoneId").toString());
        zoneNameEdit.setText("" + getArguments().get("zoneName"));
        zoneIdText.setText("Zone Number: " + zoneId);
        baseIdText.setText("Base Number: " + baseId);

        // Setup stock notifications list
        stockList = (ListView) rootView.findViewById(R.id.stockList);
        stockNotifAdaptor = new StockNotifListAdaptor(getActivity(), R.layout.subview_notif_stock);
        stockList.setAdapter(stockNotifAdaptor);
        stockNotifs.clear();

        // Setup reminders list
        reminderList = (ListView) rootView.findViewById(R.id.reminderList);
        reminderAdaptor = new ReminderListAdaptor(getActivity(), R.layout.subview_reminder);
        reminderList.setAdapter(reminderAdaptor);
        reminders.clear();

        // Setup reminders list
        weatherList = (ListView) rootView.findViewById(R.id.weatherList);
        weatherNotifAdaptor = new WeatherNotifListAdaptor(getActivity(), R.layout.subview_weather);
        weatherList.setAdapter(weatherNotifAdaptor);
        weatherNotifs.clear();

        getData();
        setOnItemClicks();
        populateLists();

        return rootView;
    }

    private void getData()
    {
        parseStockNotifsResult(Utility.fetchData("getstocknotifications/" + baseId + "/" + zoneId));
        parseRemindersResult(Utility.fetchData("getreminders/" + baseId + "/" + zoneId));
        parseWeatherResult(Utility.fetchData(("getweathernotifications/" + baseId + "/" + zoneId)));
    }

    private void parseStockNotifsResult(String result)
    {
        Log.d("LOG", "Parsing zones result: " + result);

        try
        {
            JSONArray array = new JSONArray(result);

            for (int i = 0; i < array.length(); i++)
            {
                JSONObject o = array.getJSONObject(i);
                String threshold = o.getString("threshold");
                String operator = getOperator(threshold);
                StockNotifModel stockNotif = new StockNotifModel(o.getInt("notificationid"), Float.parseFloat(threshold.substring(1, threshold.length())), (float)(o.getDouble("initialweight")), operator);
                stockNotifs.add(stockNotif);
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
            JSONArray a = new JSONArray(result);

            for (int i = 0; i < a.length(); i++)
            {
                JSONObject o = a.getJSONObject(i);
                String date = Utility.getDate(o.getString("date"));
                ReminderModel event = new ReminderModel(0, o.getInt("notificationid"), zoneId, baseId, date, o.getString("time"), o.getString("description"), 0);
                reminders.add(event);
            }

        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void parseWeatherResult(String result)
    {
        Log.d("LOG", "Parsing weather result: " + result);
        try
        {
            JSONArray a = new JSONArray(result);

            for (int i = 0; i < a.length(); i++)
            {
                JSONObject o = a.getJSONObject(i);
                String weatherType = o.getString("checktype");
                String operator = getOperator(o.getString("checkvalue"));
                String value = getWeatherValue(operator, o.getString("checkvalue"));
                WeatherNotifModel weatherNotif = new WeatherNotifModel(o.getInt("notificationid"), weatherType, operator, value);
                weatherNotifs.add(weatherNotif);
            }

        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void setOnItemClicks()
    {
        addNotifBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Fragment fragment = new AddNotifController();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("Log", "Updating Name");

                String newName = zoneNameEdit.getText().toString();
                Utility.sendData("updatedescription/" + baseId + "/" + zoneId + "/" + newName);
            }
        });

    }

    private void populateLists()
    {
        Log.d("Log", "Populating zone detail lists");
        stockNotifAdaptor.clear();
        stockNotifAdaptor.notifyDataSetChanged();

        for (int i = 0; i < stockNotifs.size(); i++)
        {
            stockNotifAdaptor.add(stockNotifs.get(i));
        }


        Log.d("Log", "Populating reminders list");
        reminderAdaptor.clear();
        reminderAdaptor.notifyDataSetChanged();

        for (int i = 0; i < reminders.size(); i++)
        {
            reminderAdaptor.add(reminders.get(i));
        }


        Log.d("Log", "Populating weather list");
        weatherNotifAdaptor.clear();
        weatherNotifAdaptor.notifyDataSetChanged();

        for (int i = 0; i < weatherNotifs.size(); i++)
        {
            weatherNotifAdaptor.add(weatherNotifs.get(i));
        }
    }

    private String getOperator(String threshold)
    {
        String op = Character.toString(threshold.charAt(0));

        switch (op)
        {
            case "<":
                op = "Falls Below: ";
                break;
            case ">":
                op = "Is Above: ";
                break;
            default:
                op = "Is";
                break;
        }

        return  op;
    }

    private String getWeatherValue(String operator, String value)
    {
        if (operator == "Is")
        {
            return value;
        }
        else
        {
            return value.substring(1, value.length());
        }
    }

}
