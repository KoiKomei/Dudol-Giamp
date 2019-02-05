package com.ilmale.doodlejump.ChainScore;

import android.content.Context;

public class HandlerScore1 extends HandlerScore{

    public HandlerScore1(Context context) {
        super(context);
    }

    @Override
    protected int getAllowable() {
        return 1;
    }

    @Override
    public String getString() {
        return "Your are the best of your street";
    }

}
