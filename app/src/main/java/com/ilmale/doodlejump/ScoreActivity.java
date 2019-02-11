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

public class ScoreActivity extends AppCompatActivity {

    private Records records = Records.getInstance();
    private TextView textView;
    private ImageButton menu;


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

    public void launchMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
