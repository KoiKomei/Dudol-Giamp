package com.ilmale.doodlejump.domain;

public class Player extends AbstractGameObject {

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
        pX = 45;
        pY = 40;
    }

    public void pickObject(Item item){
        this.item = item;
        this.hasObject = true;
    }

    public void loseObject(){
        this.item = null;
        this.hasObject = false;
    }

    @Override
    public void update(){
        velX += (accX);//* frameTime);

        float xS = (velX / 2);// * frameTime;

        pX -= xS;

        if (pX > 95) {
            pX = -5;
        } else if (pX < -6) {
            pX = 94;
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
