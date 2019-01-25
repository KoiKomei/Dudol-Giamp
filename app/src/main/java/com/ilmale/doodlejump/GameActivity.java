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

public class GameActivity extends AppCompatActivity implements SensorEventListener, Runnable {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    GameEngine engine;
    GameView gameView;

    PlayerView playerView;

    public float x;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float xPos, xAccel, xVel = 0.0f;
    private float yPos, yAccel, yVel = 0.0f;
    private float xMax, yMax;
    private Bitmap ball;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        gameView = new GameView(this);
//        engine = new GameEngine();
//        setContentView(gameView);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        playerView = new PlayerView(this);
        setContentView(playerView);

        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        xMax = (float) size.x - 100;
        yMax = (float) size.y - 100;
    }

    @Override
    protected void onStart() {
        super.onStart();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //gameView.resume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //gameView.pause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            xAccel = event.values[0];
            Log.d(LOG_TAG, "Sensor Changed: " + xAccel);
            updatePlayer();
        }
    }

    void updatePlayer() {
        Log.d(LOG_TAG, "Update");
        float frameTime = 0.666f;
        xVel += (xAccel * frameTime);
        yVel += (yAccel * frameTime);

        float xS = (xVel / 2) * frameTime;
        float yS = (yVel / 2) * frameTime;

        xPos -= xS;
        yPos += yS;

        if (xPos > xMax) {
            xPos = xMax;
            xVel=0;
        } else if (xPos < 0) {
            xPos = 0;
            xVel=0;
        }

        if (yPos > yMax) {
            yPos = yMax;
            yVel=0;
        } else if (yPos < 0) {
            yPos = 0;
            yVel=0;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // TODO Auto-generated method stub
    }

    @Override
    public void run() {

    }

    private class PlayerView extends View {

        public PlayerView(Context context) {
            super(context);
            Bitmap ballSrc = BitmapFactory.decodeResource(getResources(), R.drawable.bobleft);
            final int dstWidth = 100;
            final int dstHeight = 100;
            ball = Bitmap.createScaledBitmap(ballSrc, dstWidth, dstHeight, true);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            Log.d(LOG_TAG, "Redraw");
            canvas.drawBitmap(ball, xPos, yPos, null);
            invalidate();
        }
    }
}
