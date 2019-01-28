package com.ilmale.doodlejump.domain;

import com.ilmale.doodlejump.Constants;

public class Jetpack extends AbstractGameObject {

    private float pX;
    private float pY;
    private float yS=0;

    private Constants constants = Constants.getInstance();

    public Jetpack(){
        pX = (float)Math.random() * (constants.getPixelWidth()-52);
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

    @Override
    public void update() {
        pY -= yS;
        if(pY > constants.getPixelHeight()){
            pX = (float) (Math.random() * (constants.getPixelWidth()-52));
            pY = -300;
        }
    }

    public void replace(Jetpack jetpack){
        pX = (float) (Math.random() * (constants.getPixelWidth()-52));
        pY = -5000;
    }
}
