package com.ilmale.doodlejump;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ilmale.doodlejump.database.OurDatabase;
import com.ilmale.doodlejump.database.User;
import com.ilmale.doodlejump.domain.LoginUser;
import com.ilmale.doodlejump.domain.MyLocation;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private GoogleMap mMap;
    private MainActivity mainActivity;
    MyLocation myLocation = MyLocation.getInstance();
    private String player;
    private int points;
    Records records = Records.getInstance();
    private List<User> users;
    private LoginUser loginUser = LoginUser.getInstance();


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

    public void setMainActivity(Context context){
        mainActivity = (MainActivity) context;
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

        player = "MY RECORD";
        points = records.getRecords().get(0);

        if(loginUser.getEmail()!=null){
            player = loginUser.getUsername();
            points = loginUser.getPunteggio();
        }

        if(myLocation.getLatLng()!=null){
            LatLng myPosition = myLocation.getLatLng();
            MarkerOptions marker = new MarkerOptions().position(myPosition).title(player+":"+points);
            mMap.addMarker(marker);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myPosition));
        }

        for(User u:users){
            Log.d(LOG_TAG, "Username:" + u.getUsername() + "Email:" + u.getEmail() + "Punteggio:" + u.getPunteggio() +"Lat:" + u.getLat() +"Longi:" + u.getLongi());
        }
        for(User u:users){
            if(u.getLat()!=0.0 && u.getLongi()!=0.0 && u.getPunteggio()!=0 && !u.getEmail().equalsIgnoreCase(loginUser.getEmail())){
                player = u.getUsername();
                points = u.getPunteggio();
                LatLng position = new LatLng((float) u.getLat(), (float) u.getLat());
                MarkerOptions marker = new MarkerOptions().position(position).title(player+":"+points);
                mMap.addMarker(marker);
           }
        }

    }

}
