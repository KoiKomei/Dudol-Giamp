package com.ilmale.doodlejump.domain;

import android.util.Log;

public class Player extends AbstractGameObject {

    private static final String LOG_TAG = Player.class.getSimpleName();

    private Item item;
    private boolean hasObject;

    //current position of the player
    private float pX;
    private float pY;

    //current speed and acceleration of the player
    private float velX;
    private float accX;
    private float velY;
    private float accY;

    public Player(){
        super();
        pX = 450;
        pY = 400;
    }

    public void pickObject(Item item){
        this.item = item;
        this.hasObject = true;
    }

    public void loseObject(){
        this.item = null;
        this.hasObject = false;
    }

//    @Override
//    public void update(){
//        velX += (accX);//* frameTime);
//
//        float xS = (velX / 2);// * frameTime;
//
//        pX -= xS;
//
//        if (pX > 95) {
//            pX = -5;
//        } else if (pX < -6) {
//            pX = 94;
//        }
//    }

    @Override
    public void update() {
        Log.d(LOG_TAG, "Updating Player  " + accX + "  " + accY + "  " + pX + "  " + pY);

        float frameTime = 0.666f;
        velX += (accX * frameTime * 6);
        velY += (accY * frameTime * 6);

        if (velX > 200) velX = 200;
        else if (velX < -200) velX = -200;
        if (velY > 200) velY = 200;
        else if (velY < -200) velY = -200;

        float xS = (velX / 2) * frameTime;
        float yS = (velY / 2) * frameTime;

        pX -= xS;
        pY -= yS;

        if (pX > 980) {
            pX = -100;
        } else if (pX < -105) {
            pX = 975;
        }

        if (pY < 800) {
            pY = 800;
        } else if (pY > 1500) {
            pY = 1500;
        }
    }

    public float getAccX(){
        return accX;
    }

    public void setAccX(float accX){this.accX = accX;}

    public float getVelX(){ return velX; }

    public void setVelX(float velX){
        this.velX = velX;
    }

    public float getVelY() { return velY; }

    public void setVelY(float velY) { this.velY = velY; }

    public float getAccY() { return accY; }

    public void setAccY(float accY) { this.accY = accY; }

    public void setpX(float pX){
        this.pX = pX;
    }

    public float getpX(){
        return pX;
    }

    public float getpY(){
        return pY;
    }

    public void setpY(float pY){ this.pY = pY; }

    public Item getItem() {
        return item;
    }

    public boolean hasObject() {
        return hasObject;
    }

}
