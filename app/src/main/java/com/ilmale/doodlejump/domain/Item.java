package com.ilmale.doodlejump.domain;

import com.ilmale.doodlejump.Constants;

public class Item extends AbstractGameObject {

    private float pX;
    private float pY;
    private float timeShield;
    private float yS=0;

    private EnumItemType type;
    private Constants constants = Constants.getInstance();

    public Item(){}

    public Item(EnumItemType type, float pX, float pY){
        this.pX=pX;
        this.pY=pY;
        this.type=type;
        int typeInt = (int) Math.random()*3;
        if(typeInt==0){
            type = EnumItemType.HAT;
        }
        else if(typeInt==1){
            type = EnumItemType.JETPACK;
        }
        else if(typeInt==2){
            type = EnumItemType.SHIELD;
        }
        pX = (float)Math.random() * constants.getPixelWidth();
        pY = -300;
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

    public float getyS() {
        return yS;
    }

    public void setyS(float yS) {
        this.yS = yS;
    }

    @Override
    public void update() {
        pY -= yS;
        if(pY > constants.getPixelHeight()){
            pX = (float) (Math.random() * constants.getPixelWidth());
            pY = -300;
        }
    }

    public void replace(Item item){
        pX = (float) (Math.random() * constants.getPixelWidth());
        pY = -300;
    }
}
