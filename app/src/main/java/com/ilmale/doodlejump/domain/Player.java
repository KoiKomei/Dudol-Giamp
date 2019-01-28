package com.ilmale.doodlejump.domain;

import android.util.Log;

import com.ilmale.doodlejump.Constants;
import com.ilmale.doodlejump.settings.SettingsSI;

import java.util.List;

public class Player extends AbstractGameObject {

    private static final String LOG_TAG = Player.class.getSimpleName();

    private boolean hasJetpack;
    private boolean isDeath = false;

    //current position of the player
    private float pX;
    private float pY;

    //current speed and acceleration of the player
    private float velX;
    private float accX;
    private float velY;
    private float accY;

    private float gravity = 0.2f;

    private Constants constants = Constants.getInstance();

    private List<Platform> platforms;
    private List<Bullet> bullets;
    private Enemy enemy;
    private Jetpack jetpack;

    public Player(){
        super();
        pX = constants.getPixelWidth()/2;
        pY = constants.getPixelHeight()/3;
    }

    public void pickJetpack(){
        this.hasJetpack = true;
    }

    public void jump(float force){
        Log.d(LOG_TAG, "jumping");
        velY = -force;
    }

    @Override
    public void update() {
        Log.d(LOG_TAG, "Updating Player  " + accX + "  " + accY + "  " + pX + "  " + pY);

        float frameTime = 0.666f;
        velX += (accX * frameTime * 6);
        velY += (gravity * frameTime * 6);

        if (velX > SettingsSI.MaxVel) velX = SettingsSI.MaxVel;
        else if (velX < -SettingsSI.MaxVel) velX = -SettingsSI.MaxVel;
        if (velY > SettingsSI.MaxVel) velY = SettingsSI.MaxVel;
        else if (velY < -SettingsSI.MaxVel) velY = -SettingsSI.MaxVel;

        float xS = (velX / 2) * frameTime;
        float yS = (velY / 2) * frameTime;

        pX -= xS;
        pY += yS;

        if (pX > 980) {
            pX = -100;
        } else if (pX < -105) {
            pX = 975;
        }

        for(Bullet b: bullets){
            float yB = (100 / 2) * frameTime;
            b.setpY(b.getpY()-yB);
            if(pY<0){
                bullets.remove(b);
            }
        }

        if (pY < constants.getPixelHeight()/4) {
            pY = constants.getPixelHeight()/4;
            for(Platform p: platforms){
                p.setyS(yS);
                p.update();
            }

            enemy.setyS(yS);
            enemy.update();
            jetpack.setyS(yS);
            jetpack.update();

        } else if (pY > constants.getPixelHeight()) {
            pY = constants.getPixelHeight();
        }

        if(hasJetpack && velY < 0){

        }

    }

    public float getAccX(){
        return accX;
    }

    public void setAccX(float accX){this.accX = accX;}

    public float getVelX(){ return velX; }

    public void setVelX(float velX){
        this.velX = velX;
    }

    public float getVelY() { return velY; }

    public void setVelY(float velY) { this.velY = velY; }

    public float getAccY() { return accY; }

    public void setAccY(float accY) { this.accY = accY; }

    public void setpX(float pX){
        this.pX = pX;
    }

    public float getpX(){
        return pX;
    }

    public float getpY(){
        return pY;
    }

    public void setpY(float pY){ this.pY = pY; }

    public boolean hasJetpack() {
        return hasJetpack;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public void setJetpack(Jetpack jetpack) {
        this.jetpack = jetpack;
    }

    public void setBullets(List<Bullet> bullets) {
        this.bullets = bullets;
    }

}
