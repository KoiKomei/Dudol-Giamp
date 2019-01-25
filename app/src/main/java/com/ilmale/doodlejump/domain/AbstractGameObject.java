package com.ilmale.doodlejump.domain;

public abstract class AbstractGameObject {

    private float pX;
    private float pY;
    private float width;
    private float height;

    public AbstractGameObject(){}

    public AbstractGameObject(float pX, float pY, float width, float height) {
        this.pX = pX;
        this.pY = pY;
        this.width = width;
        this.height = height;
    }

    public float getpX() {
        return pX;
    }

    public void setpX(float pX) {
        this.pX = pX;
    }

    public float getpY() {
        return pY;
    }

    public void setpY(float pY) {
        this.pY = pY;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public abstract void update();

}
