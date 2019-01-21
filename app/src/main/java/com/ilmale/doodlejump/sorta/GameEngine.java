package com.ilmale.doodlejump.sorta;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

import com.ilmale.doodlejump.mammata.*;
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
    public Item item;
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
            int j = (int) Math.random()*10+1;
            Platform platform= new Platform(i*(float) (Math.random() * getResources().getDisplayMetrics().widthPixels),i*50);
            if(j==5){
                platform.setHasSprings(true);
            }
            platforms.add(platform);
        }
        enemy = new Enemy();
        enemy.setpX((float)Math.random() * getResources().getDisplayMetrics().widthPixels);
        enemy.setpY(-300);

        Item item = new Item();
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

    public void Jump(){
        for (Platform p: platforms) {
            if (player.getpY() + gameView.getBitmapBob().getHeight() > p.getpY()+1 && (player.getpX()+gameView.getBitmapBob().getWidth()>p.getpX() || player.getpX()<p.getpX()+gameView.getBitmapPlatform().getWidth()) && player.getSpeed()<=0) {
                if(p.hasSprings()){
                    player.setSpeed(1200);
                }
                else {
                    player.setSpeed(600);
                }
            }
        }
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
                item.setpY(item.getpY()+player.getAcceleration());
                enemy.setpY(enemy.getpY()+player.getAcceleration());
            }
        }
        else if (player.getSpeed()<=0){
            if(player.hasObject() && player.getItem().getType()==EnumItemType.HAT && player.getItem().getType()==EnumItemType.JETPACK ){
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
        if (item.getpY()<= player.getpY() && item.getpY()>=player.getpY()+gameView.getBitmapBob().getHeight() && item.getpX()>= player.getpX() && item.getpX()>= player.getpX()+gameView.getBitmapBob().getWidth() ) {
            if(item.getpY()+100<= player.getpY() && item.getpY()+100>=player.getpY()+gameView.getBitmapBob().getHeight() && item.getpX()+100>= player.getpX() && item.getpX()+100>= player.getpX()+gameView.getBitmapBob().getWidth() ){
                if(!player.hasObject()){
                    player.pickObject(item);
                    switch (item.getType()){
                        case HAT:
                            item.setpX(player.getpX());
                            item.setpY(player.getpY()-gameView.getBitmapHAT().getHeight());
                            player.setSpeed(3000);
                        case SHIELD:
                            item.setpX(player.getpX());
                            item.setpY(player.getpY());
                            item.setTimeShield(1000);
                        case JETPACK:
                            item.setpX(player.getpX());
                            item.setpY(player.getpY());
                            player.setSpeed(4000);
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
                if (player.getItem().getType()!=EnumItemType.SHIELD) {
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
