package com.ilmale.doodlejump.domain;

public class Player extends AbstractGameObject {

    private Item item;
    private boolean hasObject;
    private float speed;
    private float acceleration;
    private float pX;
    private float pY;

    public Player(){
        acceleration = 10;
    }

    public void pickObject(Item item){
        this.item = item;
        this.hasObject=true;
    }

    public void loseObject(){
        this.item =null;
        this.hasObject=false;
    }

    @Override
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

    public Item getItem() {
        return item;
    }

    public boolean hasObject() {
        return hasObject;
    }

}
