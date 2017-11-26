package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by robertomoreno on 2/8/17.
 */



public class EarthquakeLoader extends AsyncTaskLoader {

    ArrayList<Quake> earthquakes = new ArrayList<>();

    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-05-02&minfelt=50&minmagnitude=5";


    public EarthquakeLoader(Context context) {
        super(context);

    }


   /*protected void onStartLoading() {
        forceLoad();
    }*/

    @Override
    public ArrayList<Quake> loadInBackground() {


        //This try and catch makes the loader wait for two seconds to star working
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        URL urlObject = QueryUtils.createURL(USGS_REQUEST_URL);

        String jSonResponse ="";
        try {
            jSonResponse = QueryUtils.makeHttpRequest(urlObject);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            earthquakes = QueryUtils.extractEarthquakes(jSonResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.v("loadInBackground","loadInBackGround is returning an array of earthquakes");
        return earthquakes;
    }
}
