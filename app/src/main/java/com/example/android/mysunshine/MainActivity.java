package com.example.android.mysunshine;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is empty
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml
        int id = item.getItemId();

        // Noinspection SimplifiableIfStatement
        if(id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
            // commit using android studio
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            List<String> fakeData = new ArrayList<>();
            fakeData.add("Today-Sunny-88/63");
            fakeData.add("Tomorrow-Foggy-70/46");
            fakeData.add("Weds-Cloudy-72/63");
            fakeData.add("Thurs-Rainy-64/51");
            fakeData.add("Fri-Foggy-70/46");
            fakeData.add("Sat-Sunny-76/68");

            ListAdapter listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_textview, fakeData);
            // Consigo pegar diretamente do rootView, tornando a busca menor
            ListView forecastView = rootView.findViewById(R.id.listview_forecast);
            forecastView.setAdapter(listAdapter);

            return rootView;
        }
    }
}
