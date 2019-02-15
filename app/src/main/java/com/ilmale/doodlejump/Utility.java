package com.ilmale.doodlejump;

//class of variables and utility used to set:
//dimension of the screen,
//points and name of the player,
//check who win in multiplayer,
//avoid multiple requests to activate location service
//and three utility for position request

public class Utility {

    public static final int  ERROR_DIALOG_REQUEST = 9001;
    public static final int  PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9002;
    public static final int  PERMISSIONS_REQUEST_ENABLE_GPS = 9003;

    private float pixelWidth=0;
    private float pixelHeight=0;
    private int points = 0;
    private String name = null;
    private boolean askedPosition = false;
    private int contAlert = 0;
    private boolean loseInMulti = false;

    private static final Utility ourInstance = new Utility();

    public static Utility getInstance() {
        return ourInstance;
    }

    public Utility() {}

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAskedPosition() {
        return askedPosition;
    }

    public void setAskedPosition(boolean askedPosition) {
        this.askedPosition = askedPosition;
    }

    public int getContAlert() {
        return contAlert;
    }

    public void setContAlert(int contAlert) {
        this.contAlert = contAlert;
    }

    public boolean isLoseInMulti() {
        return loseInMulti;
    }

    public void setLoseInMulti(boolean loseInMulti) {
        this.loseInMulti = loseInMulti;
    }

}
