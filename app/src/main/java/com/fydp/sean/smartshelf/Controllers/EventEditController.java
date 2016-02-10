package com.fydp.sean.smartshelf.Controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fydp.sean.smartshelf.R;

/**
 * Created by seanm on 2016-02-09.
 */
public class EventEditController extends Fragment
{

    View rootView = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.view_eventedit, container, false);

        return rootView;
    }
}
