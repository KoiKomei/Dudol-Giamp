package com.ilmale.doodlejump.ChainScore;

import android.content.Context;

public class HandlerScore100 extends HandlerScore{

    public HandlerScore100(Context context) {
        super(context);
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
