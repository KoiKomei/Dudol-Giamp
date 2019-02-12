package com.ilmale.doodlejump;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ilmale.doodlejump.database.OurDatabase;
import com.ilmale.doodlejump.database.User;
import com.ilmale.doodlejump.domain.LoginUser;
import com.ilmale.doodlejump.domain.MyLocation;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String LOG_TAG = MapsActivity.class.getSimpleName();
    private static final int LOCATION_UPDATE_INTERVAL = 10000;

    private GoogleMap mMap = null;
    private Marker myMarker;
    private MyLocation myLocation = MyLocation.getInstance();
    private String player;
    private int points;
    private Records records = Records.getInstance();
    private LoginUser loginUser = LoginUser.getInstance();
    private AudioManager audioManager = AudioManager.getInstance();
    private Handler mHandler = new Handler();
    private Runnable mRunnable;

    private FirebaseFirestore fs= FirebaseFirestore.getInstance();
    private CollectionReference use=fs.collection("User");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        audioManager.playBg_audio();
        startUserLocationsRunnable();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(audioManager.isCanStopBgAudio()){
            audioManager.pauseBg_audio();
        }
        audioManager.setCanStopBgAudio(true);
        stopLocationUpdates();
    }

    @Override
    public void onBackPressed() {
        audioManager.setCanStopBgAudio(false);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
            myMarker = mMap.addMarker(new MarkerOptions().position(myPosition).title(player+":"+points)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myPosition));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myPosition.latitude, myPosition.longitude), 13.6f));
        }

        use.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        User u = d.toObject(User.class);
                        if(u.getLat()!=0.0 && u.getLongi()!=0.0 && u.getPunteggio()!=0 && !u.getEmail().equalsIgnoreCase(loginUser.getEmail())){
                            player = u.getUsername();
                            points = u.getPunteggio();
                            LatLng position = new LatLng(u.getLat(), u.getLongi());
                            MarkerOptions marker = new MarkerOptions().position(position).title(player+":"+points);
                            mMap.addMarker(marker);
                        }
                    }
                }
            }
        });
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
        LatLng latLng = new LatLng(myLocation.getLatLng().latitude, myLocation.getLatLng().longitude);
        animateMarker(myMarker, latLng, false);
        Log.d(LOG_TAG, "Location Marker:"+myMarker.getPosition().latitude+" "+myMarker.getPosition().longitude);
    }

    private void stopLocationUpdates(){
        mHandler.removeCallbacks(mRunnable);
    }

    public void animateMarker(final Marker marker, final LatLng toPosition, final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = mMap.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 500;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }
}
