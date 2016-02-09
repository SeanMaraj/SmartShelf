package com.fydp.sean.smartshelf;

import android.util.Log;
import android.widget.Toast;

import com.fydp.sean.smartshelf.Libraries.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by seanm on 2016-01-19.
 */
public class OfflineData
{
    private static OfflineData instance;
    private static String offlineZones;
    private static String offlineEvents;

    // Constructor
    private OfflineData(){}
    // Singleton pattern
    public static OfflineData create()
    {
        if (instance == null)
        {
            instance = new OfflineData();
            setup();
        }
        return instance;
    }

    public static void setup()
    {
        Log.d("Log", "Setting up offline data");

        ////////////////////////////////////////////////////
        // CREATE  ZONE DATA
        JSONArray JSONZones = new JSONArray();
        try
        {
            // Zone 1
            JSONObject JSONZone = new JSONObject();
            JSONZone.put("id", "100");
            JSONZone.put("desc", "Zone100");
            JSONZone.put("weight", 0.5);
            JSONZone.put("initialweight", 1.2);
            JSONZones.put(JSONZone);

            // Zone 2
            JSONObject JSONZone1 = new JSONObject();
            JSONZone1.put("id", "200");
            JSONZone1.put("desc", "Zone200");
            JSONZone1.put("weight", 70);
            JSONZone1.put("initialweight", 150);
            JSONZones.put(JSONZone1);

        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        offlineZones = JSONZones.toString();
        //
        ////////////////////////////////////////////////////

        ////////////////////////////////////////////////////
        // CREATE  EVENT DATA
        JSONArray JSONEvents = new JSONArray();
        try
        {

            // Event 1
            JSONObject JSONEvent1 = new JSONObject();
            JSONEvent1.put("id", 100);
            JSONEvent1.put("notifId", 101);
            JSONEvent1.put("zoneId", 102);
            JSONEvent1.put("baseId", 103);
            JSONEvent1.put("date", "Jan 5");
            JSONEvent1.put("time", "19:00");
            JSONEvent1.put("repeatWeekly", true);
            JSONEvent1.put("repeatMonthly", false);
            JSONEvent1.put("repeatDaily", false);
            JSONEvents.put(JSONEvent1);

            // Event 2
            JSONObject JSONEvent2 = new JSONObject();
            JSONEvent2.put("id", 200);
            JSONEvent2.put("notifId", 201);
            JSONEvent2.put("zoneId", 102);
            JSONEvent2.put("baseId", 103);
            JSONEvent2.put("date", "Jan 9");
            JSONEvent2.put("time", "13:00");
            JSONEvent2.put("repeatWeekly", true);
            JSONEvent2.put("repeatMonthly", false);
            JSONEvent2.put("repeatDaily", false);
            JSONEvents.put(JSONEvent2);

            // Event 2
            JSONEvent2 = new JSONObject();
            JSONEvent2.put("id", 200);
            JSONEvent2.put("notifId", 201);
            JSONEvent2.put("zoneId", 102);
            JSONEvent2.put("baseId", 103);
            JSONEvent2.put("date", "Jan 9");
            JSONEvent2.put("time", "13:00");
            JSONEvent2.put("repeatWeekly", true);
            JSONEvent2.put("repeatMonthly", false);
            JSONEvent2.put("repeatDaily", false);
            JSONEvents.put(JSONEvent2);

            // Event 2
            JSONEvent2 = new JSONObject();
            JSONEvent2.put("id", 200);
            JSONEvent2.put("notifId", 201);
            JSONEvent2.put("zoneId", 102);
            JSONEvent2.put("baseId", 103);
            JSONEvent2.put("date", "Jan 9");
            JSONEvent2.put("time", "13:00");
            JSONEvent2.put("repeatWeekly", true);
            JSONEvent2.put("repeatMonthly", false);
            JSONEvent2.put("repeatDaily", false);
            JSONEvents.put(JSONEvent2);

            // Event 2
            JSONEvent2 = new JSONObject();
            JSONEvent2.put("id", 200);
            JSONEvent2.put("notifId", 201);
            JSONEvent2.put("zoneId", 102);
            JSONEvent2.put("baseId", 103);
            JSONEvent2.put("date", "Jan 9");
            JSONEvent2.put("time", "13:00");
            JSONEvent2.put("repeatWeekly", true);
            JSONEvent2.put("repeatMonthly", false);
            JSONEvent2.put("repeatDaily", false);
            JSONEvents.put(JSONEvent2);

            // Event 2
            JSONEvent2 = new JSONObject();
            JSONEvent2.put("id", 200);
            JSONEvent2.put("notifId", 201);
            JSONEvent2.put("zoneId", 102);
            JSONEvent2.put("baseId", 103);
            JSONEvent2.put("date", "Jan 9");
            JSONEvent2.put("time", "13:00");
            JSONEvent2.put("repeatWeekly", true);
            JSONEvent2.put("repeatMonthly", false);
            JSONEvent2.put("repeatDaily", false);
            JSONEvents.put(JSONEvent2);

            // Event 2
            JSONEvent2 = new JSONObject();
            JSONEvent2.put("id", 200);
            JSONEvent2.put("notifId", 201);
            JSONEvent2.put("zoneId", 102);
            JSONEvent2.put("baseId", 103);
            JSONEvent2.put("date", "Jan 9");
            JSONEvent2.put("time", "13:00");
            JSONEvent2.put("repeatWeekly", true);
            JSONEvent2.put("repeatMonthly", false);
            JSONEvent2.put("repeatDaily", false);
            JSONEvents.put(JSONEvent2);

            // Event 2
            JSONEvent2 = new JSONObject();
            JSONEvent2.put("id", 200);
            JSONEvent2.put("notifId", 201);
            JSONEvent2.put("zoneId", 102);
            JSONEvent2.put("baseId", 103);
            JSONEvent2.put("date", "Jan 9");
            JSONEvent2.put("time", "13:00");
            JSONEvent2.put("repeatWeekly", true);
            JSONEvent2.put("repeatMonthly", false);
            JSONEvent2.put("repeatDaily", false);
            JSONEvents.put(JSONEvent2);

            // Event 2
            JSONEvent2 = new JSONObject();
            JSONEvent2.put("id", 200);
            JSONEvent2.put("notifId", 201);
            JSONEvent2.put("zoneId", 102);
            JSONEvent2.put("baseId", 103);
            JSONEvent2.put("date", "Jan 9");
            JSONEvent2.put("time", "13:00");
            JSONEvent2.put("repeatWeekly", true);
            JSONEvent2.put("repeatMonthly", false);
            JSONEvent2.put("repeatDaily", false);
            JSONEvents.put(JSONEvent2);

            // Event 2
            JSONEvent2 = new JSONObject();
            JSONEvent2.put("id", 200);
            JSONEvent2.put("notifId", 201);
            JSONEvent2.put("zoneId", 102);
            JSONEvent2.put("baseId", 103);
            JSONEvent2.put("date", "Jan 9");
            JSONEvent2.put("time", "13:00");
            JSONEvent2.put("repeatWeekly", true);
            JSONEvent2.put("repeatMonthly", false);
            JSONEvent2.put("repeatDaily", false);
            JSONEvents.put(JSONEvent2);

        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        offlineEvents = JSONEvents.toString();
        //
        ////////////////////////////////////////////////////
    }

    // Matches command with corresponding data
    public static String getData(String command)
    {
        String result = "";

        if (command.equals("getzones/1"))
        {
            result =  offlineZones;
        }
        else if (command.equals("getevents/1"))
        {
            result =  offlineEvents;
        }

        if (result != "")
        {
            Log.d("Log", "Offline result is not null");
        }
        else
        {
            Log.d("Log", "Offline result is null");
        }

        return result;
    }

    public static void setData(String command)
    {
        Log.d("Log", "Setting data");
        String[] s = command.split("/");

        Utility.log(s[0]);
        Utility.log(s[1]);

        switch(s[0])
        {
            case "updatedescription":
                updateDescription(Integer.parseInt(s[1]),Integer.parseInt(s[2]), s[3]);
                break;
            case "updateinitialweight":
                updateInitialWeight(Integer.parseInt(s[1]),Integer.parseInt(s[2]), Float.parseFloat(s[3]));
                break;
            default:
                Log.d("Log", "No offline command set");
        }
    }

    private static String getZones()
    {
        Log.d("OfflineData", "Getting offline zones");
        return offlineZones;
    }

    private static void updateDescription(int baseId, int zoneId, String zoneName )
    {
        Log.d("Log", "Updating offline description: " + zoneName);

        try
        {
            JSONArray JSONZones = new JSONArray(offlineZones);
            JSONObject JSONZone = JSONZones.getJSONObject(zoneId - 1);
            JSONZone.put("desc", zoneName);
            JSONZones.put(zoneId - 1, JSONZone);
            offlineZones = JSONZones.toString();
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private static void updateInitialWeight(int baseId, int zoneId, float intitalWeight)
    {
        Log.d("Log", "Updating offline initial weight: " + intitalWeight);

        try
        {
            JSONArray JSONZones = new JSONArray(offlineZones);
            JSONObject JSONZone = JSONZones.getJSONObject(zoneId - 1);
            JSONZone.put("initialweight", intitalWeight);
            JSONZones.put(zoneId - 1, JSONZone);
            offlineZones = JSONZones.toString();
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
