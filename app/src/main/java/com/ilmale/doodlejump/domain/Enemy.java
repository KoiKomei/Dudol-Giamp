package com.ilmale.doodlejump.domain;

import com.ilmale.doodlejump.Constants;

public class Enemy extends AbstractGameObject {

    private float pX;
    private float pY;
    private float yS=0;

    private Constants constants = Constants.getInstance();

    public Enemy(){}

    public Enemy(float pX, float pY){
        this.pX=pX;
        this.pY=pY;
        pX = (float)Math.random() * constants.getPixelWidth();
        pY = -300;
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
        if(pY > constants.getPixelHeight()){
            pX = (float) (Math.random() * constants.getPixelWidth());
            pY = -300;
        }
    }

    public void replace(){
        pX = (float) (Math.random() * constants.getPixelWidth());
        pY = -300;
    }
}
