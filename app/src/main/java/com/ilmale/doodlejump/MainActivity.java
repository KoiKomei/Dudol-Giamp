package com.ilmale.doodlejump;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.ilmale.doodlejump.database.ItemHandler;
import com.ilmale.doodlejump.settings.SettingsSI;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    SettingsSI settingsSI = SettingsSI.getInstance();
    AudioManager audioManager = AudioManager.getInstance();
    ItemHandler dataHandler=new ItemHandler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        audioManager.create(this);



//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public void initializeSettings(){
        final SharedPreferences pref = getSharedPreferences("AUDIO_PREF",this.MODE_PRIVATE);
        boolean musicInOn = pref.getBoolean("music_is_on", true);
        boolean soundIsOn = pref.getBoolean("sound_is_on", true);
        settingsSI.setMusic(musicInOn);
        settingsSI.setSound(soundIsOn);
        if(settingsSI.isMusic()){
            Log.d(LOG_TAG, "music off");
        }else{
            Log.d(LOG_TAG, "music on");
        }
        if(settingsSI.isSound()){
            Log.d(LOG_TAG, "sound off");
        }else{
            Log.d(LOG_TAG, "sound on");
        }
    }

    public void playMusic(){
        Log.d(LOG_TAG, "play music");
        audioManager.playBg_audio();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializeSettings();
        playMusic();
        Log.d(LOG_TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void launchOptionsActivity(View view) {
        Log.d(LOG_TAG, "Button options clicked!");
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
    }

    public void launchSinglePlayerActivity(View view) {
        Log.d(LOG_TAG, "Button single player clicked!");
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void launchMapsActivity(View view) {
        Log.d(LOG_TAG, "Button map clicked!");
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
