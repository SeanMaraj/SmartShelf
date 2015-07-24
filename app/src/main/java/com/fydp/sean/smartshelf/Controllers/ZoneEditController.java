package com.fydp.sean.smartshelf.Controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.fydp.sean.smartshelf.R;

/**
 * Created by Sean on 2015-07-23.
 */
public class ZoneEditController extends Fragment {

    View rootView = null;
    int positon;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.view_zoneedit, container, false);

        run();

        return rootView;
    }

    private void run() {

        EditText zoneNameText = (EditText)rootView.findViewById(R.id.zoneNameEdit);

        int i = getArguments().getInt("position");

        zoneNameText.setText(""+i);


    }
}
