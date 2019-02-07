package com.ilmale.doodlejump.engine;

import com.ilmale.doodlejump.domain.Bullet;
import com.ilmale.doodlejump.domain.Platform;
import com.ilmale.doodlejump.domain.RemotePlayer;
import com.ilmale.doodlejump.network.Client;

public class MultiEngine extends GameEngine {

    public RemotePlayer player2;
    public Client client;

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

        player.update();
        //player2.update();

        for (Bullet b: bullets){
            b.update();
        }

        audioEnemy();
        audioJetpack();

        if(fall()){
            endGame();
        }
    }

    @Override
    public void endGame(){
        if(!gameOver){
            audioManager.playLose_audio();
        }
        client.setIsGameOver(true);
        gameOver = true;
        //c.setIsGameOver(true);
    }

}
