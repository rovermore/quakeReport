package com.example.android.quakereport;

/**
 * Created by robertomoreno on 6/5/17.
 */

public class Quake {

    private double magnitude;

    private String city;

    private String date;

    private String url;


    public Quake(double s, String s1, String s2, String web) {

        this.magnitude = s;

        this.city = s1;

        this.date = s2;

        this.url = web;

    }

    public double getMagnitude(){

        return magnitude;
    }


    public String getCity(){

        return city;
    }



    public String getDate(){

        return date;
    }

    public String getUrl(){

        return url;
    }

    @Override
    public String toString() {
        return "Quake{" +
                "magnitude=" + magnitude +
                ", city='" + city + '\'' +
                ", date='" + date + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
