package com.ilmale.doodlejump.domain;

import android.util.Log;

import com.ilmale.doodlejump.Constants;
import com.ilmale.doodlejump.settings.SettingsSI;

import java.util.List;

public class Player extends AbstractGameObject {

    private static final String LOG_TAG = Player.class.getSimpleName();

    private boolean hasJetpack = false;

    //current position of the player
    private float pX;
    private float pY;

    //current speed and acceleration of the player
    private float velX;
    private float accX;
    private float velY;
    private float accY;

    private float velYjet;

    private float gravity = 0.22f;
    private int accCoeff = 2;
    private double startTime;
    private double endTime;

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
        hasJetpack = true;
        velYjet = -250;
        startTime = System.currentTimeMillis();
        endTime = startTime + jetpack.getDuration();
    }

    public void jump(float force){
        //Log.d(LOG_TAG, "jumping");
        velY = -force;
    }

    public void updateControls() {
        //Log.d(LOG_TAG, "Updating Player  " + accX + "  " + accY + "  " + pX + "  " + pY);
        if (velX - accX > velX) velX += (accX * accCoeff * 3);
        else velX += (accX * accCoeff);

        if (velX > SettingsSI.MaxVel) velX = SettingsSI.MaxVel;
        else if (velX < -SettingsSI.MaxVel) velX = -SettingsSI.MaxVel;

        float xS = (velX / 2);
        pX -= xS;
        if (pX > 980) {
            pX = -100;
        } else if (pX < -105) {
            pX = 975;
        }
    }

    @Override
    public void update(){

        velY += (gravity * 6);

        if(hasJetpack){
            velYjet += (gravity * 6);
        }

        if (velY > SettingsSI.MaxVel) velY = SettingsSI.MaxVel;
        else if (velY < -SettingsSI.MaxVel) velY = -SettingsSI.MaxVel;

        float yS = (velY / 2);

        if(velYjet<0){
            yS = (-SettingsSI.MaxVel / 2);
        }
        pY += yS;

        if(hasJetpack && velYjet > 0){
            hasJetpack=false;
            velYjet=0;
            velY=0;
            jetpack.replace();
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
