package com.ilmale.doodlejump.engine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.ilmale.doodlejump.view.GameView;

import java.util.List;
import java.util.ArrayList;

@SuppressLint("Registered")
public class GameEngine extends Activity implements SensorEventListener {

    // gameView will be the view of the game
    // It will also hold the logic of the game
    // and respond to screen touches as well
    GameView gameView;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private long lastUpdate;
    public static int x;

    public Player player;
    public List<Platform> platforms = new ArrayList<>();
    public List<Object> objects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize gameView and set it as the view

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lastUpdate = System.currentTimeMillis();

        player = new Player();
        // Load Bob from his .png file
        //bitmapBob = BitmapFactory.decodeResource(this.getResources(), R.drawable.bob);
        for (int i=0; i<10; i++){
            platforms.add(new Platform(i*(float) (Math.random() * getResources().getDisplayMetrics().widthPixels),
                    i*50));
        }
        gameView = new GameView(this);
        setContentView(gameView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
        sensorManager.registerListener(this, accelerometer,
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            x -= (int) event.values[0];
        }
    }

    public void jump(){
        if(canJump()){
            player.setSpeed(600);
        }
    }

    public boolean canJump(){

        for (Platform p: platforms) {
            if (player.getpY() + player.getLenght() > p.getpY()+1 && (player.getpX()+player.getLenght()>p.getpX() || player.getpX()<p.getpX()+p.getLenght()) && player.getSpeed()<=0) {
                return true;
            }
        }
        return false;
    }

    public boolean canShoot(){
        if(player.getObject().getType()==ObjectType.BULLET){
            return true;
        }
        return false;
    }

    public void move(){
        player.setpX(player.getpX()+x);
        if(player.getSpeed()>0){
            while (player.getSpeed()>0){
                player.setSpeed(player.getSpeed() - player.getAcceleration());
                player.setpY(player.getpY()+player.getAcceleration());
                for (Platform p: platforms) {
                    p.setpY(p.getpY()-player.getAcceleration());
                }
            }
        }
        else if (player.getSpeed()==0){
            player.setpY(player.getpY()-player.getAcceleration());
        }

        for (Platform p: platforms) {
            if(p.getpY()>getResources().getDisplayMetrics().heightPixels){
                platforms.remove(p);
                platforms.add(new Platform((float) (Math.random() * getResources().getDisplayMetrics().widthPixels),
                        0));
            }
        }
    }

    public void takeObject(){
        for (Object o: objects) {
            if (o.getpY()<= player.getpY() && o.getpY()>=player.getpY()+player.getLenght() && o.getpX()>= player.getpX() && o.getpX()>= player.getpX()+player.getLenght() ) {
                if(o.getpY()+o.getLenght()<= player.getpY() && o.getpY()+o.getLenght()>=player.getpY()+player.getLenght() && o.getpX()+o.getLenght()>= player.getpX() && o.getpX()+o.getLenght()>= player.getpX()+player.getLenght() ){
                    if(!player.hasObject()){
                        player.pickObject(o);
                    }
                }

            }
        }
    }

}
