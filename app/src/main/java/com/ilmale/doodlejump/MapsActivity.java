package com.ilmale.doodlejump;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.ilmale.doodlejump.database.Negozio;
import com.ilmale.doodlejump.database.OurDao;
import com.ilmale.doodlejump.database.Possiede;
import com.ilmale.doodlejump.database.User;
import com.ilmale.doodlejump.domain.MyLocation;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MainActivity mainActivity;
    MyLocation myLocation = MyLocation.getInstance();
    private String player;
    private int points;
    private List<Marker> markers;
    //private List<User> users;
    //private OurDao ourDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //users = ourDao.getUsers();

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

        // Add a marker in Sydney and move the camera
        player = "123456789";
        points = 999999;

        LatLng myPosition = myLocation.getLatLng();
        MarkerOptions marker = new MarkerOptions().position(myPosition).title(player+":"+points);
        mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myPosition));


        /*
            for(User u:users){
                int point = ourDao.getPoints(u.getId());
                LatLng position = ourDao.getPosition(u.getId());
                MarkerOptions marker = new MarkerOptions().position(myPosition).title(player+":"+points);
                mMap.addMarker(marker);
                markers.add(marker);
            }
         */
    }

}
