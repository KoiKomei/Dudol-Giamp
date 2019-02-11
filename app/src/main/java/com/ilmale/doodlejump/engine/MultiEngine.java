package com.ilmale.doodlejump.engine;

import com.ilmale.doodlejump.Constants;
import com.ilmale.doodlejump.domain.Bullet;
import com.ilmale.doodlejump.domain.Platform;
import com.ilmale.doodlejump.domain.RemotePlayer;
import com.ilmale.doodlejump.network.Client;

public class MultiEngine extends GameEngine {

    public RemotePlayer player2;
    public Client client;
    public boolean isStart = false;

    private Constants constants = Constants.getInstance();

    public MultiEngine(){
        super();

        player2 = new RemotePlayer();
        client = new Client(player, player2);
        client.start();

    }

    public RemotePlayer getPlayer2() {
        return player2;
    }

    public void setPlayer2(RemotePlayer player2) {
        this.player2 = player2;
    }

    @Override
    public void update() {
        //Log.d(LOG_TAG, "updating gameengine");
        if(isStart) {
            player.update();
            //player2.update();

            audioEnemy();
            audioJetpack();

            if (fall()) {
                endGame();
            }
        }
        else {

            for (Bullet b : bullets) {
                b.update();
            }

            if(player2.pY!=0 && player2.pX!=0){
                isStart=true;
            }
        }
    }

    @Override
    public void endGame(){
        if(!gameOver){
            audioManager.playLose_audio();
        }
        constants.setLoseInMulti(true);
        client.setIsGameOver(true);
        gameOver = true;
        isStart = false;
        //c.setIsGameOver(true);
    }

    public boolean isStart() {
        return isStart;
    }

}
