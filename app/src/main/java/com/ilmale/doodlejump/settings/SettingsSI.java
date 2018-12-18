package com.ilmale.doodlejump.settings;

public class SettingsSI {

    private boolean audio = true;
    private boolean music = true;

    private static final SettingsSI ourInstance = new SettingsSI();

    public static SettingsSI getInstance() {
        return ourInstance;
    }

    private SettingsSI() { }

    public boolean setAudio(){
        if (audio){
            audio = false;
            return false;
        }
        else audio = true;
        return true;
    }

    public boolean setMusic(){
        if (music){
            music = false;
            return false;
        }
        else music = true;
        return true;
    }
}
