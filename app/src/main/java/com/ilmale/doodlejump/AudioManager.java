package com.ilmale.doodlejump;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.ilmale.doodlejump.settings.SettingsSI;

public class AudioManager {

    private MediaPlayer bg_audio;
    private MediaPlayer lose_audio;
    private MediaPlayer bullet_audio;
    private MediaPlayer enemy_audio;
    private MediaPlayer jetpack_audio;
    private MediaPlayer springs_audio;
    private MediaPlayer killEnemy_audio;
    private MediaPlayer jumpOnEnemy_audio;
    private MediaPlayer jump_audio;
    private MediaPlayer rain_audio;

    SettingsSI settingsSI = SettingsSI.getInstance();
    boolean bgaudioactive = false;
    private boolean canStopBgAudio = true;
    private boolean canCreate = true;

    private static final AudioManager audioManagerInstance = new AudioManager();

    public static AudioManager getInstance() {
        return audioManagerInstance;
    }

    private AudioManager() {

    }

    public void create(Context context){
        if(canCreate){
            bg_audio=MediaPlayer.create(context,R.raw.background_audio);
            lose_audio=MediaPlayer.create(context,R.raw.lose_audio);
            bullet_audio=MediaPlayer.create(context,R.raw.bullet_audio);
            enemy_audio=MediaPlayer.create(context,R.raw.enemy_audio);
            jetpack_audio=MediaPlayer.create(context,R.raw.jetpack_audio);
            springs_audio=MediaPlayer.create(context,R.raw.springs_audio);
            killEnemy_audio=MediaPlayer.create(context,R.raw.killenemy_audio);
            jumpOnEnemy_audio=MediaPlayer.create(context,R.raw.jumponenemy_audio);
            jump_audio=MediaPlayer.create(context,R.raw.jump_audio);
            rain_audio=MediaPlayer.create(context,R.raw.rain_audio);
            canCreate=false;
        }
    }

    public void playBg_audio() {
        if(settingsSI.isMusic()){
            if(!bgaudioactive){
                bg_audio.start();
                bg_audio.setLooping(true);
                bgaudioactive = true;
            }
        }
        else{
            bg_audio.pause();
            bgaudioactive = false;
        }
    }

    public void pauseBg_audio(){
        bg_audio.pause();
        bgaudioactive = false;
    }


    public void playLose_audio() {
        if(!lose_audio.isPlaying() && settingsSI.isSound()){
            lose_audio.start();
        }
    }

    public void pauseLose_audio() {
        lose_audio.pause();
    }


    public void playBullet_audio() {
        if(!bullet_audio.isPlaying() && settingsSI.isSound()){
            bullet_audio.start();
        }
    }

    public void playEnemy_audio() {
        if(!enemy_audio.isPlaying() && settingsSI.isSound()){
            enemy_audio.start();
        }
    }

    public void pauseEnemy_audio() {
        if(enemy_audio.isPlaying()){
            enemy_audio.pause();
        }
    }

    public void playJetpack_audio() {
        if(!jetpack_audio.isPlaying() && settingsSI.isSound()){
            jetpack_audio.start();
        }
    }

    public void pauseJetpack_audio() {
        if(jetpack_audio.isPlaying()){
            jetpack_audio.pause();
        }
    }

    public void playSprings_audio() {
        if(!springs_audio.isPlaying() && settingsSI.isSound()){
            springs_audio.start();
        }
    }

    public void playKillEnemy_audio() {
        if(!killEnemy_audio.isPlaying() && settingsSI.isSound()){
            killEnemy_audio.start();
        }
    }

    public void playJumpOnEnemy_audio() {
        if(!jumpOnEnemy_audio.isPlaying() && settingsSI.isSound()){
            jumpOnEnemy_audio.start();
        }
    }

    public void playJump_audio() {
        if(!jump_audio.isPlaying() && settingsSI.isSound()){
            jump_audio.start();
        }
    }

    public void playRain_audio() {
        if(!rain_audio.isPlaying() && settingsSI.isSound()){
            rain_audio.start();
        }
    }

    public void pauseAll(){
        bullet_audio.pause();
        enemy_audio.pause();
        jetpack_audio.pause();
        springs_audio.pause();
        killEnemy_audio.pause();
        jumpOnEnemy_audio.pause();
        jump_audio.pause();
        rain_audio.pause();
    }

    public boolean isCanStopBgAudio(){
        return canStopBgAudio;
    }

    public void setCanStopBgAudio(boolean canStopBgAudio) {
        this.canStopBgAudio = canStopBgAudio;
    }

}
