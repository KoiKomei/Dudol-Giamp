package com.ilmale.doodlejump.domain;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

//this is a class used to keep location of the device

public class MyLocation{

    private LatLng latLng;

    private static final MyLocation ourInstance = new MyLocation();

    public static MyLocation getInstance() {
        return ourInstance;
    }

    private MyLocation() { }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

}
