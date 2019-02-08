package com.ilmale.doodlejump;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ilmale.doodlejump.domain.LoginUser;
import com.ilmale.doodlejump.domain.MyLocation;

public class EndGameMultiActivity extends AppCompatActivity {

    private static final String LOG_TAG = EndGameMultiActivity.class.getSimpleName();

    public Button playAgain, exit;
    public TextView text;

    private Constants constants = Constants.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game_multi);

        playAgain = findViewById(R.id.play_again_multi);
        exit = findViewById(R.id.exit_multi);
        text = findViewById(R.id.text_multi);
        if(constants.isLoseInMulti()){
            text.setText("YOU LOSE");
            constants.setLoseInMulti(false);
        }else{
            text.setText("YOU WIN");
        }

    }

    public void launchMultiPlayerActivity(View view) {
        Intent intent = new Intent(this, MultiActivity.class);
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
