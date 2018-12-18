package com.ilmale.doodlejump.engine;

public class Player {

    private boolean hasObject;
    private double speed;
    private double acceleration;

    protected Player(){
        hasObject = false;
    }

    public boolean pickObject(){
        if (hasObject) return false;
        else return true;
    }

    public void update(){

    }

}
