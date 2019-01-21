package com.ilmale.doodlejump.mammata;

public class Platform extends AbstractGameObject {

    private float pX;
    private float pY;
    private boolean hasSprings;

    public Platform(float pX, float pY){
        this.pX=pX;
        this.pY=pY;
        this.hasSprings=false;
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

    public boolean hasSprings() {
        return hasSprings;
    }

    public void setHasSprings(boolean hasSprings) {
        this.hasSprings = hasSprings;
    }

    @Override
    public void update() {

    }
}
