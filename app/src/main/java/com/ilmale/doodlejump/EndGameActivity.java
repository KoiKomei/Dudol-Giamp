package com.ilmale.doodlejump;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ilmale.doodlejump.domain.LoginUser;

public class EndGameActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    public EditText name;
    public Button save, playAgain, exit;
    public TextView points;

    private LoginUser loginUser = LoginUser.getInstance();
    private Records records = Records.getInstance();
    private Constants constants = Constants.getInstance();

    boolean clicked;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        clicked=false;

        name = findViewById(R.id.nameText);
        save = findViewById(R.id.save);
        playAgain = findViewById(R.id.play_again);
        exit = findViewById(R.id.exit);
        points = findViewById(R.id.points);
        points.setText(""+constants.getPoints());
        if(loginUser.getEmail()!=null){
            name.setText(loginUser.getUsername());
        }
        constants.setName(name.getText().toString());

    }
    public void updateRecords(View view) {
        if(!clicked) {
            clicked=true;
            constants.setName(name.getText().toString());
            records.updateRecords();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void launchSinglePlayerActivity(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void launchMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
