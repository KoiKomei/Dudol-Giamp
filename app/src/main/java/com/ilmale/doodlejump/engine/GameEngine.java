package com.ilmale.doodlejump.engine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.ilmale.doodlejump.Constants;
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
    private Constants constants = Constants.getInstance();

    private int points=0;

    //public GameView getGameView(){return gameView;}

    public GameEngine(){

        lastUpdate = System.currentTimeMillis();

        player = new Player();

        Platform platform = new Platform();
        platform.createRandomPlatform(platforms);

        player.setPlatforms(platforms);
        /*
        enemy = new Enemy();
        placeEnemy();
        Item item = new Item();
        placeItem();
        */
    }

    public void update() {
        Log.d(LOG_TAG, "updating gameengine");
        for (Platform p: platforms) {
            if (collidesFromAbove(player, p)){
                Log.d(LOG_TAG, "collision!");
                if (p.hasSprings()) {
                    player.jump(50);
                }
                else {
                    player.jump(30);
                }
            }
        }
    }

    public void updatePlayer() {
        player.update();
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
            return (y12 <= y21 && y12 >= y21 - 10);
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
            if(p.getpY()>constants.getPixelHeight()){
                p.setpX((float) (Math.random() * constants.getPixelWidth());
                p.setpY(0);
                points+=10;
            }
        }
        for(Bullet b:bullets){
            b.setpY(b.getpY()-b.getVelX());
            if(b.getpY()<0){
                bullets.remove(b);
            }

        }
        if(item.getpX()>constants.getPixelHeight()){
            placeItem();
        }
        if(enemy.getpX()>constants.getPixelHeight()){
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
        item.setpX((float)Math.random() * constants.getPixelWidth());
        item.setpY(-300);
    }

    public void placeEnemy(){
        enemy.setpX((float) Math.random() * constants.getPixelWidth());
        enemy.setpY(-300);
    }

    public void takeObject(){
        if (collide(p,item)){
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
            if (collide(enemy,b)) {
                    placeEnemy();
                }
            }
        }
    }

    public boolean killedByEnemy(){
        if ( collide(player, enemy) ) {
                if (player.getItem().getType()!=EnumItemType.SHIELD) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isDeath(){
        if(player.getpX()>constants.getPixelHeight() || killedByEnemy()){
            return true;
        }
        return false;
    }

    public void shoot() {
        Bullet bullet = new Bullet(player.getpX(), player.getpY(),30);
        bullets.add(bullet);
    }


    public void endGame(){

    }
    */

}
