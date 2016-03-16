package com.fydp.sean.smartshelf.Helpers;

import android.util.Log;

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

    private static boolean offlineMode = false;
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

    public static void sendData(String command)
    {
        Log.d("Log", "Sending data: " + command);

        if (!offlineMode)
        {
            String url = serverAddress + command;
            DataFetcher df = new DataFetcher();

            try
            {
                df.execute(url).get();
            } catch (Exception e)
            {
                e.printStackTrace();
                Log.d("Error", "Error sending online data");
            }

        } else
        {
            Log.d("Log", "Offline Mode is on");
            sendOfflineData(command);
        }
    }

    public static String fetchData(String command)
    {
        String result = "";

        if (!offlineMode)
        {
            String url = serverAddress + command;
            Log.d("Log", "Fetching url: " + url);
            DataFetcher df = new DataFetcher();

            try
            {
                result = df.execute(url).get();
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
            Log.d("LOG", "Result is null. Fetching offline data");
            return fetchOfflineData(command);
        } else
        {
            Log.d("Log", "Fetching online data successful");
            return result;
        }

    }

    public static void sendOfflineData(String command)
    {
        Log.d("Log", "Sending offline data");
        OfflineData.setData(command);
    }

    public static String fetchOfflineData(String command)
    {
        Log.d("Log", "Fetching offline data");
        return OfflineData.getData(command);
    }

    public static void log(String message)
    {
        Log.d("Log", message);
    }

    public static void testNetwork()
    {
        log("Testing Network");

        long startTime = System.nanoTime();
        log("Start: " + startTime);
        Utility.fetchData("getzones/1");
        long endTime = System.nanoTime();
        log("Endtime: " + endTime);
        long estimatedTime = endTime - startTime;
        log(""+ estimatedTime);


        startTime = System.nanoTime();
        log("Start: " + startTime);
        Utility.fetchData("getzones/1");
        endTime = System.nanoTime();
        log("Endtime: " + endTime);
        estimatedTime = endTime - startTime;
        log(""+ estimatedTime);

        startTime = System.nanoTime();
        log("Start: " + startTime);
        Utility.fetchData("getzones/1");
        endTime = System.nanoTime();
        log("Endtime: " + endTime);
        estimatedTime = endTime - startTime;
        log(""+ estimatedTime);

        startTime = System.nanoTime();
        log("Start: " + startTime);
        Utility.fetchData("getzones/1");
        endTime = System.nanoTime();
        log("Endtime: " + endTime);
        estimatedTime = endTime - startTime;
        log(""+ estimatedTime);

        startTime = System.nanoTime();
        log("Start: " + startTime);
        Utility.fetchData("getzones/1");
        endTime = System.nanoTime();
        log("Endtime: " + endTime);
        estimatedTime = endTime - startTime;
        log(""+ estimatedTime);

        startTime = System.nanoTime();
        log("Start: " + startTime);
        Utility.fetchData("getzones/1");
        endTime = System.nanoTime();
        log("Endtime: " + endTime);
        estimatedTime = endTime - startTime;
        log(""+ estimatedTime);





    }

    public static String getDate(String date)
    {
        String[] parts = date.split("/");
        String formatedDate = "";

        switch (Integer.parseInt(parts[1]))
        {
            case 1:
                formatedDate += "Jan";
                break;
            case 2:
                formatedDate += "Feb";
                break;
            case 3:
                formatedDate += "Mar";
                break;
            case 4:
                formatedDate += "Apr";
                break;
            case 5:
                formatedDate += "May";
                break;
            case 6:
                formatedDate += "June";
                break;
            case 7:
                formatedDate += "July";
                break;
            case 8:
                formatedDate += "Aug";
                break;
            case 9:
                formatedDate += "Sept";
                break;
            case 10:
                formatedDate += "Oct";
                break;
            case 11:
                formatedDate += "Nov";
                break;
            case 12:
                formatedDate += "Dec";
                break;
        }

        return formatedDate + " " + parts[0];
    }

    public static String capitalizeFirst(String s)
    {
        return s.substring(0,1).toUpperCase() + s.substring(1);
    }

}


/* How to Use:
Utility utility = Utility.create();
utility.setOfflineMode(false);
boolean globalVarValue = utility.offlineMode();
Toast.makeText(getActivity().getApplicationContext(), "Update Successful: " + globalVarValue, Toast.LENGTH_SHORT).show();
*/