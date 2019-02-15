package com.ilmale.doodlejump;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

//activity that shows the best 5 scores on the device with their respective name

public class ScoreActivity extends AppCompatActivity {

    private Records records = Records.getInstance();
    private TextView textView;
    private ImageButton menu;
    private AudioManager audioManager = AudioManager.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        textView = findViewById(R.id.scoreText);
        menu = findViewById(R.id.menu_score);
        for(int i=0; i<5; i++){
            textView.setText(textView.getText()+"\n"+(i+1)+". "+records.getSRecords().get(i)+" - "+records.getRecords().get(i));
        }
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchMainActivity(view);
            }
        });
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

    public void launchMainActivity(View view) {
        audioManager.setCanStopBgAudio(false);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void launchWorldActivity(View view){
        Intent intent = new Intent(this, WorldRecord.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        audioManager.setCanStopBgAudio(false);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
