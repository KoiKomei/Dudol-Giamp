package com.ilmale.doodlejump.engine;

public class Player {

    private boolean hasObject;
    private float speed;
    private float acceleration;

    public Player(){
        hasObject = false;
        acceleration = 10;
    }

    public boolean pickObject(){
        return !hasObject;
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

}
