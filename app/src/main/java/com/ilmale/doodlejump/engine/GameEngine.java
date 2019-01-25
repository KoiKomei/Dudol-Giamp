package com.ilmale.doodlejump.engine;

import android.annotation.SuppressLint;

import com.ilmale.doodlejump.domain.*;

import java.util.List;
import java.util.ArrayList;

@SuppressLint("Registered")
public class GameEngine {

    // gameView will be the view of the game
    // and respond to screen touches as well
    //GameView gameView;
    private long lastUpdate;

    List<AbstractGameObject> objects = new ArrayList<>();

    public int x;

    public Player player;
    public List<Platform> platforms = new ArrayList<>();
    public List<Bullet> bullets = new ArrayList<>();
    public Item item;
    public Enemy enemy;

    //public GameView getGameView(){return gameView;}

    public GameEngine(){

        lastUpdate = System.currentTimeMillis();

        player = new Player();
        player.setpX(45);
        player.setpY(30);

        /*for (int i = 0; i < 10; i++){
            int j = (int) Math.random() * 10+1;
            Platform platform = new Platform(i*(float) (Math.random() * getResources().getDisplayMetrics().widthPixels),i*50);
            if( j == 5){
                platform.setHasSprings(true);
            }
            objects.add(platform);
        }

        enemy = new Enemy();
        placeEnemy();

        Item item = new Item();
        placeItem();*/

    }

    /*@Override
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

        //setContentView(gameView);
    }*/

    private void jump(){
        for (Platform p: platforms) {
            if (collideForJump(player,p)) {
                if (p.hasSprings()) {
                    player.setSpeed(1800);
                }
                else {
                    player.setSpeed(600);
                }
            }
        }
    }

    private boolean collide(AbstractGameObject obj1, AbstractGameObject obj2) {
        float x11 = obj1.getpX();
        float x12 = x11 + obj1.getWidth();
        float x21 = obj2.getpX();
        float x22 = x21 + obj2.getWidth();
        float y11 = obj1.getpY();
        float y12 = y11 + obj1.getHeight();
        float y21 = obj2.getpY();
        float y22 = y21 + obj2.getHeight();
        if ((x11 >= x21 && x11 <= x22) || (x12 >= x21 && x12 <= x22)) {
            if ((y11 >= y21 && y11 <= y22) || (y12 >= y21 && y12 <= y22)) {
                return true;
            }
        }
        return false;
    }

    private boolean collideForJump(Player player, Platform platform) {
        float x11 = player.getpX();
        float x12 = x11 + player.getWidth();
        float x21 = platform.getpX();
        float x22 = x21 + platform.getWidth();
        float y11 = player.getpY();
        float y12 = y11 + player.getHeight();
        float y21 = platform.getpY();
        float y22 = y21 + platform.getHeight();
        if ((x11 >= x21 && x11 <= x22) || (x12 >= x21 && x12 <= x22)) {
            if (y12 > y21) {
                return true;
            }
        }
        return false;
    }
    /*
    private void move(){

        player.setpX(player.getpX() + x);

        if (player.getSpeed() > 0) {

            while (player.getSpeed() > 0) {

                player.setSpeed(player.getSpeed() + player.getAcceleration());

                if (player.getpY() + player.getHeight() > 800){
                    player.setpY(player.getpY() - player.getAcceleration());
                }
                for (Platform p: platforms) {
                    p.setpY(p.getpY() + player.getAcceleration());
                }
                item.setpY(item.getpY() + player.getAcceleration());
                enemy.setpY(enemy.getpY() + player.getAcceleration());

            }

        }
        else if (player.getSpeed() <= 0){
            if(player.hasObject() && player.getItem().getType()==EnumItemType.HAT && player.getItem().getType()==EnumItemType.JETPACK ){
                player.loseObject();
                placeItem();
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
        if(item.getpX()>getResources().getDisplayMetrics().heightPixels){
            placeItem();
        }
        if(enemy.getpX()>getResources().getDisplayMetrics().heightPixels){
            placeEnemy();
        }
    }

    public void placeItem(){
        int type = (int) Math.random()*3;
        if(type==0){
            item.setType(EnumItemType.HAT);
        }
        else if(type==1){
            item.setType(EnumItemType.JETPACK);
        }
        else if(type==2){
            item.setType(EnumItemType.SHIELD);
        }
        item.setpX((float)Math.random() * getResources().getDisplayMetrics().widthPixels);
        item.setpY(-300);
    }

    public void placeEnemy(){
        enemy.setpX((float) Math.random() * getResources().getDisplayMetrics().widthPixels);
        enemy.setpY(-300);
    }

    public void takeObject(){
        if (collide(item,player)){
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

    public void killEnemy(){
        for(Bullet b:bullets){
            if (collide(enemy,b))
                    placeEnemy();
                }
            }
        }
        if(collideForJump(player,enemy))
            placeEnemy();
    }

    public boolean killedByEnemy(){
        if(collide(enemy,player)){
            if (player.hasObject() && player.getItem().getType()!=EnumItemType.SHIELD) {
                return true;
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

    public void shoot() {
        Bullet bullet = new Bullet(player.getpX(), player.getpY(),30);
        bullets.add(bullet);
    }*/

    public void update() {
        for (AbstractGameObject o: objects) {
            o.update();
        }
    }
}
