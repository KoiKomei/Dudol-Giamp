package com.ilmale.doodlejump.ChainScore;

import android.content.Context;

public class HandlerScore100 extends HandlerScore{

    private static final HandlerScore100 ourInstance = new HandlerScore100();

    public static HandlerScore100 getInstance() {
        return ourInstance;
    }

    public HandlerScore100() {
        successor=HandlerScore30.getInstance();
    }

    @Override
    protected int getAllowable() {
        return 100;
    }

    @Override
    public String getString() {
        return "Your are the best of your region";
    }

}
