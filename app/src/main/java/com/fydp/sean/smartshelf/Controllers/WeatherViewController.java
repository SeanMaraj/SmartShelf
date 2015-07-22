package com.fydp.sean.smartshelf.Controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fydp.sean.smartshelf.DataFetcher;
import com.fydp.sean.smartshelf.R;

import java.util.concurrent.ExecutionException;

/**
 * Created by Sean on 2015-07-12.
 */
public class WeatherViewController extends Fragment {

    View rootView = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.view_weather, container, false);

        getData();

        return rootView;
    }

    private void getData() {

        TextView view = (TextView)rootView.findViewById(R.id.weatherText);
        String result = "";
        String login_url = "http://10.0.2.2/webapp/login.php";

        DataFetcher df = new DataFetcher();

        try {
            result = df.execute(login_url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        view.setText(result);
    }
}
