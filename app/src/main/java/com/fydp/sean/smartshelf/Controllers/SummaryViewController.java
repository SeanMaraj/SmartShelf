package com.fydp.sean.smartshelf.Controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fydp.sean.smartshelf.Libraries.Utility;
import com.fydp.sean.smartshelf.OfflineData;
import com.fydp.sean.smartshelf.R;

/**
 * Created by Sean on 2015-07-12.
 */
public class SummaryViewController extends Fragment{

    View rootView = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.view_summary, container, false);

        setup();
        return rootView;
    }

    public void setup()
    {
        if (Utility.offlineMode())
        {
            Log.d("Log", "Creating offline data");
            OfflineData.create();
        }
    }
}
