package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.android.quakereport.EarthquakeActivity.LOG_TAG;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {


    private QueryUtils() {
    }



    public static URL createURL(String stringUrl){

        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(LOG_TAG,"Error with the URL ", e);
            return null;
        }

        return url;

    }

    public static String makeHttpRequest(URL url) throws IOException {

        String jSonResponse = "";

        if (url == null) {



            return jSonResponse;
        }


        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = null;
            inputStream = null;

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {

                inputStream = urlConnection.getInputStream();
                jSonResponse = readFromStream(inputStream);

                Log.v("urlConnection","succesful Http connection");

            } else {

                String stringErrorConection = String.valueOf(urlConnection.getResponseCode());

                Log.e(LOG_TAG, "Error to set Http conection: " + stringErrorConection);

            }

        } catch (IOException e) {

            Log.e(LOG_TAG, "EIOException" + e);

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();

                Log.v("urlConnection", "disconnected");
            }

            if (inputStream != null) {

                inputStream.close();
                Log.v("inputStream", "closed");
            }
        }


        return jSonResponse;
    }


    public static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder();

        if(inputStream!=null){

            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(streamReader);
            String line = reader.readLine();
            while(line!=null){

                output.append(line);
                line = reader.readLine();
            }
        }

        String jSonOutput = String.valueOf(output);

        Log.v("readFromStream","jSon has been readed and added into a String");

        return jSonOutput;


    }

    public static ArrayList<Quake> extractEarthquakes(String jSonString) throws JSONException {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Quake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.


        Log.v("QueryUtils","The JSON response was: "+jSonString);

        if(jSonString != null) {

            JSONObject jsonObject = new JSONObject(jSonString);
            JSONArray features = jsonObject.getJSONArray("features");

            for (int i = 0; i < features.length(); i++) {

                JSONObject feature = features.getJSONObject(i);
                JSONObject propertie = feature.getJSONObject("properties");
                double mag = propertie.getDouble("mag");
                String place = propertie.getString("place");
                long time = propertie.getLong("time");
                String url = propertie.getString("url");

                //Format the time long data type into a dateObject
                Date date = new Date(time);

                String finalDate = fomatToDate(date);

                Quake quake = new Quake(mag, place, finalDate, url);

                earthquakes.add(quake);

            }
        }

        // Return the list of earthquakes
        return earthquakes;
    }

    //Method to convert the Date type into the specific format date
    private static String fomatToDate(Date iDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM DD, yyyy");
        String dateToDisplay = dateFormat.format(iDate);
        return dateToDisplay;
    }






}