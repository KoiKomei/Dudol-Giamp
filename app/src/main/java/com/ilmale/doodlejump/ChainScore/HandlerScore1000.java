package com.ilmale.doodlejump.ChainScore;

import android.content.Context;

public class HandlerScore1000 extends HandlerScore{

    public HandlerScore1000(Context context) {
        super(context);
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
