package com.fydp.sean.smartshelf.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fydp.sean.smartshelf.Models.ReminderModel;
import com.fydp.sean.smartshelf.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seanm on 2016-02-02.
 */
public class ReminderListAdaptor extends ArrayAdapter
{
    List list = new ArrayList();

    public ReminderListAdaptor(Context context, int resource) {
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
            row = inflater.inflate(R.layout.subview_reminder, parent, false);
        }
        else
        {
            row = convertView;
        }

        // Set content of subview_reminder item
        ReminderModel event = (ReminderModel)list.get(position);
        ((TextView)row.findViewById(R.id.eventDate)).setText(event.getDate());
        ((TextView)row.findViewById(R.id.eventDescription)).setText(event.getDesc());
        ((TextView)row.findViewById(R.id.eventZoneIdText)).setText("Zone: " + event.getZoneId());
        ((TextView)row.findViewById(R.id.eventTime)).setText(event.getTime());

        return row;
    }

}
