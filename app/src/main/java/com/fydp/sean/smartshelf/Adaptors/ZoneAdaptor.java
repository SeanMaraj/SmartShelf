package com.fydp.sean.smartshelf.Adaptors;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
            row = inflater.inflate(R.layout.subview_zone, parent, false);
        }
        else
        {
            row = convertView;
        }

        // Set content of row item
        final ZoneModel zone = (ZoneModel)list.get(position);
        float percent = Math.round(100.0 * zone.getPercentage()) / 100;
        ((TextView)row.findViewById(R.id.zoneNameTxt)).setText(zone.getMessage());
        ((TextView)row.findViewById(R.id.initialWeightText)).setText("" + zone.getInitialWeight() + " kg");
        ((TextView)row.findViewById(R.id.currentWeightText)).setText("" + zone.getCurrentWeight() + " kg");
        TextView percentTextView = ((TextView)row.findViewById(R.id.percentageText));
        percentTextView.setText("" + percent + " %");

        setCurrentWeightColor(percentTextView, percent);

        if (zone.getIsNotif())
        {
            ((TextView)row.findViewById(R.id.zoneNumberTxt)).setVisibility(View.INVISIBLE);
        }
        else
        {
            ((TextView)row.findViewById(R.id.zoneNumberTxt)).setText("Zone " + zone.getZoneId());
        }

        if (zone.getMessage().equals("Enter Description Here"))
        {
            ((TextView)row.findViewById(R.id.zoneNameTxt)).setText("EMPTY");
        }

        return row;
    }

    //**HELPER METHODS**//

    private void setCurrentWeightColor(TextView view, float percent)
    {
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
