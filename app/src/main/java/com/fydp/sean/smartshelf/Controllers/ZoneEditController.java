package com.fydp.sean.smartshelf.Controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fydp.sean.smartshelf.Helpers.Utility;
import com.fydp.sean.smartshelf.MainActivity;
import com.fydp.sean.smartshelf.R;

/**
 * Created by Sean on 2015-07-23.
 */
public class ZoneEditController extends Fragment
{
    View rootView = null;
    CheckBox stockCheck;
    CheckBox customEventCheck;
    CheckBox weatherCheck;
    LinearLayout setWeightLayout;
    LinearLayout eventOptionsLayout;
    LinearLayout weatherOptionsLayout;
    TextView zoneNameEdit;
    TextView zoneNumberText;
    TextView baseNumberText;
    EditText weightEdit;
    Button applyButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ((MainActivity) getActivity()).setActionBarTitle("Edit Zone");
        rootView = inflater.inflate(R.layout.view_zoneedit, container, false);

        setWeightLayout = (LinearLayout) rootView.findViewById(R.id.monitorStockOptionsLayout);
        eventOptionsLayout = (LinearLayout) rootView.findViewById(R.id.eventOptionsLayout);
        weatherOptionsLayout = (LinearLayout) rootView.findViewById(R.id.weatherOptionsLayout);
        zoneNameEdit = (TextView) rootView.findViewById(R.id.zoneNameText);
        zoneNumberText = (TextView) rootView.findViewById(R.id.zoneNumberText);
        baseNumberText = (TextView) rootView.findViewById(R.id.baseNumberText);
        weightEdit = (EditText) rootView.findViewById(R.id.initWeightEdit);
        applyButton = (Button) rootView.findViewById((R.id.applyBtn));
        stockCheck = (CheckBox) rootView.findViewById(R.id.monitorStockCheck);
        customEventCheck = (CheckBox) rootView.findViewById(R.id.customEventCheck);
        stockCheck = (CheckBox) rootView.findViewById(R.id.monitorStockCheck);
        weatherCheck = (CheckBox) rootView.findViewById(R.id.weatherCheck);

        setListeners();
        getData();

        return rootView;
    }

    private void setListeners()
    {
        stockCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (!isChecked)
                {
                    setWeightLayout.setVisibility(View.GONE);
                } else if (isChecked)
                {
                    setWeightLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        customEventCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (!isChecked)
                {
                    eventOptionsLayout.setVisibility(View.GONE);
                } else if (isChecked)
                {
                    eventOptionsLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        weatherCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (!isChecked)
                {
                    weatherOptionsLayout.setVisibility(View.GONE);
                } else if (isChecked)
                {
                    weatherOptionsLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        applyButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onApplyButton(v);

            }
        });
    }

    private void getData()
    {
        populateView();
    }

    private void populateView()
    {
        int zoneNumber = getArguments().getInt("position") + 1;
        float initialWeight = getArguments().getFloat("initialWeight");
        zoneNumberText.setText("Zone Number: " + zoneNumber);
        zoneNameEdit.setText(getArguments().getString("itemName"));
        weightEdit.setText("" + initialWeight);

        if (initialWeight > 0)
        {
            stockCheck.setChecked(true);
        } else
        {
            stockCheck.setChecked(false);
        }
    }

    private void onApplyButton(View v)
    {
        //TODO: handle spaces in zone names

        String baseId = "1"; //TODO: get real value
        String zoneId = "" + (getArguments().getInt("position") + 1);
        String zoneName = zoneNameEdit.getText().toString();
        String initialWeight = weightEdit.getText().toString();

        String sendWeightCommand = "updateinitialweight/" + baseId + "/" + zoneId + "/" + initialWeight + "/";
        String sendNameCommand = "updatedescription/" + baseId + "/" + zoneId + "/" + zoneName + "/";

        Utility.sendData(sendWeightCommand);
        Utility.sendData(sendNameCommand);

        Toast.makeText(getActivity().getApplicationContext(), "Update Successful", Toast.LENGTH_SHORT).show();
    }

    private void editName(View v)
    {

    }
}
