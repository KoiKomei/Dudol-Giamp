package com.ilmale.doodlejump.ChainScore;

import android.content.Context;

public class HandlerScore1 extends HandlerScore{

    private static final HandlerScore1 ourInstance = new HandlerScore1();

    public static HandlerScore1 getInstance() {
        return ourInstance;
    }

    public HandlerScore1() {}

    @Override
    protected int getAllowable() {
        return 1;
    }

    @Override
    public String getString() {
        return "Your are the best of your street";
    }

}
