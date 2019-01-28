package com.ilmale.doodlejump;

import com.google.android.gms.maps.model.LatLng;

public class Constants {

    public static final int  ERROR_DIALOG_REQUEST = 9001;
    public static final int  PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9002;
    public static final int  PERMISSIONS_REQUEST_ENABLE_GPS = 9003;

    private float pixelWidth=0;
    private float pixelHeight=0;
    private int points = 0;

    private static final Constants ourInstance = new Constants();

    public static Constants getInstance() {
        return ourInstance;
    }

    public Constants() {}

    public float getPixelWidth() {
        return pixelWidth;
    }

    public void setPixelWidth(float pixelWidth) {
        this.pixelWidth = pixelWidth;
    }

    public float getPixelHeight() {
        return pixelHeight;
    }

    public void setPixelHeight(float pixelHeight) {
        this.pixelHeight = pixelHeight;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
