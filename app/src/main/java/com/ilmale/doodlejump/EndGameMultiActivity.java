package com.ilmale.doodlejump;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * ACTIVITY PER LA FINE DEL MULTIPLAYER
 * DICHIARA SE HAI VINTO O PERSO
 * IN PIù SE VUOI RIGIOCARE O MENO
 *
 *
 * */

public class EndGameMultiActivity extends AppCompatActivity {

    private static final String LOG_TAG = EndGameMultiActivity.class.getSimpleName();

    public ImageButton playAgain, menu;
    public TextView text;

    private Utility utility = Utility.getInstance();
    private AudioManager audioManager = AudioManager.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game_multi);

        playAgain = findViewById(R.id.play_again_multi);
        menu = findViewById(R.id.menu_multi);
        text = findViewById(R.id.text_multi);

        if(utility.isLoseInMulti()){
            text.setText("YOU LOSE");
            utility.setLoseInMulti(false);
        }else{
            text.setText("YOU WIN");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        audioManager.playBg_audio();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(audioManager.isCanStopBgAudio()){
            audioManager.pauseBg_audio();
        }
        audioManager.setCanStopBgAudio(true);
    }

    public void launchMultiPlayerActivity(View view) {
        audioManager.setCanStopBgAudio(false);
        Intent intent = new Intent(this, MultiActivity.class);
        startActivity(intent);
    }

    public void launchMainActivity(View view) {
        audioManager.setCanStopBgAudio(false);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        audioManager.setCanStopBgAudio(false);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
