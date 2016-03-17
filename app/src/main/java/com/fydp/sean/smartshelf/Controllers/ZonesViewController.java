package com.fydp.sean.smartshelf.Controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.fydp.sean.smartshelf.Adaptors.ZoneAdaptor;
import com.fydp.sean.smartshelf.Helpers.Utility;
import com.fydp.sean.smartshelf.MainActivity;
import com.fydp.sean.smartshelf.Models.ZoneModel;
import com.fydp.sean.smartshelf.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sean on 2015-07-12.
 */
public class ZonesViewController extends Fragment
{
    View rootView = null;

    CheckBox base1Check;
    CheckBox base2Check;
    CheckBox base3Check;
    LinearLayout base1ListLayout;
    LinearLayout base2ListLayout;
    LinearLayout base3ListLayout;
    ListView base1List;
    ListView base2List;
    ListView base3List;

    ZoneAdaptor base1Adaptor;
    ZoneAdaptor base2Adaptor;
    ZoneAdaptor base3Adaptor;
    ArrayList<ZoneModel> base1Zones = new ArrayList<ZoneModel>();
    ArrayList<ZoneModel> base2Zones = new ArrayList<ZoneModel>();
    ArrayList<ZoneModel> base3Zones = new ArrayList<ZoneModel>();
    int[] activeBases = new int[3];


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ((MainActivity) getActivity()).setActionBarTitle("Zone Stock Levels");
        rootView = inflater.inflate(R.layout.view_zones, container, false);


        base1Check = (CheckBox)rootView.findViewById(R.id.base1Check);
        base2Check = (CheckBox)rootView.findViewById(R.id.base2Check);
        base3Check = (CheckBox)rootView.findViewById(R.id.base3Check);
        base1ListLayout = (LinearLayout)rootView.findViewById(R.id.base1ListLayout);
        base2ListLayout = (LinearLayout)rootView.findViewById(R.id.base2ListLayout);
        base3ListLayout = (LinearLayout)rootView.findViewById(R.id.base3ListLayout);

        base2List = (ListView)rootView.findViewById(R.id.base2List);
        base3List = (ListView)rootView.findViewById(R.id.base3List);

        // Setup base 1 list
        base1List = (ListView)rootView.findViewById(R.id.base1List);
        base1Adaptor = new ZoneAdaptor(getActivity(), R.layout.subview_zone);
        base1List.setAdapter(base1Adaptor);
        base1Zones.clear();

        // Setup base 2 list
        base2List = (ListView)rootView.findViewById(R.id.base2List);
        base2Adaptor = new ZoneAdaptor(getActivity(), R.layout.subview_zone);
        base2List.setAdapter(base2Adaptor);
        base2Zones.clear();

        // Setup base 3 list
        base3List = (ListView)rootView.findViewById(R.id.base3List);
        base3Adaptor = new ZoneAdaptor(getActivity(), R.layout.subview_zone);
        base3List.setAdapter(base3Adaptor);
        base3Zones.clear();


        getData();
        setOnItemClick();
        populateList();
        setBaseChecks();

        return rootView;
    }

    private void populateList()
    {
        Log.d("Log", "Populating list");
        base1Adaptor.clear();
        base2Adaptor.clear();
        base3Adaptor.clear();
        base1Adaptor.notifyDataSetChanged();
        base2Adaptor.notifyDataSetChanged();
        base3Adaptor.notifyDataSetChanged();

        for (int i = 0; i < base1Zones.size(); i++)
        {
            base1Adaptor.add(base1Zones.get(i));
        }

        for (int i = 0; i < base2Zones.size(); i++)
        {
            base2Adaptor.add(base2Zones.get(i));
        }

        for (int i = 0; i < base3Zones.size(); i++)
        {
            base3Adaptor.add(base3Zones.get(i));
        }
    }

    private void setOnItemClick()
    {
        Log.d("Log", "Setting item click");

        base1List.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Log.d("LOG", "Zone clicked: " + position);

                Fragment fragment = new ZoneDetailController();
                Bundle args = new Bundle();

                args.putString("zoneName", base1Zones.get(position).getDesc());
                args.putInt("zoneId", base1Zones.get(position).getZoneId());
                args.putInt("baseId", base1Zones.get(position).getBaseId());
                args.putFloat("initWeight", base1Zones.get(position).getInitialWeight());
                args.putFloat("currentWeight", base1Zones.get(position).getCurrentWeight());

                fragment.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        base2List.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Log.d("LOG", "Zone clicked: " + position);

                Fragment fragment = new ZoneDetailController();
                Bundle args = new Bundle();

                args.putString("zoneName", base2Zones.get(position).getDesc());
                args.putInt("zoneId", base2Zones.get(position).getZoneId());
                args.putInt("baseId", base2Zones.get(position).getBaseId());
                args.putFloat("initWeight", base2Zones.get(position).getInitialWeight());
                args.putFloat("currentWeight", base2Zones.get(position).getCurrentWeight());

                fragment.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        base3List.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Log.d("LOG", "Zone clicked: " + position);

                Fragment fragment = new ZoneDetailController();
                Bundle args = new Bundle();

                args.putString("zoneName", base3Zones.get(position).getDesc());
                args.putInt("zoneId", base3Zones.get(position).getZoneId());
                args.putInt("baseId", base3Zones.get(position).getBaseId());
                args.putFloat("initWeight", base3Zones.get(position).getInitialWeight());
                args.putFloat("currentWeight", base3Zones.get(position).getCurrentWeight());

                fragment.setArguments(args);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });


        base1Check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (!isChecked)
                {
                    base1ListLayout.setVisibility(View.GONE);
                    Utility.sendData("activatebase/1/0");
                } else if (isChecked)
                {
                    Utility.sendData("activatebase/1/1");
                    base1ListLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        base2Check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (!isChecked)
                {
                    base2ListLayout.setVisibility(View.GONE);
                    Utility.sendData("activatebase/2/0");
                } else if (isChecked)
                {
                    Utility.sendData("activatebase/2/1");
                    base2ListLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        base3Check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (!isChecked)
                {
                    base3ListLayout.setVisibility(View.GONE);
                    Utility.sendData("activatebase/3/0");
                } else if (isChecked)
                {
                    Utility.sendData("activatebase/3/1");
                    base3ListLayout.setVisibility(View.VISIBLE);
                }
            }
        });




    }

    private void getData()
    {
        parseResult(Utility.fetchData("getzones/1"), base1Zones);
        parseResult(Utility.fetchData("getzones/2"), base2Zones);
        parseResult(Utility.fetchData("getzones/3"), base3Zones);
        parseBases(Utility.fetchData("getbases"));
    }

    private void parseResult(String result, ArrayList<ZoneModel> list)
    {
        try
        {
            JSONArray JSONZones = new JSONArray(result);

            for (int i = 0; i < JSONZones.length(); i++)
            {
                JSONObject JSONZone = JSONZones.getJSONObject(i);
                ZoneModel zone = new ZoneModel(JSONZone.getInt("id"), JSONZone.getString("description"), (float)(JSONZone.getDouble("weight")), (float)(JSONZone.getDouble("initialweight")), 0, JSONZone.getInt("baseid"), JSONZone.getString("description"));
                list.add(zone);
            }

        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void parseBases(String result)
    {
        try
        {
            JSONArray a = new JSONArray(result);

            for (int i = 0; i < a.length(); i++)
            {
                JSONObject o = a.getJSONObject(i);
                activeBases[i] = o.getInt("active");;
            }

        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void setBaseChecks()
    {
        base1Check.setChecked( activeBases[0] == 0 ? false : true);
        base2Check.setChecked( activeBases[1] == 0 ? false : true);
        base3Check.setChecked( activeBases[2] == 0 ? false : true);
    }
}
