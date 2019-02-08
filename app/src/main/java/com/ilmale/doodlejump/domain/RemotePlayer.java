package com.ilmale.doodlejump.domain;

import com.ilmale.doodlejump.Constants;
import com.ilmale.doodlejump.network.Client;

import java.util.StringTokenizer;

public class RemotePlayer {

    public float pX = 0;
    public float pY = 0;
    public int x; //VARIABILE DI CONTRLLO PER IL BITMAP

    private Constants constants = Constants.getInstance();

    public void update(){



    }

    public void setpX(float pX) {
        this.pX = pX;
    }

    public void setpY(float pY) {
        this.pY = pY;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void receiveMessage(String s){

        StringTokenizer st = new StringTokenizer(s, " ", false);
        //java.lang.NumberFormatException: For input string: "Exit"
        //        at java.lang.FloatingDecimal.readJavaFormatString(FloatingDecimal.java:1306)
        //        at java.lang.Float.parseFloat(Float.java:459)
        String string = st.nextToken();
        if (!string.equals("Exit")){
            setpX(Float.parseFloat(string));
            setpY(Float.parseFloat(st.nextToken()) + constants.getPoints());
        }
        //setX(Integer.parseInt(st.nextToken()));

    }

}
