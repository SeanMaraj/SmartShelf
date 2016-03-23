package com.fydp.sean.smartshelf.Controllers;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.fydp.sean.smartshelf.Dialogs.SelectDateFragment;
import com.fydp.sean.smartshelf.Helpers.Utility;
import com.fydp.sean.smartshelf.MainActivity;
import com.fydp.sean.smartshelf.R;

import java.util.ArrayList;
import java.util.Calendar;
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
        Utility.setCurrentFragment("addNotifFragment");
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

        // Set default date and time
        setDateAndTime();

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
                String description = "";

                if (monitorStockCheck.isChecked())
                {
                    notiftype = "weight";
                    checktype = "weight";
                    checkvalue = "l" + thresholdEdit.getText().toString();

                    //newnotification/<int:baseid>/<int:zoneid>/<notiftype>/<checktype>/<checkvalue>/<description>/<pushflag>
                } else if (reminderCheck.isChecked())
                {
                    notiftype = "time";
                    checktype = getRepeatValue(repeatSpinner.getSelectedItem().toString());
                    String[] date = dateEdt.getText().toString().split("/");
                    String[] time = timeEdt.getText().toString().split(":");
                    checkvalue = date[2] + date[0] + date[1] + time[0] + time[1];
                    description = reminderDesc.getText().toString().replaceAll(" ", "%20");

                } else if (weatherCheck.isChecked())
                {
                    notiftype = "weather";
                    checktype = weatherTypeSpinner.getSelectedItem().toString().toLowerCase();
                    String operator = operatorSpinner.getSelectedItem().toString().toLowerCase();

                    if (operator.equals("less than"))
                    {
                        checkvalue += "l";
                    } else if (operator.equals("greater than"))
                    {
                        checkvalue += "g";
                    }

                    checkvalue += valueEdit.getText().toString();

                }

                if (description.equals(""))
                {
                    description = "none";
                }

                url = "newnotification/" + baseId + "/" + zoneId + "/" + notiftype + "/" + checktype + "/" + checkvalue + "/" + description + "/1";

                Log.d("URL", url);
                Utility.sendData(url);

                Toast.makeText(getActivity(), "Notification created", Toast.LENGTH_SHORT).show();
            }
        });

        dateEdt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("LOG", "DATE");
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        timeEdt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("LOG", "TIME");

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeEdt.setText( String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
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
        repeatArray.add("Remind Once");
        repeatArray.add("Remind Daily");
        repeatArray.add("Remind Weekly");
        repeatArray.add("Remind Monthly");
        ArrayAdapter<String> repeatAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, repeatArray);
        repeatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeatSpinner.setAdapter(repeatAdapter);
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

    private void setDateAndTime()
    {
        // Date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = String.format("%02d", month) + "/" + String.format("%02d", day) + "/" + String.format("%02d", year);
        dateEdt.setText(date);

        // Time
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        timeEdt.setText( String.format("%02d", hour) + ":" + String.format("%02d", minute));

    }

    private String getRepeatValue(String value)
    {

        switch (value)
        {
            case "Remind Once":
                return "repeatonce";
            case "Remind Daily":
                return "repeatdaily";
            case "Remind Weekly":
                return "repeatweekly";
            case "Remind Monthly":
                return "repeatmonthly";
            default:
                return "repeatonce";
        }
    }
}
