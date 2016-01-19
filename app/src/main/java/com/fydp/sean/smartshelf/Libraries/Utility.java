package com.fydp.sean.smartshelf.Libraries;

import android.app.Application;
import android.util.Log;

/**
 * Created by seanm on 2015-10-05.
 */
public class Utility
{

    private static Utility instance;
    private static boolean online = false;

    // Constructor
    private Utility()
    {

    }

    public static Utility create()
    {
        if (instance == null)
        {
            instance = new Utility();
        }
        return instance;
    }

    public static boolean isOnline()
    {
        return online;
    }

    public static void setOfflineMode(boolean value)
    {
        online = value;
    }
}
/*
public class Controller
{
    private static final String TAG = "Controller";
    private static sController sController;
    private Dao mDao;

    private Controller()
    {
        mDao = new Dao();
    }

    public static Controller create()
    {
        if (sController == null)
        {
            sController = new Controller();
        }
        return sController;
    }
}
*/


// How to USE
/*
Utility utility = Utility.create();
utility.setOfflineMode(false);
boolean globalVarValue = utility.isOnline();
Toast.makeText(getActivity().getApplicationContext(), "Update Successful: " + globalVarValue, Toast.LENGTH_SHORT).show();
*/