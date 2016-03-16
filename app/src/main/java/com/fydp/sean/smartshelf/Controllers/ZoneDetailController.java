package com.fydp.sean.smartshelf.Controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.fydp.sean.smartshelf.Adaptors.EventAdaptor;
import com.fydp.sean.smartshelf.Adaptors.StockNotifListAdaptor;
import com.fydp.sean.smartshelf.Adaptors.ZoneAdaptor;
import com.fydp.sean.smartshelf.MainActivity;
import com.fydp.sean.smartshelf.Models.EventModel;
import com.fydp.sean.smartshelf.Models.StockNotifModel;
import com.fydp.sean.smartshelf.Models.ZoneModel;
import com.fydp.sean.smartshelf.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Sean on 2016-03-10.
 */
public class ZoneDetailController extends Fragment
{
    View rootView = null;
    Button addNotifBtn;
    TextView zoneNameText;
    TextView zoneNumberText;
    ListView stockList;
    ListView reminderList;
    ListView weatherList;

    StockNotifListAdaptor stockNotifAdaptor;
    ArrayList<StockNotifModel> stockNotifs = new ArrayList<StockNotifModel>();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ((MainActivity) getActivity()).setActionBarTitle("Zone Detail");
        rootView = inflater.inflate(R.layout.view_zone_detail, container, false);

        addNotifBtn = (Button)rootView.findViewById(R.id.addNotifBtn);
        zoneNameText = (TextView)rootView.findViewById(R.id.zoneNameText);
        zoneNumberText = (TextView)rootView.findViewById(R.id.zoneNumberText);
        stockList = (ListView)rootView.findViewById(R.id.stockList);
        reminderList = (ListView)rootView.findViewById(R.id.reminderList);
        weatherList = (ListView)rootView.findViewById(R.id.weatherList);


        // Setup stock notifications list
        stockList = (ListView) rootView.findViewById(R.id.stockList);
        stockNotifAdaptor = new StockNotifListAdaptor(getActivity(), R.layout.subview_notif_stock);
        stockList.setAdapter(stockNotifAdaptor);
        stockNotifs.clear();

        StockNotifModel o = new StockNotifModel(1,30,34);
        stockNotifAdaptor.add(o);



        getData();
        setOnItemClicks();
        populateLists();

        return rootView;
    }

    private void getData()
    {

    }

    private void setOnItemClicks()
    {
        addNotifBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Fragment fragment = new AddNotifController();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });


    }

    private void populateLists()
    {

    }

}
