package com.ilmale.doodlejump.domain;

import android.util.Log;

import com.ilmale.doodlejump.Constants;
import com.ilmale.doodlejump.view.GameView;

import java.util.List;

public class Platform extends AbstractGameObject {

    private static final String LOG_TAG = GameView.class.getSimpleName();

    private float pX;
    private float pY;
    private float yS=0;
    private int maxPlat=10;
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

    public void setyS(float yS) {
        this.yS = yS;
    }

    public boolean hasSprings() {
        return hasSprings;
    }

    public void setHasSprings(boolean hasSprings) {
        this.hasSprings = hasSprings;
    }

    @Override
    public void update() {
        pY -= yS;
        if(pY > (constants.getPixelHeight()-50)){
            //constants.setPoints(constants.getPoints() - (int)yS);
            pX = (float) (Math.random() * (constants.getPixelWidth()-114));
            pY = 0;
            setHasSprings(false);
            float j = (float) Math.random()*100;
            if(j>=97){
                setHasSprings(true);
            }
        }
    }

    public void createRandomPlatform(List<Platform> platforms){
        for (int i=0; i<maxPlat; i++){
            float j = (float) Math.random()*100;
            Platform platform = new Platform((float)Math.random() * (constants.getPixelWidth()-134),i*((constants.getPixelHeight()-50)/10));
            if(j>=97){
                platform.setHasSprings(true);
            }
            platforms.add(platform);
        }
    }

}