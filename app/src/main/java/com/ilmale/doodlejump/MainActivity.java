package com.ilmale.doodlejump;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
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
import com.ilmale.doodlejump.ChainScore.HandlerScoreWorld;
import com.ilmale.doodlejump.database.ItemHandler;
import com.ilmale.doodlejump.domain.LoginUser;
import com.ilmale.doodlejump.domain.MyLocation;
import com.ilmale.doodlejump.services.LocationService;
import com.ilmale.doodlejump.settings.SettingsSI;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.ilmale.doodlejump.Utility.ERROR_DIALOG_REQUEST;
import static com.ilmale.doodlejump.Utility.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.ilmale.doodlejump.Utility.PERMISSIONS_REQUEST_ENABLE_GPS;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    SettingsSI settingsSI = SettingsSI.getInstance();
    Utility utility = Utility.getInstance();
    AudioManager audioManager = AudioManager.getInstance();
    MyLocation myLocation = MyLocation.getInstance();
    Records records = Records.getInstance();
    LoginUser loginUser = LoginUser.getInstance();
    ItemHandler dataHandler = new ItemHandler();
    MyAlertDialog myAlertDialog = MyAlertDialog.getInstance();
    HandlerScoreWorld handlerScoreWorld = HandlerScoreWorld.getInstance();

    private boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient mFusedLocationClient;

    JSONObject data = null;
    public static String weather = "";

    private Button account;

    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        account = this.findViewById(R.id.button_account);

        // initialize settings and dimension of the screen
        initializeSettings();
        setDimension();

        //initialize with the context an alert dialog for best score check and set cont for the alert dialog
        myAlertDialog.setContext(this);
        utility.setContAlert(0);

        //initialize all the audio and sound
        audioManager.create(this);

        //initialize scores of scoreboard
        records.initializeRecords(this);

        //check if user is logged
        checkLogin();

        //initialize the FusedLocationClient to get position
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    private void checkLogin() {
        loginUser.initializeLoginUser(this);
        if(loginUser.getEmail()!=null){
            account.setText(loginUser.getUsername());
        }
        else{
            account.setText(R.string.register);
        }
    }

    public void setDimension(){
        utility.setPixelHeight(getResources().getDisplayMetrics().heightPixels);
        utility.setPixelWidth(getResources().getDisplayMetrics().widthPixels);
        Log.d(LOG_TAG, "Width: "+utility.getPixelWidth()+ ", Height:" +utility.getPixelHeight());
    }

    public void initializeSettings(){
        final SharedPreferences pref = getSharedPreferences("SETTINGS_PREF", MODE_PRIVATE);
        boolean musicInOn = pref.getBoolean("music_is_on", true);
        boolean soundIsOn = pref.getBoolean("sound_is_on", true);
        boolean weatherIsOn = pref.getBoolean("weather_is_on", true);
        settingsSI.setMusic(musicInOn);
        settingsSI.setSound(soundIsOn);
        settingsSI.setWeatherCondition(weatherIsOn);
        if(settingsSI.isMusic()){
            Log.d(LOG_TAG, "music on");
        }else{
            Log.d(LOG_TAG, "music off");
        }
        if(settingsSI.isSound()){
            audioManager.setVolume1();
            Log.d(LOG_TAG, "sound on");
        }else{
            audioManager.setVolume0();
            Log.d(LOG_TAG, "sound off");
        }
        if(settingsSI.isWeatherCondition()){
            Log.d(LOG_TAG, "weather on");
        }else{
            Log.d(LOG_TAG, "weather off");
        }
    }

    /* functions to handle position */
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
                    Log.d(LOG_TAG, "OnComplete: latitude: " + latLng.latitude + " OnComplete: longitude: " + latLng.longitude);
                    startLocationService();
                    if(settingsSI.isWeatherCondition()){
                        getJSON(latLng);
                    }
                }
            }
        });
    }

    private boolean checkMapServices(){
        if(isServicesOK()){
            return isMapsEnabled();
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
            if(!utility.isAskedPosition()) {
                buildAlertMessageNoGps();
                utility.setAskedPosition(true);
            }
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
    /* end of location handle */

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        utility.setContAlert(0);
        checkLogin();
        initializeSettings();
        audioManager.playBg_audio();
        if (checkMapServices()) {
            if (mLocationPermissionGranted) {
                getLastKnownLocation();
            } else {
                getLocationPermission();
            }
        }

        Log.d(LOG_TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        utility.setContAlert(3);
        if(audioManager.isCanStopBgAudio()){
            audioManager.pauseBg_audio();
        }
        audioManager.setCanStopBgAudio(true);
        Log.d(LOG_TAG, "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    @Override
    public void onWindowFocusChanged(boolean value){
        super.onWindowFocusChanged(value);
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

    @Override
    public void onBackPressed() { }

    public void launchOptionsActivity(View view) {
        Log.d(LOG_TAG, "Button options clicked!");
        audioManager.setCanStopBgAudio(false);
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
    }

    public void launchSinglePlayerActivity(View view) {
        Log.d(LOG_TAG, "Button single player clicked!");
        audioManager.setCanStopBgAudio(false);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void launchMultiPlayerActivity(View view) {
        Log.d(LOG_TAG, "Button multi player clicked!");
        audioManager.setCanStopBgAudio(false);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent = new Intent(this, MultiActivity.class);
        startActivity(intent);
    }

    public void launchMapsActivity(View view) {
        Log.d(LOG_TAG, "Button map clicked!");
        if(checkMapServices()){
            if(mLocationPermissionGranted){
                getLastKnownLocation();
            }
            else{
                getLocationPermission();
            }
        }
        audioManager.setCanStopBgAudio(false);
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);
    }

    public void launchRegisterActivity(View view){
        Log.d(LOG_TAG, "Register clicked!");
        audioManager.setCanStopBgAudio(false);
        Intent intent=new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void launchScoreboardActivity(View view){
        Log.d(LOG_TAG, "Scoreboard clicked!");
        audioManager.setCanStopBgAudio(false);
        Intent intent=new Intent(this, ScoreActivity.class);
        startActivity(intent);
    }

    public void launchShopActivity(View view){
        Log.d(LOG_TAG, "Scoreboard clicked!");
        audioManager.setCanStopBgAudio(false);
        Intent intent=new Intent(this, ShopActivity.class);
        startActivity(intent);
    }

    //start the service to get location every 10 seconds
    private void startLocationService(){
        Intent serviceIntent = new Intent(this, LocationService.class);
        startService(serviceIntent);
    }

    //function that use a weather api for the background
    @SuppressLint("StaticFieldLeak")
    public void getJSON(final LatLng latLng) {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected Void doInBackground(Void... params) {
                try {

                    URL url = new URL("http://api.openweathermap.org/data/2.5/weather?lat="+latLng.latitude+"&lon="+latLng.longitude+"&APPID=ea574594b9d36ab688642d5fbeab847e");

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    StringBuffer json = new StringBuffer(1024);
                    String tmp = "";

                    while((tmp = reader.readLine()) != null)
                        json.append(tmp).append("\n");
                    reader.close();

                    data = new JSONObject(json.toString());
                    JSONArray arrayWeather =  data.getJSONArray("weather");
                    JSONObject wObject = arrayWeather.getJSONObject(0);
                    weather = wObject.getString("main");

                    if(data.getInt("cod") != 200) {
                        System.out.println("Cancelled");
                        return null;
                    }

                } catch (Exception e) {
                    System.out.println("Exception "+ e.getMessage());
                    return null;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void Void) {
                if(data!=null){
                    Log.d("my weather received",weather);
                }

            }
        }.execute();

    }

}
