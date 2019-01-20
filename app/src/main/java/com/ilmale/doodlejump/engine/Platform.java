package com.ilmale.doodlejump.engine;

public class Platform {

    private float pX;
    private float pY;
    private float lenght;

    public Platform(float pX, float pY){
        this.pX=pX;
        this.pY=pY;
        this.lenght=100;
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

    public float getLenght(){
        return lenght;
    }
}
