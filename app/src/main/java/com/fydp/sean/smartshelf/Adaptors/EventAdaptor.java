package com.fydp.sean.smartshelf.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fydp.sean.smartshelf.Models.EventModel;
import com.fydp.sean.smartshelf.Models.ZoneModel;
import com.fydp.sean.smartshelf.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seanm on 2016-02-02.
 */
public class EventAdaptor extends ArrayAdapter
{
    List list = new ArrayList();

    public EventAdaptor(Context context, int resource) {
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
            row = inflater.inflate(R.layout.event, parent, false);
        }
        else
        {
            row = convertView;
        }

        // Set content of event item
        EventModel event = (EventModel)list.get(position);
        ((TextView)row.findViewById(R.id.eventDate)).setText(event.getDate());
        ((TextView)row.findViewById(R.id.eventDescription)).setText(event.getDesc());
        ((TextView)row.findViewById(R.id.eventZoneIdText)).setText("Zone: " + event.getZoneId());
        ((TextView)row.findViewById(R.id.eventTime)).setText(event.getTime());
        /*((TextView)row.findViewById(R.id.zoneNumberText)).setText("" + zone.getNumber());
        ((TextView)row.findViewById(R.id.zoneNameText)).setText(zone.getName());
        ((TextView)row.findViewById(R.id.initialWeightText)).setText("" + zone.getInitialWeight() + "kg");
        TextView currentWeightText = ((TextView)row.findViewById(R.id.currentWeightText));
        currentWeightText.setText("" + zone.getCurrentWeight() + "kg");

        float percent = zone.getPercentage();
        setCurrentWeightColor(currentWeightText, percent);

        */

        //ViewGroup.LayoutParams layoutParams = ((RelativeLayout)row.findViewById(R.id.scheduleView)).getLayoutParams();
        //layoutParams.width = 100;

        return row;
    }

}
