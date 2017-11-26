package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by robertomoreno on 6/5/17.
 */

public class EarthquakeAdapter extends ArrayAdapter<Quake> {


    private static final String LOG_TAG = EarthquakeAdapter.class.getSimpleName();

    public EarthquakeAdapter(Activity context, ArrayList<Quake> quake) {
        super(context, 0, quake);

    }


    String mLocation;

    String sLocation;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.quake_list_item, parent, false);
        }

        Quake currentArrayQuake = getItem(position);

        TextView magnitude = (TextView) listItemView.findViewById(R.id.magnitude_view);

        DecimalFormat decimalFormat= new DecimalFormat("0.0");

        String outputMagnitude = decimalFormat.format(currentArrayQuake.getMagnitude());

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitude.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentArrayQuake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        magnitude.setText(outputMagnitude);

        //Get location from the current Quake object of the arrayList
        String completeLocation = currentArrayQuake.getCity();

        //Separe the location into two different strings
       separeLocation(completeLocation);

        //Set locations into the TextViews

        TextView nearCity = (TextView) listItemView.findViewById(R.id.location_view);

        nearCity.setText(sLocation);


        TextView city = (TextView) listItemView.findViewById((R.id.city_view));

        city.setText(mLocation);




        TextView date = (TextView) listItemView.findViewById((R.id.date_view));

        date.setText(currentArrayQuake.getDate());


        return listItemView;

    }

    private void separeLocation(String location){

        CharSequence of = "of";

        if(location.contains(of)){

            String[] adress = location.split("of");

            String secondLocation = adress[0];

            secondLocation=secondLocation + " of";

            this.sLocation=secondLocation;

            String mainLocation = adress[1];

            this.mLocation=mainLocation;

        }else{

            String mainLocation = location;
            this.mLocation=mainLocation;

            String secondLocation = "Near to";
            this.sLocation=secondLocation;
        }

    }

    private int getMagnitudeColor(double mag){

        int magRetunrId;

        int magnitudeNoDecimal = (int) Math.floor(mag);

        switch (magnitudeNoDecimal){

            case 0:
            case 1:
               magRetunrId = R.color.magnitude1;
                break;
            case 2:
                magRetunrId = R.color.magnitude2;
                break;
            case 3:
                magRetunrId = R.color.magnitude3;
                break;
            case 4:
                magRetunrId = R.color.magnitude4;
                break;
            case 5:
                magRetunrId = R.color.magnitude5;
                break;
            case 6:
                magRetunrId = R.color.magnitude6;
                break;
            case 7:
                magRetunrId = R.color.magnitude7;
                break;
            case 8:
                magRetunrId = R.color.magnitude8;
                break;
            case 9:
                magRetunrId = R.color.magnitude9;
                break;
            default:
                magRetunrId = R.color.magnitude10plus;
                break;


        }

        return ContextCompat.getColor(getContext(), magRetunrId);
    }
}
