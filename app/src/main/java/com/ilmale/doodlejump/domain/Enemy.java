package com.ilmale.doodlejump.domain;

import com.ilmale.doodlejump.Utility;

public class Enemy extends AbstractGameObject {

    private float pX;
    private float pY;
    private float yS=0;

    private Utility utility = Utility.getInstance();

    public Enemy(){
        pX = (float)Math.random() * (utility.getPixelWidth()-76);
        pY = -2000;
    }

    public Enemy(float pX, float pY){
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

    @Override
    public void update() {
        pY -= yS;
        if(pY > utility.getPixelHeight()){
            pX = (float) (Math.random() * utility.getPixelWidth());
            pY = -2000;
        }
    }

    public void replace(){
        pX = (float) (Math.random() * (utility.getPixelWidth()-76));
        pY = -2000;
    }
}
