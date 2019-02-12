package com.ilmale.doodlejump;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ilmale.doodlejump.ChainScore.HandlerScore1;
import com.ilmale.doodlejump.ChainScore.HandlerScore100;
import com.ilmale.doodlejump.ChainScore.HandlerScore1000;
import com.ilmale.doodlejump.ChainScore.HandlerScore30;
import com.ilmale.doodlejump.ChainScore.HandlerScoreWorld;
import com.ilmale.doodlejump.domain.LoginUser;
import com.ilmale.doodlejump.domain.MyLocation;

public class EndGameActivity extends AppCompatActivity {

    private static final String LOG_TAG = EndGameActivity.class.getSimpleName();

    public EditText name;
    public ImageButton save, playAgain, menu;
    public TextView points, textPoints;

    private LoginUser loginUser = LoginUser.getInstance();
    private MyLocation myLocation = MyLocation.getInstance();
    private Records records = Records.getInstance();
    private Constants constants = Constants.getInstance();
    private AudioManager audioManager = AudioManager.getInstance();

    private boolean clickedUR;
    private boolean clickedLM;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        clickedUR=false;
        clickedLM=false;

        name = findViewById(R.id.nameText);
        save = findViewById(R.id.save);
        playAgain = findViewById(R.id.play_again);
        menu = findViewById(R.id.menu);
        points = findViewById(R.id.points);
        points.setText(""+constants.getPoints());
        textPoints = findViewById(R.id.textPoints);

        if(myLocation.getLatLng()!=null){
            textPoints.setText(checkScore());
        }
        if(loginUser.getEmail()!=null){
            name.setText(loginUser.getUsername().substring(0,7));
        }
        constants.setName(name.getText().toString().substring(0,7));

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
        audioManager.pauseLose_audio();
    }

    public void updateRecords(View view) {
        if(!clickedUR) {
            clickedUR=true;
            constants.setName(name.getText().toString());
            records.updateRecords();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void launchSinglePlayerActivity(View view) {
        audioManager.setCanStopBgAudio(false);
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void launchMainActivity(View view) {
        if(!clickedLM) {
            audioManager.setCanStopBgAudio(false);
            clickedLM=true;
            constants.setName(name.getText().toString());
            records.updateRecords();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        audioManager.setCanStopBgAudio(false);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private String checkScore(){
        HandlerScoreWorld handlerScoreWorld = HandlerScoreWorld.getInstance();
        return handlerScoreWorld.processScore(constants.getPoints());
    }
}
