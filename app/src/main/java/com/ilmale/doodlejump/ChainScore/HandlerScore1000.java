package com.ilmale.doodlejump.ChainScore;

import android.content.Context;

public class HandlerScore1000 extends HandlerScore{

    private static final HandlerScore1000 ourInstance = new HandlerScore1000();

    public static HandlerScore1000 getInstance() {
        return ourInstance;
    }

    public HandlerScore1000() {
        successor=HandlerScore100.getInstance();
    }

    @Override
    protected int getAllowable() {
        return 1000;
    }

    @Override
    public String getString() {
        return "Your are the best of your country";
    }

}
