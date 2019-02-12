package com.ilmale.doodlejump;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import com.ilmale.doodlejump.engine.GameEngine;
import com.ilmale.doodlejump.engine.MultiEngine;
import com.ilmale.doodlejump.network.Client;
import com.ilmale.doodlejump.view.GameView;
import com.ilmale.doodlejump.view.MultiGameView;

import java.security.PublicKey;

public class MultiActivity extends AppCompatActivity implements SensorEventListener {

    MultiEngine engine;
    MultiGameView gameView;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private Constants constants = Constants.getInstance();
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
        constants.setPoints(0);
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
