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

        enemy = new Enemy();
        item = new Item();
        player.setEnemy(enemy);
        player.setItem(item);
    }

    public void update() {
        Log.d(LOG_TAG, "updating gameengine");
        for (Platform p: platforms) {
            if (collidesFromAbove(player, p)){
                Log.d(LOG_TAG, "collision!");
                if (p.hasSprings()) {
                    player.jump(60);
                }
                else {
                    player.jump(40);
                }
            }
        }
        takeObject();

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

    public void takeObject(){
        if (collide(player,item)){
            if(!player.hasObject()){
                player.pickObject(item);
                switch (item.getType()){
                    case HAT:
                         item.setpX(player.getpX());
                         item.setpY(player.getpY()-item.getHeight());
                         player.jump(70);
                    case SHIELD:
                         item.setpX(player.getpX());
                         item.setpY(player.getpY());
                         item.setTimeShield(150);
                    case JETPACK:
                         item.setpX(player.getpX());
                         item.setpY(player.getpY());
                         player.jump(70);
                }
            }
        }
    }

    public void killEnemy(){
        for(Bullet b:bullets){
            if (collide(enemy,b)) {
                enemy.replace();
            }
        }
    }

    public boolean killedByEnemy(){
        if (collide(player, enemy) ) {
            if (player.getTake().getType()!=EnumItemType.SHIELD) {
                return true;
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


}
