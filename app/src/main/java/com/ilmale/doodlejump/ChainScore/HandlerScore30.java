package com.ilmale.doodlejump.ChainScore;

import android.content.Context;

public class HandlerScore30 extends HandlerScore{

    private static final HandlerScore30 ourInstance = new HandlerScore30();

    public static HandlerScore30 getInstance() {
        return ourInstance;
    }

    public HandlerScore30() {
        successor=HandlerScore1.getInstance();
    }

    @Override
    protected int getAllowable() {
        return 30;
    }

    @Override
    public String getString() {
        return "Your are the best of your city";
    }

}
