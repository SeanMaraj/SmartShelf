package com.fydp.sean.smartshelf.Controllers;

import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fydp.sean.smartshelf.DataFetcher;
import com.fydp.sean.smartshelf.Libraries.Utility;
import com.fydp.sean.smartshelf.R;

import org.w3c.dom.Text;

import java.util.concurrent.ExecutionException;

/**
 * Created by Sean on 2015-07-23.
 */
public class ZoneEditController extends Fragment
{
    View rootView = null;
    CheckBox stockCheck;
    LinearLayout setWeightLayout;
    TextView zoneNameEdit;
    TextView zoneNumberText;
    TextView baseNumberText;
    EditText weightEdit;
    Button applyButton;
    ImageView editNameImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.view_zoneedit, container, false);

        setWeightLayout = (LinearLayout) rootView.findViewById(R.id.monitorStockOptionsLayout);
        zoneNameEdit = (TextView) rootView.findViewById(R.id.zoneNameText);
        zoneNumberText = (TextView) rootView.findViewById(R.id.zoneNumberText);
        baseNumberText = (TextView) rootView.findViewById(R.id.baseNumberText);
        weightEdit = (EditText) rootView.findViewById(R.id.initWeightEdit);
        applyButton = (Button) rootView.findViewById((R.id.applyBtn));
        stockCheck = (CheckBox) rootView.findViewById(R.id.monitorStockCheck);
        editNameImage = (ImageView) rootView.findViewById(R.id.editNameImage);

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

        applyButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onApplyButton(v);

            }
        });

        editNameImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                editName(v);
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
