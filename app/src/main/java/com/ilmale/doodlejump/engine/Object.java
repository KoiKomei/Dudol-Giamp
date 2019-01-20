package com.ilmale.doodlejump.engine;

enum ObjectType{BULLET,HAT,JETPACK,SPRINGS};
public class Object {

    private float pX;
    private float pY;
    private float lenght;

    private ObjectType type;

    public Object(ObjectType type, float pX, float pY){
        this.pX=pX;
        this.pY=pY;
        this.lenght=100;
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

    public float getLenght(){
        return lenght;
    }


}
