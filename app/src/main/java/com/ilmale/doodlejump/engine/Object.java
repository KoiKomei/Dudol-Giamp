package com.ilmale.doodlejump.engine;

public class Object {

    private float pX;
    private float pY;
    private float timeShield;

    private ObjectType type;

    public Object(){}

    public Object(ObjectType type, float pX, float pY){
        this.pX=pX;
        this.pY=pY;
        this.type=type;
    }

    public void setType(ObjectType type){
        this.type = type;
    }

    public ObjectType getType() {
        return type;
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

    public float getTimeShield() {
        return timeShield;
    }

    public void setTimeShield(float timeShield) {
        this.timeShield = timeShield;
    }

}
