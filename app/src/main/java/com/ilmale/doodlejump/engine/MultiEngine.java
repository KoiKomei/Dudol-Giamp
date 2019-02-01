package com.ilmale.doodlejump.engine;

import com.ilmale.doodlejump.domain.Bullet;
import com.ilmale.doodlejump.domain.Platform;
import com.ilmale.doodlejump.domain.RemotePlayer;
import com.ilmale.doodlejump.network.Client;

public class MultiEngine extends GameEngine {

    public RemotePlayer player2;
    public Client c;

    public MultiEngine(){
        super();

        player2 = new RemotePlayer();
        Client client = new Client(player, player2);
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

        for (Platform p: platformsNearThePlayer) {
            if (collidesFromAbove(player, p)){
                //Log.d(LOG_TAG, "collision!");
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

        for (Bullet b: bullets){
            b.update();
        }

        if (player.getpY() < constants.getPixelHeight()/3) {
            player.setpY(constants.getPixelHeight()/3);
            for(Platform p: platforms){
                p.setyS(player.getyS());
                p.update();
            }
            enemy.setyS((player.getyS()));
            enemy.update();
            jetpack.setyS((player.getyS()));
            jetpack.update();
        }
        if (contatore == 0){
            aggiornaPiattaforme();
        }

        audioEnemy();
        audioJetpack();
        takeJetpack();
        killEnemy();

        if(isDeath()){
            endGame();
        }

        if (contatore > 10) {
            contatore = 0;
        }
        contatore++;
    }

}
