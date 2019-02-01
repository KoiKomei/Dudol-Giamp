package com.ilmale.doodlejump;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.ilmale.doodlejump.database.ItemHandler;
import com.ilmale.doodlejump.domain.LoginUser;
import com.ilmale.doodlejump.domain.MyLocation;
import com.ilmale.doodlejump.settings.SettingsSI;

import static com.ilmale.doodlejump.Constants.ERROR_DIALOG_REQUEST;
import static com.ilmale.doodlejump.Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.ilmale.doodlejump.Constants.PERMISSIONS_REQUEST_ENABLE_GPS;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    SettingsSI settingsSI = SettingsSI.getInstance();
    AudioManager audioManager = AudioManager.getInstance();
    MyLocation myLocation = MyLocation.getInstance();
    Records records = Records.getInstance();
    LoginUser loginUser = LoginUser.getInstance();
    ItemHandler dataHandler = new ItemHandler();

    private boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient mFusedLocationClient;

    private Button account;

    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        account = this.findViewById(R.id.button_account);
        checkLogin();
        audioManager.create(this);
        records.initializeRecords(this);
        for(Integer i: records.getRecords()){
            Log.d(LOG_TAG,"Punteggio "+i);
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        setDimension();
    }

    private void checkLogin() {
        loginUser.initializeLoginUser(this);
        if(loginUser.getEmail()!=null){
            account.setText(loginUser.getUsername());
            loginUser.initializeBobEquipped();
        }
        else{
            account.setText(R.string.register);
        }
    }

    public void setDimension(){
        Constants constants = Constants.getInstance();
        constants.setPixelHeight(getResources().getDisplayMetrics().heightPixels);
        constants.setPixelWidth(getResources().getDisplayMetrics().widthPixels);
        Log.d(LOG_TAG, "Width: "+constants.getPixelWidth()+ ", Height:" +constants.getPixelHeight());

    }

    public void getLastKnownLocation() {
        Log.d(LOG_TAG, "getLastKnownLocation: called.");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if(location!=null){
                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    myLocation.setLatLng(latLng);
                    Log.d(LOG_TAG, "OnComplete: latitude: "+ latLng.latitude);
                    Log.d(LOG_TAG, "OnComplete: longitude: "+ latLng.longitude);
                }

            }
        });
    }

    private boolean checkMapServices(){
        if(isServicesOK()){
            if(isMapsEnabled()){
                return true;
            }
        }
        return false;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean isMapsEnabled(){
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            getLastKnownLocation();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public boolean isServicesOK(){
        Log.d(LOG_TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(LOG_TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(LOG_TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(LOG_TAG, "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if(mLocationPermissionGranted){
                    getLastKnownLocation();
                }
                else{
                    getLocationPermission();
                }
            }
        }

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
        checkLogin();
        initializeSettings();
        playMusic();
        if(checkMapServices()){
            if(mLocationPermissionGranted){
                getLastKnownLocation();
            }
            else{
                getLocationPermission();
            }
        }
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

    public void launchRegisterActivity(View view){
        Log.d(LOG_TAG, "Register clicked!");
        Intent intent=new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void launchScoreboardActivity(View view){
        Log.d(LOG_TAG, "Scoreboard clicked!");
        Intent intent=new Intent(this, ScoreActivity.class);
        startActivity(intent);
    }

    public void launchShopActivity(View view){
        Log.d(LOG_TAG, "Scoreboard clicked!");
        Intent intent=new Intent(this, ShopActivity.class);
        startActivity(intent);
    }

}
