package com.ilmale.doodlejump.domain;

public class Bullet extends AbstractGameObject {

    private float pX;
    private float pY;
    private float speed;

    public Bullet(){}

    public Bullet(float pX, float pY, float speed) {
        this.pX = pX;
        this.pY = pY;
        this.speed = speed;
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

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public void update() {

    }
}
