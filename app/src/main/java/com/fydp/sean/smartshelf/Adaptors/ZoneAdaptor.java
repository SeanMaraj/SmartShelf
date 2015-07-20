package com.fydp.sean.smartshelf.Adaptors;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fydp.sean.smartshelf.Models.ZoneModel;
import com.fydp.sean.smartshelf.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sean on 2015-07-19.
 */
public class ZoneAdaptor extends ArrayAdapter {

    List list = new ArrayList();

    public ZoneAdaptor(Context context, int resource) {
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
            row = inflater.inflate(R.layout.row_zone, parent, false);
        }
        else
        {
            row = convertView;
        }

        // Set content of row item
        ZoneModel zone = (ZoneModel)list.get(position);
        ((TextView)row.findViewById(R.id.zoneNumberText)).setText(""+zone.getZoneNumber());
        ((TextView)row.findViewById(R.id.zoneNameText)).setText(zone.getZoneName());
        ((TextView)row.findViewById(R.id.initialWeightText)).setText("" + zone.getInitialWeight() + "g");
        TextView percentView = ((TextView)row.findViewById(R.id.percentText));
        int percent = zone.getPercentage();
        setPercentage(percentView, percent);


        return row;
    }

    void setPercentage(TextView view, int percent)
    {
        view.setText("" + percent + "%");
        if(percent <= 10)
        {
            view.setTextColor(Color.RED);
        }else if (percent >10 && percent <50)
        {
            view.setTextColor(Color.YELLOW);
        }else
        {
            view.setTextColor(Color.GREEN);
        }

    }
}