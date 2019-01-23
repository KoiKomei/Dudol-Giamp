package com.ilmale.doodlejump.settings;

public class SettingsSI {

    private boolean sound = true;
    private boolean music = true;

    private static final SettingsSI ourInstance = new SettingsSI();

    public static SettingsSI getInstance() {
        return ourInstance;
    }

    private SettingsSI() { }

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
}
