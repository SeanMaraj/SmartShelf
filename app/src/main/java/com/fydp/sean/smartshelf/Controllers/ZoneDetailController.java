package com.fydp.sean.smartshelf.Controllers;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import org.w3c.dom.Text;

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
    Button setInitWeightBtn;
    TextView initWeightText;
    TextView currentWeightText;
    TextView weightTitleText;
    TextView reminderTitleText;
    TextView weatherTitleText;
    ImageView addWeightBtn;
    ImageView addReminderBtn;
    ImageView addWeatherBtn;

    TextView stockTab;
    TextView reminderTab;
    TextView weatherTab;
    LinearLayout stockNotifLayout;
    LinearLayout reminderNotifLayout;
    LinearLayout weatherNotifLayout;


    ArrayList<StockNotifModel> stockNotifs = new ArrayList<StockNotifModel>();
    ArrayList<ReminderModel> reminders = new ArrayList<ReminderModel>();
    ArrayList<WeatherNotifModel> weatherNotifs = new ArrayList<WeatherNotifModel>();
    StockNotifListAdaptor stockNotifAdaptor;
    ReminderListAdaptor reminderAdaptor;
    WeatherNotifListAdaptor weatherNotifAdaptor;

    int zoneId = 1;
    int baseId = 1;
    float initWeight;
    float currentWeight;

    int listItemNumber;
    SelectedList selectedList;

    public enum SelectedList {
        STOCK,
        REMINDERS,
        WEATHER
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ((MainActivity) getActivity()).setActionBarTitle("Zone Detail");
        Utility.setCurrentFragment("zoneDetailFragment");
        rootView = inflater.inflate(R.layout.view_zone_detail, container, false);

        zoneNameEdit = (EditText)rootView.findViewById(R.id.zoneNameEdit);
        zoneIdText = (TextView)rootView.findViewById(R.id.zoneNumberText);
        baseIdText = (TextView)rootView.findViewById(R.id.baseNumberText);
        zoneNumberText = (TextView)rootView.findViewById(R.id.zoneNumberText);
        stockList = (ListView)rootView.findViewById(R.id.stockList);
        reminderList = (ListView)rootView.findViewById(R.id.reminderList);
        weatherList = (ListView)rootView.findViewById(R.id.weatherList);
        saveBtn = (Button)rootView.findViewById(R.id.saveBtn);
        initWeightText = (TextView)rootView.findViewById(R.id.initialWeightText);
        setInitWeightBtn = (Button)rootView.findViewById(R.id.setInitWeightBtn);
        currentWeightText = (TextView)rootView.findViewById(R.id.currentWeightText);
        weightTitleText = (TextView)rootView.findViewById(R.id.weightTitleText);
        reminderTitleText = (TextView)rootView.findViewById(R.id.reminderTitleText);
        weatherTitleText = (TextView)rootView.findViewById(R.id.weatherTitleText);
        addWeightBtn = (ImageView)rootView.findViewById(R.id.addWeightBtn);
        addReminderBtn = (ImageView)rootView.findViewById(R.id.addReminderBtn);
        addWeatherBtn = (ImageView)rootView.findViewById(R.id.addWeatherBtn);

        // Setup tabs
        stockTab = (TextView)rootView.findViewById(R.id.stockTab);
        reminderTab = (TextView)rootView.findViewById(R.id.reminderTab);
        weatherTab = (TextView)rootView.findViewById(R.id.weatherTab);
        stockNotifLayout = (LinearLayout)rootView.findViewById(R.id.stockNotifLayout);
        reminderNotifLayout = (LinearLayout)rootView.findViewById(R.id.reminderNotifLayout);
        weatherNotifLayout = (LinearLayout)rootView.findViewById(R.id.weatherNotifLayout);
        stockNotifLayout.setVisibility(View.VISIBLE);
        reminderNotifLayout.setVisibility(View.INVISIBLE);
        weatherNotifLayout.setVisibility(View.INVISIBLE);


        //Set zone info
        baseId = Integer.parseInt(getArguments().get("baseId").toString());
        zoneId = Integer.parseInt(getArguments().get("zoneId").toString());
        initWeight = getArguments().getFloat("initWeight");
        currentWeight = getArguments().getFloat("currentWeight");
        zoneNameEdit.setText("" + getArguments().get("zoneName"));
        zoneIdText.setText("Zone Number: " + zoneId);
        baseIdText.setText("Base Number: " + baseId);
        initWeightText.setText("Initial Weight: " + initWeight + " kg");
        currentWeightText.setText("Current Weight: " + currentWeight + " kg");

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

        // Register for context menus
        registerForContextMenu(stockList);
        registerForContextMenu(reminderList);
        registerForContextMenu(weatherList);

        getData();
        setOnClicks();
        populateStockList();
        populateReminderList();
        populateWeatherList();

        return rootView;
    }

    private void getData()
    {
        parseStockNotifsResult(Utility.fetchData("getstocknotifications/" + baseId + "/" + zoneId));
        parseRemindersResult(Utility.fetchData("getreminders/" + baseId + "/" + zoneId));
        parseWeatherResult(Utility.fetchData(("getweathernotifications/" + baseId + "/" + zoneId)));
        setCurrentWeight(Utility.fetchData("getweight/" + baseId + "/" + zoneId));
        setInitialWeight(Utility.fetchData("getinitialweight/" + baseId + "/" + zoneId));
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
                WeatherNotifModel weatherNotif = new WeatherNotifModel(o.getInt("notificationid"), weatherType, operator, value, 0, "");
                weatherNotifs.add(weatherNotif);
            }

        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }


    private void setOnClicks()
    {
        addWeightBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Fragment fragment = new AddNotifController();

                Bundle args = new Bundle();
                args.putInt("zoneId", zoneId);
                args.putInt("baseId", baseId);
                args.putFloat("initWeight", initWeight);
                args.putString("notifType", "weight");
                fragment.setArguments(args);

                Utility.setCurrentFragment("addNotifFragment");

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, "addNotifFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        addReminderBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Fragment fragment = new AddNotifController();

                Bundle args = new Bundle();
                args.putInt("zoneId", zoneId);
                args.putInt("baseId", baseId);
                args.putFloat("initWeight", initWeight);
                args.putString("notifType", "reminder");
                fragment.setArguments(args);

                Utility.setCurrentFragment("addNotifFragment");

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, "addNotifFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        addWeatherBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Fragment fragment = new AddNotifController();

                Bundle args = new Bundle();
                args.putInt("zoneId", zoneId);
                args.putInt("baseId", baseId);
                args.putFloat("initWeight", initWeight);
                args.putString("notifType", "weather");
                fragment.setArguments(args);

                Utility.setCurrentFragment("addNotifFragment");

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, "addNotifFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });


        zoneNameEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                LayoutInflater inflater = getActivity().getLayoutInflater();

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Set Name");

                final View layout = inflater.inflate(R.layout.dialog_edittext,null);
                alertDialog.setView(layout);
                final EditText e = (EditText)(layout.findViewById(R.id.dialogEdit));
                e.setText(zoneNameEdit.getText());

                alertDialog.setPositiveButton("SET", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Log.d("Log", "Updating Name");

                        // Send name
                        String newName = e.getText().toString();

                        Utility.sendData("updatedescription/" + baseId + "/" + zoneId + "/" + newName);
                        zoneNameEdit.setText(newName);
                        Toast.makeText(getActivity(), "Updated zone name to " + newName, Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Log.d("LOG", "CANCEL");
                    }
                });
                alertDialog.show();
            }
        });

        setInitWeightBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("Log", "Setting Init Weight");

                Utility.sendData("setinitialweight/" + baseId + "/" + zoneId);

                refresh();

                Toast.makeText(getActivity(), "Initial weight set to " + currentWeight + " kg", Toast.LENGTH_SHORT).show();
            }
        });


        stockTab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                stockNotifLayout.setVisibility(View.VISIBLE);
                reminderNotifLayout.setVisibility(View.INVISIBLE);
                weatherNotifLayout.setVisibility(View.INVISIBLE);

                stockTab.setBackgroundColor(getResources().getColor(R.color.background2));
                reminderTab.setBackgroundColor(getResources().getColor(R.color.background3));
                weatherTab.setBackgroundColor(getResources().getColor(R.color.background3));
            }
        });

        reminderTab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                stockNotifLayout.setVisibility(View.INVISIBLE);
                reminderNotifLayout.setVisibility(View.VISIBLE);
                weatherNotifLayout.setVisibility(View.INVISIBLE);

                stockTab.setBackgroundColor(getResources().getColor(R.color.background3));
                reminderTab.setBackgroundColor(getResources().getColor(R.color.background2));
                weatherTab.setBackgroundColor(getResources().getColor(R.color.background3));
            }
        });

        weatherTab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                stockNotifLayout.setVisibility(View.INVISIBLE);
                reminderNotifLayout.setVisibility(View.INVISIBLE);
                weatherNotifLayout.setVisibility(View.VISIBLE);

                stockTab.setBackgroundColor(getResources().getColor(R.color.background3));
                reminderTab.setBackgroundColor(getResources().getColor(R.color.background3));
                weatherTab.setBackgroundColor(getResources().getColor(R.color.background2));
            }
        });

        stockList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                listItemNumber = position;
                selectedList = SelectedList.STOCK;
                stockList.showContextMenu();
            }
        });

        reminderList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                listItemNumber = position;
                selectedList = SelectedList.REMINDERS;
                reminderList.showContextMenu();
            }
        });

        weatherList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                listItemNumber = position;
                selectedList = SelectedList.WEATHER;
                weatherList.showContextMenu();
            }
        });

    }


    private void populateStockList()
    {
        Log.d("Log", "Populating zone detail lists");
        stockNotifAdaptor.clear();
        stockNotifAdaptor.notifyDataSetChanged();

        if (stockNotifs.size() > 0)
        {
            for (int i = 0; i < stockNotifs.size(); i++)
            {
                stockNotifAdaptor.add(stockNotifs.get(i));
            }
        }
        else
        {
            weightTitleText.setText("NO WEIGHT NOTIFICATIONS");
        }

    }

    private void populateReminderList()
    {
        Log.d("Log", "Populating reminders list");
        reminderAdaptor.clear();
        reminderAdaptor.notifyDataSetChanged();

        if (reminders.size() > 0)
        {
            for (int i = 0; i < reminders.size(); i++)
            {
                reminderAdaptor.add(reminders.get(i));
            }
        }
        else
        {
            reminderTitleText.setText("NO CUSTOM REMINDERS");
        }

    }


    private void populateWeatherList()
    {
        Log.d("Log", "Populating weather list");
        weatherNotifAdaptor.clear();
        weatherNotifAdaptor.notifyDataSetChanged();

        if (weatherNotifs.size() > 0)
        {
            for (int i = 0; i < weatherNotifs.size(); i++)
            {
                weatherNotifAdaptor.add(weatherNotifs.get(i));
            }
        }
        else
        {
            weatherTitleText.setText("NO WEATHER NOTIFICATIONS");
        }

    }

    private void setCurrentWeight(String result)
    {
        Log.d("LOG", "Parsing current weight: " + result);
        try
        {
            JSONArray a = new JSONArray(result);
            JSONObject o = a.getJSONObject(0);
            String currentWeight = "" + o.getDouble("weight");
            currentWeightText.setText("Current Weight: " + currentWeight + " kg");

        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void setInitialWeight(String result)
    {
        Log.d("LOG", "Parsing initial weight: " + result);
        try
        {
            JSONArray a = new JSONArray(result);
            JSONObject o = a.getJSONObject(0);
            String initialWeight = "" + o.getDouble("initialweight");
            initWeightText.setText("Initial Weight: " + initialWeight + " kg");

        } catch (JSONException e)
        {
            e.printStackTrace();
        }

    }

    private String getOperator(String threshold)
    {
        String op = Character.toString(threshold.charAt(0));

        switch (op)
        {
            case "l":
                op = "Falls Below ";
                break;
            case "g":
                op = "Is Above ";
                break;
            default:
                op = "Is";
                break;
        }

        return  op;
    }

    private String getWeatherValue(String operator, String value)
    {
        return value.substring(1, value.length());
    }

    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        menu.setHeaderTitle("Options");
        menu.add(Menu.NONE, 0, menu.NONE, "Delete Notification");
    }

    public boolean onContextItemSelected(MenuItem item)
    {
        if (selectedList == SelectedList.STOCK)
        {
            switch (item.getItemId()) {
                case 0:
                    Log.d("LOG", "Dismiss Notification");
                    deleteStockNotification(listItemNumber);
                    break;
                case 1:
                    Log.d("LOG", "Case 1");
                    break;
            }
        }

        else if (selectedList == SelectedList.REMINDERS)
        {
            switch (item.getItemId()) {
                case 0:
                    Log.d("LOG", "Dismiss Notification");
                    deleteReminderNotification(listItemNumber);
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
                    deleteWeatherNotification(listItemNumber);
                    break;
                case 1:
                    Log.d("LOG", "Case 1");
                    break;
            }
        }

        return super.onContextItemSelected(item);
    }

    private void deleteStockNotification(int n)
    {
        int notifId = stockNotifs.get(n).getNotifId();
        stockNotifs.remove(n);
        stockNotifAdaptor = new StockNotifListAdaptor(getActivity(), R.layout.subview_notif_stock);
        stockList.setAdapter(stockNotifAdaptor);

        Utility.sendData("deletenotification/" + notifId);
        populateStockList();
        Toast.makeText(getActivity(), "Stock Notification Deleted", Toast.LENGTH_SHORT).show();
    }

    private void deleteReminderNotification(int n)
    {
        int notifId = reminders.get(n).getNotifId();
        reminders.remove(n);
        reminderAdaptor = new ReminderListAdaptor(getActivity(), R.layout.subview_reminder);
        reminderList.setAdapter(reminderAdaptor);

        Utility.sendData("deletenotification/" + notifId);
        populateReminderList();
        Toast.makeText(getActivity(), "Reminder Notification Deleted", Toast.LENGTH_SHORT).show();

    }

    private void deleteWeatherNotification(int n)
    {
        int notifId = weatherNotifs.get(n).getNotifId();
        weatherNotifs.remove(n);
        weatherNotifAdaptor = new WeatherNotifListAdaptor(getActivity(), R.layout.subview_weather);
        weatherList.setAdapter(weatherNotifAdaptor);

        Utility.sendData("deletenotification/" + notifId);
        populateWeatherList();
        Toast.makeText(getActivity(), "Weather Notification Deleted", Toast.LENGTH_SHORT).show();
    }

    private void refresh()
    {
        Log.d("LOG", "Refresh");

        Fragment f = getActivity().getSupportFragmentManager().findFragmentByTag("zoneDetailFragment");
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.detach(f);
        ft.attach(f);
        ft.commit();
    }
}
