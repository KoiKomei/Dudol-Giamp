package com.ilmale.doodlejump.domain;

import android.util.Log;

import com.ilmale.doodlejump.AudioManager;
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
    private float yS = 0;

    private float gravity = 0.198f;
    private double startTime;
    private double endTime;

    private Constants constants = Constants.getInstance();

    private List<Platform> platforms;
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
        velY = -force;
    }

    public void updateControls() {
        //Log.d(LOG_TAG, "Updating Player  " + accX + "  " + accY + "  " + pX + "  " + pY);
        velX = (accX * 100);
        float xS = (velX / 2);
        pX -= xS;
        if (pX > constants.getPixelWidth()) {
            pX = -this.getWidth();
        } else if (pX < -this.getWidth()) {
            pX = constants.getPixelWidth();
        }
    }

    @Override
    public void update(){

        velY += (gravity * 6);

        yS = (velY / 2);

        if(hasJetpack && velYjet < 0){
            velYjet += (gravity * 6);
            yS = (-SettingsSI.MaxVel / 2);
        }
        pY += yS;

        if(hasJetpack && velYjet >= 0){
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

    public void setJetpack(Jetpack jetpack) {
        this.jetpack = jetpack;
    }

    public float getyS() {
        return yS;
    }

}
