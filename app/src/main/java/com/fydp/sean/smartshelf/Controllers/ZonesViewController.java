package com.fydp.sean.smartshelf.Controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fydp.sean.smartshelf.Adaptors.ZoneAdaptor;
import com.fydp.sean.smartshelf.Models.ZoneModel;
import com.fydp.sean.smartshelf.R;

/**
 * Created by Sean on 2015-07-12.
 */
public class ZonesViewController extends Fragment {

    View rootView = null;
    ListView zoneListView;
    ZoneAdaptor adaptor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.view_zones, container, false);
        zoneListView = (ListView)rootView.findViewById(R.id.zoneList);

        //TODO: get data from database
        int[] zoneNumbers = {1,2,3,4};
        String[] itemNames = {"Sugar", "Flour", "Bolts", "Nuts"};
        int[] percentages = {25,8,80,50};
        int[] initialWeights = {100, 200, 62, 400};


        adaptor = new ZoneAdaptor(getActivity(), R.layout.row_zone);
        zoneListView.setAdapter(adaptor);

        for (int i=0; i<zoneNumbers.length; i++)
        {
            ZoneModel zone = new ZoneModel(zoneNumbers[i], itemNames[i], percentages[i], initialWeights[i]);
            adaptor.add(zone);
        }

        return rootView;
    }
}
