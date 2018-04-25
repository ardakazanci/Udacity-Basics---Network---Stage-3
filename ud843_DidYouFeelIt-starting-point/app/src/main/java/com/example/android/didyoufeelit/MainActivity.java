/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.didyoufeelit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.net.URL;

/**
 * Displays the perceived strength of a single earthquake event based on responses from people who
 * felt the earthquake.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * URL for earthquake data from the USGS dataset
     */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-05-02&minfelt=50&minmagnitude=5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Perform the HTTP request for earthquake data and process the response.


        // Update the information displayed to the user.
        new ActivityAsyncTask().execute(USGS_REQUEST_URL);
    }

    /**
     * Update the UI with the given earthquake information.
     */


    private class ActivityAsyncTask extends AsyncTask<String, Void, Event> {


        @Override
        protected Event doInBackground(String... strings) {

            // Boş kontrolü sağlanmalı.
            if (strings[0] == null || strings.length < 1) {

                return null;

            }

            String url = strings[0];
            Event earthquake = Utils.fetchEarthquakeData(url);
            return earthquake;
        }

        @Override
        protected void onPostExecute(Event event) {
            super.onPostExecute(event);

            // Boş nesne kontrolü sağlanmalı 
            if (event == null) {
                return;

            }
            TextView titleTextView = (TextView) findViewById(R.id.title);
            titleTextView.setText(event.title);

            TextView tsunamiTextView = (TextView) findViewById(R.id.number_of_people);
            tsunamiTextView.setText(getString(R.string.num_people_felt_it, event.numOfPeople));

            TextView magnitudeTextView = (TextView) findViewById(R.id.perceived_magnitude);
            magnitudeTextView.setText(event.perceivedStrength);

        }
    }


}
