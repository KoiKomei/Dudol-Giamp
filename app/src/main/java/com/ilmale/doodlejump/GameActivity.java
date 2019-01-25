package com.ilmale.doodlejump;

import android.content.Context;
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
import android.view.View;

import com.ilmale.doodlejump.engine.GameEngine;
import com.ilmale.doodlejump.view.GameView;

public class GameActivity extends AppCompatActivity implements SensorEventListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    GameEngine engine;
    GameView gameView;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        engine = new GameEngine();
        gameView = new GameView(this, engine);
        setContentView(gameView);

//        Point size = new Point();
//        Display display = getWindowManager().getDefaultDisplay();
//        display.getSize(size);
//        xMax = (float) size.x - 100;
//        yMax = (float) size.y - 100;
    }

    @Override
    protected void onStart() {
        super.onStart();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            //Log.d(LOG_TAG, "Sensor Changed: " + event.values[0]);
            engine.player.setAccX(event.values[0]);
            engine.player.setAccY(-event.values[1]);
            engine.updatePlayer();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // TODO Auto-generated method stub
    }
}
