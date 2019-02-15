package com.ilmale.doodlejump.domain;

import com.ilmale.doodlejump.Utility;

public class Bullet extends AbstractGameObject {

    private float pX;
    private float pY;
    private float velY=100;

    public Bullet(float pX, float pY) {
        this.pX = pX;
        this.pY = pY;
    }

    public float getpX() {
        return pX;
    }

    public void setpX(float pX) {
        this.pX = pX;
    }

    public float getpY() {
        return pY;
    }

    public void setpY(float pY) {
        this.pY = pY;
    }

    @Override
    public void update() {
        float yB = ( velY / 2);
        setpY(pY - yB);
    }

}
