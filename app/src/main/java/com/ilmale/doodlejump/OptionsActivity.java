package com.ilmale.doodlejump;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;

import com.ilmale.doodlejump.settings.SettingsSI;

import static android.media.tv.TvContract.Programs.Genres.MUSIC;

public class OptionsActivity extends AppCompatActivity {

    private static final String LOG_TAG = OptionsActivity.class.getSimpleName();
    // initiate Switch
    Switch musicSwitch;
    Switch soundSwitch;
    Switch weatherSwitch;
    // initiate Button
    ImageButton save, menu;

    SettingsSI settingsSI = SettingsSI.getInstance();
    AudioManager audioManager = AudioManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        final SharedPreferences pref = getSharedPreferences("SETTINGS_PREF",this.MODE_PRIVATE);
        boolean musicInOn = pref.getBoolean("music_is_on", true);
        boolean soundIsOn = pref.getBoolean("sound_is_on", true);
        boolean weatherIsOn = pref.getBoolean("weather_is_on", true);
        settingsSI.setMusic(musicInOn);
        settingsSI.setSound(soundIsOn);
        settingsSI.setWeatherCondition(weatherIsOn);

        final SharedPreferences.Editor editorPref = pref.edit();

        // initiate Switch
        musicSwitch = (Switch) findViewById(R.id.MUSIC);
        soundSwitch = (Switch) findViewById(R.id.SOUND);
        weatherSwitch = (Switch) findViewById(R.id.WEATHER);
        musicSwitch.setChecked(settingsSI.isMusic());
        soundSwitch.setChecked(settingsSI.isSound());
        weatherSwitch.setChecked(settingsSI.isWeatherCondition());

        // initiate Button
        save = findViewById(R.id.saveButton);
        menu = findViewById(R.id.menu_option);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (musicSwitch.isChecked()){
                    settingsSI.setMusic(true);
                    audioManager.playBg_audio();
                    Log.d(LOG_TAG, "music on");

                }
                else{
                    settingsSI.setMusic(false);
                    audioManager.playBg_audio();
                    Log.d(LOG_TAG, "music off");
                }
                if (soundSwitch.isChecked()){
                    settingsSI.setSound(true);
                    Log.d(LOG_TAG, "sound on");
                }
                else{
                    settingsSI.setSound(false);
                    Log.d(LOG_TAG, "sound off");
                }
                if (weatherSwitch.isChecked()){
                    settingsSI.setWeatherCondition(true);
                    Log.d(LOG_TAG, "weather on");
                }
                else{
                    settingsSI.setWeatherCondition(false);
                    Log.d(LOG_TAG, "weather off");
                }

                editorPref.putBoolean("music_is_on",musicSwitch.isChecked());
                editorPref.putBoolean("sound_is_on",soundSwitch.isChecked());
                editorPref.putBoolean("weather_is_on",weatherSwitch.isChecked());
                editorPref.commit();


            }
        });

    }

    public void launchMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
