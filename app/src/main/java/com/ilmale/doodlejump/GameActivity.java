package com.ilmale.doodlejump;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ilmale.doodlejump.engine.GameEngine;
import com.ilmale.doodlejump.view.GameView;


/**
 * ACTIVITY CHE FA PARTIRE IL GAME ENGINE E LA GAMEVIEW DEL SINGLEPLAYER
 *
 * */

public class GameActivity extends AppCompatActivity implements SensorEventListener {

    GameEngine engine;
    GameView gameView;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private Utility utility = Utility.getInstance();
    private AudioManager audioManager = AudioManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        engine = new GameEngine();
        gameView = new GameView(this, engine);
        setContentView(gameView);
        utility.setPoints(0);

    }

    @Override
    protected void onStart() {
        super.onStart();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onResume() {
        super.onResume();
        audioManager.playBg_audio();
        gameView.resume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
        if(audioManager.isCanStopBgAudio()){
            audioManager.pauseBg_audio();
        }
        audioManager.setCanStopBgAudio(true);
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            engine.player.setAccX(event.values[0]/5);
            engine.updatePlayer();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onBackPressed() {
        audioManager.setCanStopBgAudio(false);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
