package com.ilmale.doodlejump;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ilmale.doodlejump.engine.MultiEngine;
import com.ilmale.doodlejump.view.MultiGameView;

/**
 * ACTIVITY CHE FA PARTIRE L'ENGINE E LA VIEW DEL MULTIPLAYER
 * */

public class MultiActivity extends AppCompatActivity implements SensorEventListener {

    MultiEngine engine;
    MultiGameView gameView;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private Utility utility = Utility.getInstance();
    private Records records = Records.getInstance();
    private AudioManager audioManager = AudioManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //Client client = new Client();
        //client.start();

        engine = new MultiEngine();
        gameView = new MultiGameView(this, engine);
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
        if(audioManager.isCanStopBgAudio()){
            audioManager.pauseBg_audio();
        }
        audioManager.setCanStopBgAudio(true);
        gameView.pause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onBackPressed() {
        audioManager.setCanStopBgAudio(false);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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

}
