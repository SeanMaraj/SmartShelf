package com.fydp.sean.smartshelf.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fydp.sean.smartshelf.Helpers.Utility;
import com.fydp.sean.smartshelf.Models.StockNotifModel;
import com.fydp.sean.smartshelf.Models.WeatherNotifModel;
import com.fydp.sean.smartshelf.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sean on 2016-03-16.
 */
public class WeatherNotifListAdaptor extends ArrayAdapter
{
    List list = new ArrayList();

    public WeatherNotifListAdaptor(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row;
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.subview_weather, parent, false);
        }
        else
        {
            row = convertView;
        }

        // Set content of row item
        WeatherNotifModel wnm = (WeatherNotifModel)list.get(position);
        ((TextView)row.findViewById(R.id.weatherTypeText)).setText(Utility.capitalizeFirst(wnm.getWeatherType()));
        ((TextView)row.findViewById(R.id.operatorText)).setText("" + wnm.getOperator());
        ((TextView)row.findViewById(R.id.weatherValue)).setText(Utility.capitalizeFirst("" + wnm.getValue()));

        return row;
    }


}
