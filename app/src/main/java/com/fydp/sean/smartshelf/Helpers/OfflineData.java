package com.fydp.sean.smartshelf.Helpers;

import android.util.Log;

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
            JSONZone.put("id", "2");
            JSONZone.put("desc", "Sugar");
            JSONZone.put("weight", 0.23);
            JSONZone.put("initialweight", 1.0);
            JSONZones.put(JSONZone);

            // Zone 2
            JSONZone = new JSONObject();
            JSONZone.put("id", "5");
            JSONZone.put("desc", "Flour");
            JSONZone.put("weight", 10.6);
            JSONZone.put("initialweight", 14);
            JSONZones.put(JSONZone);

            // Zone 3
            JSONZone = new JSONObject();
            JSONZone.put("id", "7");
            JSONZone.put("desc", "Nutella");
            JSONZone.put("weight", 0.1);
            JSONZone.put("initialweight", 156);
            JSONZones.put(JSONZone);

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
            JSONObject JSONEvent = new JSONObject();
            JSONEvent.put("id", 100);
            JSONEvent.put("notifId", 101);
            JSONEvent.put("zoneId", 1);
            JSONEvent.put("baseId", 1);
            JSONEvent.put("date", "Jan 5");
            JSONEvent.put("time", "19:00");
            JSONEvent.put("desc", "Pack for vacation");
            JSONEvent.put("repeatWeekly", true);
            JSONEvent.put("repeatMonthly", false);
            JSONEvent.put("repeatDaily", false);
            JSONEvents.put(JSONEvent);

            // Event 2
            JSONEvent = new JSONObject();
            JSONEvent.put("id", 200);
            JSONEvent.put("notifId", 201);
            JSONEvent.put("zoneId", 3);
            JSONEvent.put("baseId", 1);
            JSONEvent.put("date", "Feb 12");
            JSONEvent.put("time", "16:00");
            JSONEvent.put("desc", "Hand in report");
            JSONEvent.put("repeatWeekly", false);
            JSONEvent.put("repeatMonthly", false);
            JSONEvent.put("repeatDaily", false);
            JSONEvents.put(JSONEvent);

            // Event 3
            JSONEvent = new JSONObject();
            JSONEvent.put("id", 200);
            JSONEvent.put("notifId", 201);
            JSONEvent.put("zoneId", 5);
            JSONEvent.put("baseId", 2);
            JSONEvent.put("date", "Feb 19");
            JSONEvent.put("time", "05:00");
            JSONEvent.put("desc", "Go back home");
            JSONEvent.put("repeatWeekly", true);
            JSONEvent.put("repeatMonthly", false);
            JSONEvent.put("repeatDaily", false);
            JSONEvents.put(JSONEvent);

            // Event 4
            JSONEvent = new JSONObject();
            JSONEvent.put("id", 200);
            JSONEvent.put("notifId", 201);
            JSONEvent.put("zoneId", 6);
            JSONEvent.put("baseId", 2);
            JSONEvent.put("date", "Mar 1");
            JSONEvent.put("time", "22:40");
            JSONEvent.put("desc", "Hand in report");
            JSONEvent.put("repeatWeekly", true);
            JSONEvent.put("repeatMonthly", false);
            JSONEvent.put("repeatDaily", false);
            JSONEvents.put(JSONEvent);

            // Event 4
            JSONEvent = new JSONObject();
            JSONEvent.put("id", 200);
            JSONEvent.put("notifId", 201);
            JSONEvent.put("zoneId", 8);
            JSONEvent.put("baseId", 2);
            JSONEvent.put("date", "Apr 1");
            JSONEvent.put("time", "22:40");
            JSONEvent.put("desc", "Study for quiz");
            JSONEvent.put("repeatWeekly", true);
            JSONEvent.put("repeatMonthly", false);
            JSONEvent.put("repeatDaily", false);
            JSONEvents.put(JSONEvent);



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


        /*

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

        */

        switch (command)
        {
            case "getlowstock":
                result = "[{\"activenotificationid\": 47, \"message\": \"Zone 1: When weight l90%\", \"weight\": \"0.750000\", \"initialweight\": \"3.870000\", \"baseid\": 1, \"zoneid\": 1, \"description\": \"Name\"}]";
                break;
            case "getupcomingreminders":
                result = "[{\"date\": \"21/03/2016\", \"time\": \"22:00\", \"description\": \"Hello my \", \"zoneid\": 1, \"isactive\": 0}]";
                break;
            case "getactiveweather":
                result = "[{\"id\": 46, \"baseid\": 2, \"zoneid\": 6, \"message\": \"Zone 6: When temperature l35 degrees\"}, {\"id\": 53, \"baseid\": 1, \"zoneid\": 2, \"message\": \"Zone 2: When temperature L25 degrees\"}, {\"id\": 54, \"baseid\": 2, \"zoneid\": 6, \"message\": \"Zone 6: When temperature L35 degrees\"}]";
                break;
            case "getcurrentweather":
                result = "[{\"status\": \"clear\", \"temperature\": -1.24}]";
                break;
            case "getstocknotifications/1/1":
                result = "[{\"notificationid\": 25, \"description\": \"When weight l90%\", \"threshold\": \"l90\", \"weight\": \"0.750000\", \"initialweight\": \"0.750000\"}, {\"notificationid\": 47, \"description\": \"When weight l80%\", \"threshold\": \"l80\", \"weight\": \"0.750000\", \"initialweight\": \"0.750000\"}, {\"notificationid\": 48, \"description\": \"When weight l50%\", \"threshold\": \"l50\", \"weight\": \"0.750000\", \"initialweight\": \"0.750000\"}]";
                break;
            case "getreminders/1/1":
                result = "[{\"notificationid\": 43, \"date\": \"21/03/2016\", \"time\": \"10:00\", \"description\": \"Des \"}, {\"notificationid\": 44, \"date\": \"21/03/2016\", \"time\": \"22:00\", \"description\": \"Hello my \"}]";
                break;
            case "getweathernotifications/1/1":
                result = "[{\"notificationid\": 45, \"checktype\": \"temperature\", \"checkvalue\": \"l10\"}, {\"notificationid\": 46, \"checktype\": \"uv\", \"checkvalue\": \"g60\"}]";
                break;
            case "getzones/1":
                result = "[{\"id\": 1, \"baseid\": 1, \"type\": \"scale\", \"weight\": \"0.750000\", \"initialweight\": \"3.870000\", \"units\": \"kg\", \"description\": \"Name\"}, {\"id\": 2, \"baseid\": 1, \"type\": \"scale\", \"weight\": \"0.000000\", \"initialweight\": \"0.000000\", \"units\": \"kg\", \"description\": \"Z2B1\"}, {\"id\": 3, \"baseid\": 1, \"type\": \"scale\", \"weight\": \"0.080000\", \"initialweight\": \"0.000000\", \"units\": \"kg\", \"description\": \"Enter Description Here\"}, {\"id\": 4, \"baseid\": 1, \"type\": \"scale\", \"weight\": \"0.400000\", \"initialweight\": \"0.000000\", \"units\": \"kg\", \"description\": \"Enter Description Here\"}]";
                break;
            case "getzones/2":
                result = "[{\"id\": 5, \"baseid\": 2, \"type\": \"scale\", \"weight\": \"2.130000\", \"initialweight\": \"5.700000\", \"units\": \"kg\", \"description\": \"Z5B2\"}, {\"id\": 6, \"baseid\": 2, \"type\": \"scale\", \"weight\": \"2.130000\", \"initialweight\": \"0.000000\", \"units\": \"kg\", \"description\": \"Z6B2\"}, {\"id\": 7, \"baseid\": 2, \"type\": \"scale\", \"weight\": \"0.440000\", \"initialweight\": \"0.000000\", \"units\": \"kg\", \"description\": \"Enter Description Here\"}, {\"id\": 8, \"baseid\": 2, \"type\": \"scale\", \"weight\": \"0.440000\", \"initialweight\": \"0.000000\", \"units\": \"kg\", \"description\": \"Enter Description Here\"}]";
                break;
            case "getzones/3":
                result = "[{\"id\": 9, \"baseid\": 3, \"type\": \"scale\", \"weight\": \"1.160000\", \"initialweight\": \"0.000000\", \"units\": \"kg\", \"description\": \"Sugar\"}, {\"id\": 10, \"baseid\": 3, \"type\": \"scale\", \"weight\": \"0.890000\", \"initialweight\": \"7.000000\", \"units\": \"kg\", \"description\": \"Sugar\"}, {\"id\": 11, \"baseid\": 3, \"type\": \"scale\", \"weight\": \"0.160000\", \"initialweight\": \"0.000000\", \"units\": \"kg\", \"description\": \"Enter Description Here\"}, {\"id\": 12, \"baseid\": 3, \"type\": \"scale\", \"weight\": \"0.440000\", \"initialweight\": \"0.000000\", \"units\": \"kg\", \"description\": \"Enter Description Here\"}]";
                break;
            case "getbases":
                result = "[{\"id\": 1, \"position\": \"1\", \"active\": 1}, {\"id\": 2, \"position\": \"2\", \"active\": 1}, {\"id\": 3, \"position\": \"3\", \"active\": 1}]";
                break;
            case "getallreminders":
                result = "[{\"notificationid\": 11, \"date\": \"17/03/2016\", \"time\": \"13:18\", \"description\": \"Teeest \", \"baseid\": 3, \"zoneid\": 9}, {\"notificationid\": 13, \"date\": \"17/03/2016\", \"time\": \"13:32\", \"description\": \"Demo \", \"baseid\": 3, \"zoneid\": 9}, {\"notificationid\": 43, \"date\": \"21/03/2016\", \"time\": \"10:00\", \"description\": \"Des \", \"baseid\": 1, \"zoneid\": 1}, {\"notificationid\": 44, \"date\": \"21/03/2016\", \"time\": \"22:00\", \"description\": \"Hello my \", \"baseid\": 1, \"zoneid\": 1}]";
                break;

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
