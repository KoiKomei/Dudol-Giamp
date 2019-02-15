package com.ilmale.doodlejump.domain;

import com.ilmale.doodlejump.Utility;

public class Jetpack extends AbstractGameObject {

    private float pX;
    private float pY;
    private float yS=0;
    private int duration = 5;

    private Utility utility = Utility.getInstance();

    public Jetpack(){
        pX = (float)Math.random() * (utility.getPixelWidth()-52);
        pY = -5000;
    }

    public Jetpack(float pX, float pY){
        this.pX=pX;
        this.pY=pY;
    }

    public void setpX(float pX){
        this.pX=pX;
    }

    public float getpX(){
        return pX;
    }

    public float getpY(){
        return pY;
    }

    public void setpY(float pY){
        this.pY=pY;
    }

    public float getyS() {
        return yS;
    }

    public void setyS(float yS) {
        this.yS = yS;
    }

    public int getDuration(){
        return duration;
    }

    @Override
    public void update() {
        pY -= yS;
        if(pY > utility.getPixelHeight()){
            pX = (float) (Math.random() * (utility.getPixelWidth()-52));
            pY = -5000;
        }
    }

    public void replace(){
        pX = (float) (Math.random() * (utility.getPixelWidth()-52));
        pY = -15000;
    }
}
