package com.ilmale.doodlejump;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioManager {

    private MediaPlayer bg_audio;
    private MediaPlayer lose_audio;
    private MediaPlayer win_audio;
    private MediaPlayer bullet_audio;
    private MediaPlayer enemy_audio;
    private MediaPlayer hat_audio;
    private MediaPlayer jetpack_audio;
    private MediaPlayer springs_audio;
    private MediaPlayer killEnemy_audio;
    private MediaPlayer jumpOnEnemy_audio;
    private MediaPlayer jump_audio;
    private MediaPlayer buttonClick_audio;
    private MediaPlayer rain_audio;

    private static Context context;
    private static final AudioManager audioManagerInstance = new AudioManager(context);

    public static AudioManager getInstance() {
        return audioManagerInstance;
    }

    private AudioManager(Context context) {
        bg_audio=MediaPlayer.create(context,R.raw.background_audio);
        lose_audio=MediaPlayer.create(context,R.raw.lose_audio);
        win_audio=MediaPlayer.create(context,R.raw.win_audio);
        bullet_audio=MediaPlayer.create(context,R.raw.bullet_audio);
        enemy_audio=MediaPlayer.create(context,R.raw.enemy_audio);
        hat_audio=MediaPlayer.create(context,R.raw.hat_audio);
        jetpack_audio=MediaPlayer.create(context,R.raw.jetpack_audio);
        springs_audio=MediaPlayer.create(context,R.raw.springs_audio);
        killEnemy_audio=MediaPlayer.create(context,R.raw.killenemy_audio);
        jumpOnEnemy_audio=MediaPlayer.create(context,R.raw.jumponenemy_audio);
        jump_audio=MediaPlayer.create(context,R.raw.jump_audio);
        buttonClick_audio=MediaPlayer.create(context,R.raw.buttonclick_audio);
        rain_audio=MediaPlayer.create(context,R.raw.rain_audio);
    }

    public void playBg_audio() {
        if(!bg_audio.isPlaying()){
            bg_audio.start();
            bg_audio.setLooping(true);
        }
    }

    public void playLose_audio() {
         if(!lose_audio.isPlaying()){
             lose_audio.start();
         }
    }

    public void playWin_audio() {
        if(!win_audio.isPlaying()){
            win_audio.start();
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

    public void playHat_audio() {
        if(!hat_audio.isPlaying()){
            hat_audio.start();
        }
    }

    public void playJetpack_audio() {
        if(!jetpack_audio.isPlaying()){
            jetpack_audio.start();
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

    public void playButtonClick_audio() {
        if(!buttonClick_audio.isPlaying()){
            buttonClick_audio.start();
        }
    }

    public void playRain_audio() {
        if(!rain_audio.isPlaying()){
            rain_audio.start();
        }
    }
}
