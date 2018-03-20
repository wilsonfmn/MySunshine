package com.example.android.mysunshine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by willf on 20/03/2018.
 */

public class ForecastFragment extends Fragment {

    public ForecastFragment() {
        // commit using android studio
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        // Com inflate eu consigo montar a hierarquia de layouts
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        List<String> fakeData = new ArrayList<>();
        fakeData.add("Today-Sunny-88/63");
        fakeData.add("Tomorrow-Foggy-70/46");
        fakeData.add("Weds-Cloudy-72/63");
        fakeData.add("Thurs-Rainy-64/51");
        fakeData.add("Fri-Foggy-70/46");
        fakeData.add("Sat-Sunny-76/68");

        // Criando a lista de item
        ListAdapter listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_textview, fakeData);
        // Consigo pegar diretamente do rootView, tornando a busca menor
        ListView forecastView = rootView.findViewById(R.id.listview_forecast);
        forecastView.setAdapter(listAdapter);

        return rootView;
    }

    public class FetchWeatherTask extends AsyncTask<Void, Void, Void> {

        private final String LOG_TAG =  FetchWeatherTask.class.getSimpleName();

        @Override
        protected Void doInBackground(Void... voids) {

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=05588000,br&mode=json&units=metric&cnt=7&appid=b06972a13aa30297eb847b93d248836d");

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            //return forecastJsonStr;
            return null;
        }

    }

}
