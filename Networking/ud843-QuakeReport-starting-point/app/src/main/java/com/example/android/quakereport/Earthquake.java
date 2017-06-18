package com.example.android.quakereport;

/**
 * Created by chira on 6/17/2017.
 */

public class Earthquake {
    double mag;
    String place,url;
    long timeInMilliSec;

    public Earthquake(double mag, String place, String url, long timeInMilliSec) {
        this.mag = mag;
        this.place = place;
        this.url = url;
        this.timeInMilliSec = timeInMilliSec;
    }

    public double getMag() {
        return mag;
    }

    public void setMag(double mag) {
        this.mag = mag;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTimeInMilliSec() {
        return timeInMilliSec;
    }

    public void setTimeInMilliSec(long timeInMilliSec) {
        this.timeInMilliSec = timeInMilliSec;
    }
}
