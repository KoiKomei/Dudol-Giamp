package com.ilmale.doodlejump.engine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.ilmale.doodlejump.AudioManager;
import com.ilmale.doodlejump.Constants;
import com.ilmale.doodlejump.EndGameActivity;
import com.ilmale.doodlejump.GameActivity;
import com.ilmale.doodlejump.Records;
import com.ilmale.doodlejump.RegisterActivity;
import com.ilmale.doodlejump.database.User;
import com.ilmale.doodlejump.domain.*;

import java.util.List;
import java.util.ArrayList;

@SuppressLint("Registered")
public class GameEngine {

    private static final String LOG_TAG = GameEngine.class.getSimpleName();

    // gameView will be the view of the game
    // and respond to screen touches as well
    //GameView gameView;
    protected long lastUpdate;

    List<AbstractGameObject> objects = new ArrayList<>();

    public int contatore; //contatore per operazioni di ottimizzazione

    public Player player;
    public List<Platform> platforms = new ArrayList<>();
    public List<Platform> platformsNearThePlayer = new ArrayList<>();
    public List<Bullet> bullets = new ArrayList<>();
    public Jetpack jetpack;
    public Enemy enemy;

    protected Constants constants = Constants.getInstance();
    protected Records records = Records.getInstance();
    protected AudioManager audioManager = AudioManager.getInstance();

    protected boolean gameOver = false;

    protected int jumpForce = 40;

    public GameEngine(){

        lastUpdate = System.currentTimeMillis();

        player = new Player(this);

        Platform platform = new Platform();
        platform.createRandomPlatform(platforms);
        player.setPlatforms(platforms);

        enemy = new Enemy();

        jetpack = new Jetpack();
        player.setJetpack(jetpack);

        constants.setPoints(0);

    }

    public void update() {
        player.update();

        for (Bullet b: bullets){
            b.update();
        }

        audioEnemy();
        audioJetpack();

        if(fall()){
            endGame();
        }

    }

    public void checkStatus(){

        for (Platform p: platformsNearThePlayer) {
            if (collidesFromAbove(player, p)){
                Log.d(LOG_TAG, "collision!");
                if (p.hasSprings()) {
                    player.jump(jumpForce * 3);
                    if(!player.hasJetpack()){
                        audioManager.playSprings_audio();
                    }
                }
                else {
                    player.jump(jumpForce);
                    if(!player.hasJetpack()){
                        audioManager.playJump_audio();
                    }
                }
            }
        }

        if (player.getpY() < constants.getPixelHeight()/3) {
            player.setpY(constants.getPixelHeight()/3);
            constants.setPoints(constants.getPoints() + 1);
            for(Platform p: platforms){
                p.setyS(-1);
                p.update();
            }
            enemy.setyS(-1);
            enemy.update();
            jetpack.setyS(-1);
            jetpack.update();
        }
        if (contatore == 0){
            aggiornaPiattaforme();
        }

        takeJetpack();
        killEnemy();
        killedByEnemy();

        if (contatore > 10) {
            contatore = 0;
        }
        contatore++;

    }

    public void updatePlayer() {
        player.updateControls();
    }

    protected boolean collide(AbstractGameObject obj1, AbstractGameObject obj2) {
        float x11 = obj1.getpX();
        float x12 = x11 + obj1.getWidth();
        float x21 = obj2.getpX();
        float x22 = x21 + obj2.getWidth();
        float y11 = obj1.getpY();
        float y12 = y11 + obj1.getHeight();
        float y21 = obj2.getpY();
        float y22 = y21 + obj2.getHeight();
        if(obj1 instanceof Player && obj2 instanceof Enemy ) {
            x11 += 30;
            x12 -= 30;
            y11 += 30;
            y12 -= 30;
        }
        if ((x11 >= x21 && x11 <= x22) || (x12 >= x21 && x12 <= x22)) {
            return (y11 >= y21 && y11 <= y22) || (y12 >= y21 && y12 <= y22);
        }
        return false;
    }

    protected boolean collidesFromAbove(AbstractGameObject obj1, AbstractGameObject obj2) {
        float x11 = obj1.getpX();
        float x12 = x11 + obj1.getWidth();
        float x21 = obj2.getpX();
        float x22 = x21 + obj2.getWidth();
        float y11 = obj1.getpY();
        float y12 = y11 + obj1.getHeight();
        float y21 = obj2.getpY();
        if (x11 + player.getWidth()/4 <= x22 && x12 - player.getWidth()/4 >= x21 ) {
            if (y12 >= y21-0.5 && y12 <= y21+0.5){
                return player.getVelY() >= 0;
            }
        }
        return false;
    }

    public boolean isOnScreen(AbstractGameObject object){
        float x1 = object.getpX();
        float x2 = x1+object.getWidth();
        float y1 = object.getpY();
        float y2 = y1+object.getHeight();
        if(y1<constants.getPixelHeight() && y2>0 && x2>0 && x1<constants.getPixelWidth()){
            return true;
        }
        return false;
    }

    public List<Platform> getPlatforms(){
        return platforms;
    }

    public void takeJetpack(){
        if (collide(player,jetpack)){
            if(!player.hasJetpack()){
                player.pickJetpack();
                jetpack.replace();
            }
        }
    }

    public void killEnemy(){

        for(Bullet b:bullets){
            if (collide(b,enemy)) {
                enemy.replace();
                audioManager.playKillEnemy_audio();
            }
        }
        if (collidesFromAbove(player,enemy)){
            enemy.replace();
            player.jump(jumpForce);
            audioManager.playJumpOnEnemy_audio();
        }
    }

    public void killedByEnemy(){
        if(!player.hasJetpack()){
            if(!collidesFromAbove(player,enemy)){
                if (collide(player, enemy)){
                    endGame();
                }
            }
        }
    }


    public boolean fall(){
        if((player.getpY()>constants.getPixelHeight() && constants.getPoints()>0 )){
            return true;
        }
        if((player.getpY()>constants.getPixelHeight() && constants.getPoints()==0 )){
            player.jump(jumpForce*2);
        }
        return false;
    }

    public void shoot() {
        Bullet bullet = new Bullet(player.getpX()+53, player.getpY());
        bullets.add(bullet);
        audioManager.playBullet_audio();
    }

    public void endGame(){
        if(!gameOver){
            audioManager.playLose_audio();
        }
        gameOver=true;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void aggiornaPiattaforme(){
        for (Platform p: platforms){
            if (p.getpY() >= player.getpY() - 1000 || p.getpY() <= player.getpY() + 1000){
                platformsNearThePlayer.add(p);
            }
            else if (platformsNearThePlayer.contains(p)){
                platformsNearThePlayer.remove(p);
            }
        }
    }

    public void audioEnemy(){
        if(isOnScreen(enemy)){
            audioManager.playEnemy_audio();
        }else{
            audioManager.pauseEnemy_audio();
        }
    }

    public void audioJetpack(){
        if(player.hasJetpack()){
            audioManager.playJetpack_audio();
        }else{
            audioManager.pauseJetpack_audio();
        }
    }

}
