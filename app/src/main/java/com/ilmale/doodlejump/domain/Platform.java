package com.ilmale.doodlejump.domain;

import com.ilmale.doodlejump.Constants;

import java.util.List;

public class Platform extends AbstractGameObject {

    private float pX;
    private float pY;
    private boolean hasSprings;
    private Constants constants = Constants.getInstance();

    public Platform(float pX, float pY){
        this.pX=pX;
        this.pY=pY;
        this.hasSprings=false;
    }

    public Platform() {}

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

    public void createRandomPlatform(List<Platform> platforms){
        for (int i=0; i<10; i++){
            int j = (int) Math.random()*10+1;
            Platform platform= new Platform(i*(float)Math.random() * (constants.getPixelWidth()-100),i*(constants.getPixelHeight()/10));
            if(j==5){
                platform.setHasSprings(true);
            }
            platforms.add(platform);
        }

    }

}
