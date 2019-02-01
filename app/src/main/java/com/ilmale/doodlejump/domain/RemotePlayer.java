package com.ilmale.doodlejump.domain;

import java.util.StringTokenizer;

public class RemotePlayer {

    public float pX;
    public float pY;

    public void update(){



    }

    public void receiveMessage(String s){
        StringTokenizer st = new StringTokenizer(" ");
        String string = "";
        while (st.hasMoreTokens()){
            string = st.nextToken();

        }
    }

}
