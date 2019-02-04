package com.ilmale.doodlejump;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ilmale.doodlejump.database.OurDatabase;
import com.ilmale.doodlejump.database.User;
import com.ilmale.doodlejump.domain.LoginUser;
import com.ilmale.doodlejump.domain.MyLocation;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String LOG_TAG = MapsActivity.class.getSimpleName();
    private static final int LOCATION_UPDATE_INTERVAL = 3000;

    private GoogleMap mMap = null;
    private MarkerOptions myMarker;
    MyLocation myLocation = MyLocation.getInstance();
    private String player;
    private int points;
    Records records = Records.getInstance();
    private List<User> users;
    private LoginUser loginUser = LoginUser.getInstance();
    private Handler mHandler = new Handler();
    private Runnable mRunnable;

    public static OurDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        db = Room.databaseBuilder(this, OurDatabase.class,"userdb").allowMainThreadQueries().build();
        users = db.ourDao().getUsers();

    }

    @Override
    protected void onResume() {
        super.onResume();
        startUserLocationsRunnable();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(loginUser.getEmail()!=null){
            player = loginUser.getUsername();
            points = loginUser.getPunteggio();
        }else{
            player = records.getSRecords().get(0);
            points = records.getRecords().get(0);
        }

        if(myLocation.getLatLng()!=null){
            LatLng myPosition = myLocation.getLatLng();
            myMarker = new MarkerOptions().position(myPosition).title(player+":"+points);
            myMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            mMap.addMarker(myMarker);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myPosition));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myPosition.latitude, myPosition.longitude), 13.6f));
        }
        for(User u:users){
            if(u.getLat()!=0.0 && u.getLongi()!=0.0 && u.getPunteggio()!=0 && !u.getEmail().equalsIgnoreCase(loginUser.getEmail())){
                player = u.getUsername();
                points = u.getPunteggio();
                LatLng position = new LatLng((float) u.getLat(), (float) u.getLongi());
                MarkerOptions marker = new MarkerOptions().position(position).title(player+":"+points);
                mMap.addMarker(marker);
            }
        }
    }
    
    private void startUserLocationsRunnable(){
        Log.d(LOG_TAG, "startUserLocationsRunnable: starting runnable for retrieving updated locations.");
        mHandler.postDelayed(mRunnable = new Runnable() {
            @Override
            public void run() {
                setUpdateMarker();
                mHandler.postDelayed(mRunnable, LOCATION_UPDATE_INTERVAL);
            }
        }, LOCATION_UPDATE_INTERVAL);
    }

    public void setUpdateMarker() {
        myMarker.position(new LatLng(myLocation.getLatLng().latitude, myLocation.getLatLng().longitude));
        Log.d(LOG_TAG, "latitude: "+ myLocation.getLatLng().latitude);
        Log.d(LOG_TAG, "longitude: "+ myLocation.getLatLng().longitude);
    }

    private void stopLocationUpdates(){
        mHandler.removeCallbacks(mRunnable);
    }
}
