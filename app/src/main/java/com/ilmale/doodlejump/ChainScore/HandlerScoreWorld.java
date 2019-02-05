package com.ilmale.doodlejump.ChainScore;

import android.content.Context;

public class HandlerScoreWorld extends HandlerScore{

    public HandlerScoreWorld(Context context) {
        super(context);
    }

    @Override
    protected int getAllowable() {
        return 100000;
    }

    @Override
    public String getString() {
        return "Your are the best of the world";
    }

}
