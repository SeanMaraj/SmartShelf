package com.fydp.sean.smartshelf.Controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fydp.sean.smartshelf.MainActivity;
import com.fydp.sean.smartshelf.R;

/**
 * Created by Sean on 2016-03-09.
 */
public class AddNotifController extends Fragment
{
    View rootView = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ((MainActivity) getActivity()).setActionBarTitle("Add Notification");
        rootView = inflater.inflate(R.layout.view_notif_add, container, false);

        return rootView;
    }
}
