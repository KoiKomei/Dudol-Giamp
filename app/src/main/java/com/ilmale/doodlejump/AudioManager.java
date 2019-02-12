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
            if(bgaudioactive){
                bg_audio.pause();
                bgaudioactive = false;
            }
        }
    }

    public void pauseBg_audio(){
        if(bgaudioactive){
            bg_audio.pause();
            bgaudioactive = false;
        }
    }


    public void playLose_audio() {
        if(!lose_audio.isPlaying()){
            lose_audio.start();
        }
    }

    public void pauseLose_audio() {
        if(lose_audio.isPlaying()){
            lose_audio.pause();
        }
    }


    public void playBullet_audio() {
        if(!bullet_audio.isPlaying()){
            bullet_audio.start();
        }
    }

    public void playEnemy_audio() {
        if(!enemy_audio.isPlaying()){
            enemy_audio.start();
        }
    }

    public void pauseEnemy_audio() {
        if(enemy_audio.isPlaying()){
            enemy_audio.pause();
        }
    }

    public void playJetpack_audio() {
        if(!jetpack_audio.isPlaying()){
            jetpack_audio.start();
        }
    }

    public void pauseJetpack_audio() {
        if(jetpack_audio.isPlaying()){
            jetpack_audio.pause();
        }
    }

    public void playSprings_audio() {
        if(!springs_audio.isPlaying()){
            springs_audio.start();
        }
    }

    public void playKillEnemy_audio() {
        if(!killEnemy_audio.isPlaying()){
            killEnemy_audio.start();
        }
    }

    public void playJumpOnEnemy_audio() {
        if(!jumpOnEnemy_audio.isPlaying()){
            jumpOnEnemy_audio.start();
        }
    }

    public void playJump_audio() {
        if(!jump_audio.isPlaying()){
            jump_audio.start();
        }
    }

    public void setVolume0(){
        bullet_audio.setVolume(0,0);
        enemy_audio.setVolume(0,0);
        jetpack_audio.setVolume(0,0);
        springs_audio.setVolume(0,0);
        killEnemy_audio.setVolume(0,0);
        jumpOnEnemy_audio.setVolume(0,0);
        jump_audio.setVolume(0,0);
    }

    public void setVolume1(){
        bullet_audio.setVolume(1,1);
        enemy_audio.setVolume(1,1);
        jetpack_audio.setVolume(1,1);
        springs_audio.setVolume(1,1);
        killEnemy_audio.setVolume(1,1);
        jumpOnEnemy_audio.setVolume(1,1);
        jump_audio.setVolume(1,1);
    }


    public void pauseAll(){
        if(settingsSI.isSound()){
            bullet_audio.pause();
            enemy_audio.pause();
            jetpack_audio.pause();
            springs_audio.pause();
            killEnemy_audio.pause();
            jumpOnEnemy_audio.pause();
            jump_audio.pause();
        }
    }

    public boolean isCanStopBgAudio(){
        return canStopBgAudio;
    }

    public void setCanStopBgAudio(boolean canStopBgAudio) {
        this.canStopBgAudio = canStopBgAudio;
    }

}
