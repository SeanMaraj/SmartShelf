package com.fydp.sean.smartshelf;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Sean on 2015-07-12.
 */
public class Test extends Fragment{
    View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.test1_layout, container, false);

        changeButton();
        return rootView;
    }

    private void changeButton()
    {
        Button b = (Button)rootView.findViewById(R.id.button);
        b.setText("hello world");
    }

}



