package com.ilmale.doodlejump.settings;

public class SettingsSI {

    private static final SettingsSI ourInstance = new SettingsSI();

    public static SettingsSI getInstance() {
        return ourInstance;
    }

    private SettingsSI() { }

    private boolean sound = true;
    private boolean music = true;
    private boolean weatherCondition = true;
    public static final float MaxVel = 100;

    public void setSound(boolean v){
        sound = v;
    }

    public void setMusic(boolean v){
        music = v;
    }

    public boolean isSound() {
        return sound;
    }

    public boolean isMusic() {
        return music;
    }

    public boolean isWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(boolean weatherCondition) {
        this.weatherCondition = weatherCondition;
    }
}
