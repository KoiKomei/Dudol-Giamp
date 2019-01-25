package com.ilmale.doodlejump.engine;

import android.annotation.SuppressLint;
import android.util.Log;

import com.ilmale.doodlejump.domain.*;

import java.util.List;
import java.util.ArrayList;

@SuppressLint("Registered")
public class GameEngine {

    private static final String LOG_TAG = GameEngine.class.getSimpleName();

    // gameView will be the view of the game
    // and respond to screen touches as well
    //GameView gameView;
    private long lastUpdate;

    List<AbstractGameObject> objects = new ArrayList<>();

    public int x;

    public Player player;
    List<Platform> platforms = new ArrayList<>();
    public List<Bullet> bullets = new ArrayList<>();
    public Item item;
    public Enemy enemy;

    //public GameView getGameView(){return gameView;}

    public GameEngine(){

        lastUpdate = System.currentTimeMillis();

        player = new Player();




        Platform platform1 = new Platform(550, 1650);
        Platform platform2 = new Platform(200, 1650);
        Platform platform3 = new Platform(1500, 450);
        Platform platform4 = new Platform(1500, 450);
        Platform platform5 = new Platform(1500, 450);

        platforms.add(platform1); platforms.add(platform2);


        /*enemy = new Enemy();
        placeEnemy();

        Item item = new Item();
        placeItem();*/

    }

    public void update() {
        Log.d(LOG_TAG, "updating gameengine");
        for (Platform p: platforms) {
            if (collidesFromAbove(player, p)){
                Log.d(LOG_TAG, "collision!");
                player.jump(1000);
            }
        }
    }

    public void updatePlayer() {
        player.update();
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
            if (collide(p, player)) {
                if (p.hasSprings()) {
                    player.setVelX(1800);
                }
                else {
                    player.setVelX(600);
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
            return (y11 >= y21 && y11 <= y22) || (y12 >= y21 && y12 <= y22);
        }
        return false;
    }

    private boolean collidesFromAbove(AbstractGameObject obj1, AbstractGameObject obj2) {
        float x11 = obj1.getpX();
        float x12 = x11 + obj1.getWidth();
        float x21 = obj2.getpX();
        float x22 = x21 + obj2.getWidth();
        float y11 = obj1.getpY();
        float y12 = y11 + obj1.getHeight();
        float y21 = obj2.getpY();
        float y22 = y21 + obj2.getHeight();
        if ((x11 >= x21 && x11 <= x22) || (x12 >= x21 && x12 <= x22)) {
            Log.d(LOG_TAG, "colliding X");
            return (y12 >= y21 - 15 || y12 <= y21 + 15);
        }
        return false;
    }

    public List<Platform> getPlatforms(){
        return platforms;
    }

    /*private void move(){

        player.setpX(player.getpX() + x);

        if (player.getVelX() > 0) {

            while (player.getVelX() > 0) {

                player.setVelX(player.getVelX() + player.getAccX());

                if (player.getpY() + player.getHeight() > 800){
                    player.setpY(player.getpY() - player.getAccX());
                }
                for (Platform p: platforms) {
                    p.setpY(p.getpY() + player.getAccX());
                }
                item.setpY(item.getpY() + player.getAccX());
                enemy.setpY(enemy.getpY() + player.getAccX());

            }

        }
        else if (player.getVelX() <= 0){
            if(player.hasObject() && player.getItem().getType()==EnumItemType.HAT && player.getItem().getType()==EnumItemType.JETPACK ){
                player.loseObject();
                placeItem();
            }
            player.setpY(player.getpY()+player.getAccX());
        }

        for (Platform p: platforms) {
            if(p.getpY()>getResources().getDisplayMetrics().heightPixels){
                platforms.remove(p);
                platforms.add(new Platform((float) (Math.random() * getResources().getDisplayMetrics().widthPixels),
                        0));
            }
        }
        for(Bullet b:bullets){
            b.setpY(b.getpY()-b.getVelX());
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
        if (item.getpY()<= player.getpY() && item.getpY()>=player.getpY()+gameView.getBitmapBob().getHeight() && item.getpX()>= player.getpX() && item.getpX()>= player.getpX()+gameView.getBitmapBob().getWidth() ) {
            if(item.getpY()+100<= player.getpY() && item.getpY()+100>=player.getpY()+gameView.getBitmapBob().getHeight() && item.getpX()+100>= player.getpX() && item.getpX()+100>= player.getpX()+gameView.getBitmapBob().getWidth() ){
                if(!player.hasObject()){
                    player.pickObject(item);
                    switch (item.getType()){
                        case HAT:
                            item.setpX(player.getpX());
                            item.setpY(player.getpY()-gameView.getBitmapHAT().getHeight());
                            player.setVelX(3000);
                        case SHIELD:
                            item.setpX(player.getpX());
                            item.setpY(player.getpY());
                            item.setTimeShield(1000);
                        case JETPACK:
                            item.setpX(player.getpX());
                            item.setpY(player.getpY());
                            player.setVelX(4000);
                    }
                }
            }
        }
    }

    public void killEnemy(){
        for(Bullet b:bullets){
            if (enemy.getpY() <= b.getpY() && enemy.getpY() >= b.getpY() + gameView.getBitmapBULLET().getHeight() && enemy.getpX() >= b.getpX() && enemy.getpX() >= b.getpX() + gameView.getBitmapBULLET().getWidth()) {
                if (enemy.getpY() + gameView.getBitmapEnemy().getHeight() <= b.getpY() && enemy.getpY() + gameView.getBitmapEnemy().getHeight() >= b.getpY() + gameView.getBitmapBULLET().getHeight() && enemy.getpX() + gameView.getBitmapEnemy().getWidth() >= b.getpX() && enemy.getpX() + gameView.getBitmapEnemy().getWidth() >= b.getpX() + gameView.getBitmapBULLET().getWidth()) {
                    placeEnemy();
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

    public void shoot() {
        Bullet bullet = new Bullet(player.getpX(), player.getpY(),30);
        bullets.add(bullet);
    }*/

}
