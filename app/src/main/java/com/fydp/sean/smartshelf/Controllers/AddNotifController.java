package com.fydp.sean.smartshelf.Controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fydp.sean.smartshelf.Helpers.Utility;
import com.fydp.sean.smartshelf.MainActivity;
import com.fydp.sean.smartshelf.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sean on 2016-03-09.
 */
public class AddNotifController extends Fragment
{
    View rootView = null;

    TextView zoneNumberText;
    TextView baseNumberText;
    EditText dateEdt;
    EditText timeEdt;
    CheckBox monitorStockCheck;
    CheckBox reminderCheck;
    CheckBox weatherCheck;
    LinearLayout monitorStockOptionsLayout;
    LinearLayout reminderOptionsLayout;
    LinearLayout weatherOptionsLayout;
    TextView initWeightText;
    EditText thresholdEdit;
    EditText reminderDesc;
    Spinner weatherTypeSpinner;
    Spinner operatorSpinner;
    Spinner repeatSpinner;
    EditText valueEdit;
    Button saveBtn;

    int baseId;
    int zoneId;
    float initWeight;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ((MainActivity) getActivity()).setActionBarTitle("Add Notification");
        rootView = inflater.inflate(R.layout.view_notif_add, container, false);

        // Get views
        zoneNumberText = (TextView)rootView.findViewById(R.id.zoneNumberText);
        baseNumberText = (TextView)rootView.findViewById(R.id.baseNumberText);
        dateEdt = (EditText)rootView.findViewById(R.id.dateEdt);
        timeEdt = (EditText)rootView.findViewById(R.id.timeEdt);
        monitorStockCheck = (CheckBox)rootView.findViewById(R.id.monitorStockCheck);
        reminderCheck = (CheckBox)rootView.findViewById(R.id.reminderCheck);
        weatherCheck = (CheckBox)rootView.findViewById(R.id.weatherCheck);
        monitorStockOptionsLayout = (LinearLayout)rootView.findViewById(R.id.monitorStockOptionsLayout);
        reminderOptionsLayout = (LinearLayout)rootView.findViewById(R.id.reminderOptionsLayout);
        weatherOptionsLayout = (LinearLayout)rootView.findViewById(R.id.weatherOptionsLayout);
        initWeightText = (TextView)rootView.findViewById(R.id.initWeightText);
        thresholdEdit = (EditText)rootView.findViewById(R.id.thresholdEdit);
        reminderDesc = (EditText)rootView.findViewById(R.id.reminderDesc);
        weatherTypeSpinner = (Spinner)rootView.findViewById(R.id.weatherTypeSpinner);
        operatorSpinner = (Spinner)rootView.findViewById(R.id.operatorSpinner);
        repeatSpinner = (Spinner)rootView.findViewById(R.id.repeatSpinner);
        valueEdit = (EditText)rootView.findViewById(R.id.valueEdit);
        saveBtn = (Button)rootView.findViewById(R.id.saveBtn);


        // Get arguments and set corresponding
        baseId = getArguments().getInt("baseId");
        zoneId = getArguments().getInt("zoneId");
        initWeight = getArguments().getFloat("initWeight");
        zoneNumberText.setText("Zone Number: " + zoneId);
        baseNumberText.setText("Base Number: " + baseId);
        initWeightText.setText("" + initWeight);

        // Set spinner content
        setSpinners();


        // Set checked values to false by default
        monitorStockOptionsLayout.setVisibility(View.GONE);
        reminderOptionsLayout.setVisibility(View.GONE);
        weatherOptionsLayout.setVisibility(View.GONE);

        setOnClickListenrs();
        setNotifType();

        return rootView;
    }


    private void setOnClickListenrs()
    {
        monitorStockCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (!isChecked)
                {
                    monitorStockOptionsLayout.setVisibility(View.GONE);
                } else if (isChecked)
                {
                    monitorStockOptionsLayout.setVisibility(View.VISIBLE);
                    reminderCheck.setChecked(false);
                    weatherCheck.setChecked(false);
                }
            }
        });

        reminderCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (!isChecked)
                {
                    reminderOptionsLayout.setVisibility(View.GONE);
                } else if (isChecked)
                {
                    reminderOptionsLayout.setVisibility(View.VISIBLE);
                    weatherCheck.setChecked(false);
                    monitorStockCheck.setChecked(false);
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
                    reminderCheck.setChecked(false);
                    monitorStockCheck.setChecked(false);
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String url;
                String notiftype = "";
                String checktype = "";
                String checkvalue = "";
                String description = "none";

                if (monitorStockCheck.isChecked())
                {
                    notiftype = "weight";
                    checktype = "weight";
                    checkvalue = "l" + thresholdEdit.getText().toString();

                    //newnotification/<int:baseid>/<int:zoneid>/<notiftype>/<checktype>/<checkvalue>/<description>/<pushflag>
                }
                else if (reminderCheck.isChecked())
                {
                    notiftype = "time";
                    checktype = repeatSpinner.getSelectedItem().toString();
                    checkvalue = "2016" + dateEdt.getText().toString() + timeEdt.getText().toString();
                    description = reminderDesc.getText().toString().replaceAll(" ", "%20");
                }
                else if (weatherCheck.isChecked())
                {
                    notiftype = "weather";
                    checktype = weatherTypeSpinner.getSelectedItem().toString().toLowerCase();
                    String operator = operatorSpinner.getSelectedItem().toString().toLowerCase();

                    if (operator.equals("less than"))
                    {
                        checkvalue += "l";
                    }
                    else if (operator.equals("greater than"))
                    {
                        checkvalue += "g";
                    }

                    checkvalue += valueEdit.getText().toString();

                }

                url = "newnotification/" + baseId + "/" + zoneId + "/" + notiftype + "/" + checktype + "/" + checkvalue + "/" + description + "/1";

                Log.d("URL", url);
                Utility.sendData(url);

                Toast.makeText(getActivity(), "Notification created", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSpinners()
    {

        List<String> weatherTypeArray =  new ArrayList<String>();
        weatherTypeArray.add("Temperature");
        weatherTypeArray.add("Forecast");
        weatherTypeArray.add("UV");
        ArrayAdapter<String> weatherAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, weatherTypeArray);
        weatherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weatherTypeSpinner.setAdapter(weatherAdapter);

        List<String> operatorArray =  new ArrayList<String>();
        operatorArray.add("Less Than");
        operatorArray.add("Greater Than");
        ArrayAdapter<String> operatorAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, operatorArray);
        operatorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        operatorSpinner.setAdapter(operatorAdapter);

        List<String> repeatArray =  new ArrayList<String>();
        repeatArray.add("repeatonce");
        repeatArray.add("repeatdaily");
        repeatArray.add("repeatweekly");
        repeatArray.add("repeatmonthly");
        ArrayAdapter<String> repeatAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, repeatArray);
        repeatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeatSpinner.setAdapter(repeatAdapter);

        /* Get spinner item
        String selected = sItems.getSelectedItem().toString();
        if (selected.equals("what ever the option was")) {
        }
        */
    }

    private void setNotifType()
    {
        String type = getArguments().getString("notifType");

        if (type.equals("weight"))
        {
            monitorStockCheck.setChecked(true);
        }
        else if (type.equals("reminder"))
        {
            reminderCheck.setChecked(true);
        }
        else if (type.equals("weather"))
        {
            weatherCheck.setChecked(true);
        }
    }
}
