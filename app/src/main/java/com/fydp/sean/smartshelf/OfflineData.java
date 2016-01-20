package com.fydp.sean.smartshelf;

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

    // Constructor
    private OfflineData(){}
    // Singleton pattern
    public static OfflineData create()
    {
        if (instance == null)
        {
            instance = new OfflineData();
        }
        return instance;
    }

    // Matches command with corresponding data
    public static String getData(String command)
    {
        String result = "";
        if (command.equals("getzones/1"))
        {
            if (offlineZones != null)
            {
                return offlineZones;
            }
            else
            {
                return getZones();
            }
        }

        return result;
    }

    public static void setData(String command)
    {
        String[] c = command.split("/");

        Utility.log(c[0]);
        Utility.log(c[1]);
    }

    //-- FAKE DATA --//

    private static String getZones()
    {

        JSONArray JSONZones = new JSONArray();
        try
        {
            JSONObject JSONZone = new JSONObject();
            JSONZone.put("id", "100");
            JSONZone.put("desc", "Zone100");
            JSONZone.put("weight", 0.5);
            JSONZone.put("initialweight", 1.2);
            JSONZones.put(JSONZone);


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
        return offlineZones;
    }
}
