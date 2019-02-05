package com.ilmale.doodlejump.ChainScore;

import android.content.Context;

public class HandlerScore30 extends HandlerScore{

    public HandlerScore30(Context context) {
        super(context);
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
