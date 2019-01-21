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
    public List<Bullet> bullets = new ArrayList<>();
    public Object object;
    public Enemy enemy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize gameView and set it as the view

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lastUpdate = System.currentTimeMillis();

        gameView = new GameView(this);

        player = new Player();
        player.setpX(getResources().getDisplayMetrics().widthPixels/2-gameView.getBitmapBob().getWidth()/2);
        player.setpY((getResources().getDisplayMetrics().heightPixels - 50) - gameView.getBitmapBob().getHeight());

        // Load Bob from his .png file
        //bitmapBob = BitmapFactory.decodeResource(this.getResources(), R.drawable.bob);
        for (int i=0; i<10; i++){
            platforms.add(new Platform(i*(float) (Math.random() * getResources().getDisplayMetrics().widthPixels),
                    i*50));
        }
        enemy = new Enemy();
        enemy.setpX((float)Math.random() * getResources().getDisplayMetrics().widthPixels);
        enemy.setpY(-300);

        Object object = new Object();
        enemy.setpX((float)Math.random() * getResources().getDisplayMetrics().widthPixels);
        enemy.setpY(-300);

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
            if (player.getpY() + gameView.getBitmapBob().getHeight() > p.getpY()+1 && (player.getpX()+gameView.getBitmapBob().getWidth()>p.getpX() || player.getpX()<p.getpX()+gameView.getBitmapPlatform().getWidth()) && player.getSpeed()<=0) {
                return true;
            }
        }
        return false;
    }

    public void move(){
        player.setpX(player.getpX()+x);
        if(player.getSpeed()>0){
            while (player.getSpeed()>0){
                player.setSpeed(player.getSpeed() + player.getAcceleration());
                player.setpY(player.getpY()-player.getAcceleration());
                for (Platform p: platforms) {
                    p.setpY(p.getpY()+player.getAcceleration());
                }
                object.setpY(object.getpY()+player.getAcceleration());
                enemy.setpY(enemy.getpY()+player.getAcceleration());
            }
        }
        else if (player.getSpeed()<=0){
            if(player.hasObject() && player.getObject().getType()==ObjectType.HAT && player.getObject().getType()==ObjectType.JETPACK && player.getObject().getType()==ObjectType.SPRINGS ){
                player.loseObject();
            }
            player.setpY(player.getpY()+player.getAcceleration());
        }

        for (Platform p: platforms) {
            if(p.getpY()>getResources().getDisplayMetrics().heightPixels){
                platforms.remove(p);
                platforms.add(new Platform((float) (Math.random() * getResources().getDisplayMetrics().widthPixels),
                        0));
            }
        }
        for(Bullet b:bullets){
            b.setpY(b.getpY()-b.getSpeed());
            if(b.getpY()<0){
                bullets.remove(b);
            }

        }
    }

    public void takeObject(){
        if (object.getpY()<= player.getpY() && object.getpY()>=player.getpY()+gameView.getBitmapBob().getHeight() && object.getpX()>= player.getpX() && object.getpX()>= player.getpX()+gameView.getBitmapBob().getWidth() ) {
            if(object.getpY()+100<= player.getpY() && object.getpY()+100>=player.getpY()+gameView.getBitmapBob().getHeight() && object.getpX()+100>= player.getpX() && object.getpX()+100>= player.getpX()+gameView.getBitmapBob().getWidth() ){
                if(!player.hasObject()){
                    player.pickObject(object);
                    switch (object.getType()){
                        case HAT:
                            object.setpX(player.getpX());
                            object.setpY(player.getpY()-gameView.getBitmapHAT().getHeight());
                            player.setSpeed(3000);
                        case SHIELD:
                            object.setpX(player.getpX());
                            object.setpY(player.getpY());
                            object.setTimeShield(1000);
                        case JETPACK:
                            object.setpX(player.getpX());
                            object.setpY(player.getpY());
                            player.setSpeed(4000);
                        case SPRINGS:
                            object.setpX(player.getpX());
                            object.setpY(player.getpY()+gameView.getBitmapBob().getHeight());
                            player.setSpeed(1200);
                    }
                }
            }
        }
    }

    public void killEnemy(){
        for(Bullet b:bullets){
            if (enemy.getpY() <= b.getpY() && enemy.getpY() >= b.getpY() + gameView.getBitmapBULLET().getHeight() && enemy.getpX() >= b.getpX() && enemy.getpX() >= b.getpX() + gameView.getBitmapBULLET().getWidth()) {
                if (enemy.getpY() + gameView.getBitmapEnemy().getHeight() <= b.getpY() && enemy.getpY() + gameView.getBitmapEnemy().getHeight() >= b.getpY() + gameView.getBitmapBULLET().getHeight() && enemy.getpX() + gameView.getBitmapEnemy().getWidth() >= b.getpX() && enemy.getpX() + gameView.getBitmapEnemy().getWidth() >= b.getpX() + gameView.getBitmapBULLET().getWidth()) {
                    enemy.setpX((float) Math.random() * getResources().getDisplayMetrics().widthPixels);
                    enemy.setpY(-300);
                }
            }
        }
    }

    public boolean killedByEnemy(){
        if (enemy.getpY()<= player.getpY() && enemy.getpY()>=player.getpY()+gameView.getBitmapBob().getHeight() && enemy.getpX()>= player.getpX() && enemy.getpX()>= player.getpX()+gameView.getBitmapBob().getWidth() ) {
            if(enemy.getpY()+gameView.getBitmapEnemy().getHeight()<= player.getpY() && enemy.getpY()+gameView.getBitmapEnemy().getHeight()>=player.getpY()+gameView.getBitmapBob().getHeight() && enemy.getpX()+gameView.getBitmapEnemy().getWidth()>= player.getpX() && enemy.getpX()+gameView.getBitmapEnemy().getWidth()>= player.getpX()+gameView.getBitmapBob().getWidth() ) {
                if (player.getObject().getType()!=ObjectType.SHIELD) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isDeath(){
        if(player.getpX()>getResources().getDisplayMetrics().heightPixels || killedByEnemy()){
            return true;
        }
        return false;
    }

    public void Shoot() {
        Bullet bullet = new Bullet(player.getpX(), player.getpY(),30);
        bullets.add(bullet);
    }
}
