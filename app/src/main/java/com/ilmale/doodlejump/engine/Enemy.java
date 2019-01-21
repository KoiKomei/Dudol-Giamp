package com.ilmale.doodlejump.engine;

public class Enemy {

    private float pX;
    private float pY;

    public Enemy(){}

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
}
