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
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;


public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Quake>> {

    //Tag for the log messages */
    public static final String LOG_TAG = EarthquakeActivity.class.getSimpleName();

    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-05-02&minfelt=50&minmagnitude=5";


    private TextView emptyStateView;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);


        Log.v("initloader", "The oncreate has runned and loader has started");
        getLoaderManager().initLoader(0, null, this).forceLoad();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateUI(final ArrayList<Quake> quakeArrayList) {


        EarthquakeAdapter adapter = null;
        adapter = new EarthquakeAdapter(
                this, quakeArrayList);


        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);


        //Throws error screen in case adapter is empty
        earthquakeListView.setEmptyView(emptyStateView);


        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);


        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                ArrayList<Quake> newQuakeArrayList = new ArrayList<Quake>(quakeArrayList);

                Quake currentQuakeToIntent = newQuakeArrayList.get(i);

                String url = currentQuakeToIntent.getUrl();

                Intent goLink = new Intent();

                goLink.setData(Uri.parse(url));

                goLink.setAction(Intent.ACTION_WEB_SEARCH);

                startActivity(goLink);


            }
        });


    }

    @Override
    public Loader<ArrayList<Quake>> onCreateLoader(int i, Bundle bundle) {


        Log.v("onCreateLoader", "onCreate loader running loader should be created");

        EarthquakeLoader loader = new EarthquakeLoader(this);


        return loader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Quake>> loader, ArrayList<Quake> earthquakes) {

        Log.v("onLoadFinished", "update of the UUI should start and create de interface");

        //Inflates emptyStateView in order to not apperar in screen until loading has finished
        emptyStateView = (TextView) findViewById(R.id.empty_state);

        //Saves ProgresBar view into an object in order to treat it from java
        progressBar = (ProgressBar) findViewById(R.id.loading_spinner);

        //Makes progerss bar disappear when the loader is loaded
        progressBar.setVisibility(View.GONE);

        updateUI(earthquakes);


    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Quake>> loader) {

        Log.v("onLoaderReset", "Create a new arraylist");
        updateUI(new ArrayList<Quake>());


    }
}




