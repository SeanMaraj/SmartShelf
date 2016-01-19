package com.fydp.sean.smartshelf.Libraries;

import android.util.Log;

import com.fydp.sean.smartshelf.DataFetcher;

/**
 * Created by seanm on 2015-10-05.
 */
public class Utility
{
    private static Utility instance;

    // Constructor
    private Utility(){}
    // Singleton pattern
    public static Utility create()
    {
        if (instance == null)
        {
            instance = new Utility();
        }
        return instance;
    }


    //-- GLOBAL VARIABLES --//

    private static boolean offlineMode = true;
    private static String serverAddress = "http://99.235.222.196:5001//";


    //-- GLOBAL METHODS --//

    public static boolean offlineMode()
    {
        return offlineMode;
    }

    public static void setOfflineMode(boolean value)
    {
        offlineMode = value;
    }

    public static String fetchData(String command)
    {
        Log.d("Log", "Fetching data: " + command);
        String result = "";

        if (!offlineMode)
        {
            String url = serverAddress + command;
            DataFetcher df = new DataFetcher();

            try
            {
                result = df.execute(url).get();
                Log.d("Log", "Fetching online data successful");
            } catch (Exception e)
            {
                e.printStackTrace();
                Log.d("Error", "Error fetching online data");
            }

        } else
        {
            Log.d("Log", "Offline Mode is on");
        }

        // If no result, fetch local data, otherwise return result
        if (result == null || result.equals(""))
        {
            return fetchOfflineData(command);
        }
        else
        {
            return result;
        }

    }

    public static String fetchOfflineData(String command)
    {
        Log.d("Log", "Fetching offline data");
        return "";
    }
}


/* How to Use:
Utility utility = Utility.create();
utility.setOfflineMode(false);
boolean globalVarValue = utility.offlineMode();
Toast.makeText(getActivity().getApplicationContext(), "Update Successful: " + globalVarValue, Toast.LENGTH_SHORT).show();
*/