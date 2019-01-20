package com.ilmale.doodlejump.engine;

public class Player {

    private Object object;
    private boolean hasObject;
    private float speed;
    private float acceleration;
    private float pX;
    private float pY;

    public Player(){
        acceleration = 10;
    }

    public void pickObject(Object object){
        this.object=object;
    }

    public void update(){

    }

    public float getAcceleration(){
        return acceleration;
    }

    public float getSpeed(){
        return speed;
    }

    public void setSpeed(float speed){
        this.speed=speed;
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

    public Object getObject() {
        return object;
    }

    public boolean hasObject() {
        return hasObject;
    }
}
