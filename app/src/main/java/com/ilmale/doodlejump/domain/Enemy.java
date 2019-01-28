package com.ilmale.doodlejump.domain;

import com.ilmale.doodlejump.Constants;

public class Enemy extends AbstractGameObject {

    private float pX;
    private float pY;
    private Constants constants = Constants.getInstance();

    public Enemy(){}

    public Enemy(float pX, float pY){
        this.pX=pX;
        this.pY=pY;
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

    @Override
    public void update() {

    }

    public void createRandomEnemy(Enemy enemy){
        enemy.setpX((float)Math.random() * constants.getPixelWidth());
        enemy.setpY(-300);

    }
}
