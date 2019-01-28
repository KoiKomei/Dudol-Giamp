package com.ilmale.doodlejump.domain;

import com.ilmale.doodlejump.Constants;

public class Item extends AbstractGameObject {

    private float pX;
    private float pY;
    private float timeShield;

    private EnumItemType type;
    private Constants constants = Constants.getInstance();

    public Item(){}

    public Item(EnumItemType type, float pX, float pY){
        this.pX=pX;
        this.pY=pY;
        this.type=type;
    }

    public void setType(EnumItemType type){
        this.type = type;
    }

    public EnumItemType getType() {
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

    @Override
    public void update() {

    }

    public void createRandomItem(Item item){
        item.setpX((float)Math.random() * constants.getPixelWidth());
        item.setpY(-300);

    }
}
