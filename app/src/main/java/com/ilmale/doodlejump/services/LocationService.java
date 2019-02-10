package com.ilmale.doodlejump.services;

import android.Manifest;
import android.app.IntentService;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ilmale.doodlejump.Constants;
import com.ilmale.doodlejump.MyAlertDialog;
import com.ilmale.doodlejump.database.OurDatabase;
import com.ilmale.doodlejump.database.User;
import com.ilmale.doodlejump.domain.LoginUser;
import com.ilmale.doodlejump.domain.MyLocation;

import java.util.ArrayList;
import java.util.List;

public class LocationService extends IntentService {

    private static final String TAG = "LocationService";

    private FusedLocationProviderClient mFusedLocationClient;

    public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;

    private List<User> users = new ArrayList<>();

    LoginUser loginUser = LoginUser.getInstance();
    MyLocation myLocation = MyLocation.getInstance();
    MyAlertDialog myAlertDialog = MyAlertDialog.getInstance();
    Constants constants = Constants.getInstance();

    private FirebaseFirestore fs= FirebaseFirestore.getInstance();
    private CollectionReference use=fs.collection("User");

    public LocationService() {
        super("location service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    private void getLocation() {

        // ---------------------------------- LocationRequest ------------------------------------
        // Create the location request to start receiving updates
        LocationRequest mLocationRequestHighAccuracy = new LocationRequest();
        mLocationRequestHighAccuracy.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequestHighAccuracy.setInterval(10000);
        mLocationRequestHighAccuracy.setFastestInterval(10000);


        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "getLocation: stopping the location service.");
            stopSelf();
            return;
        }
        Log.d(TAG, "getLocation: getting location information.");
        mFusedLocationClient.requestLocationUpdates(mLocationRequestHighAccuracy, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {

                        Log.d(TAG, "onLocationResult: got location result.");

                        Location location = locationResult.getLastLocation();
                        if (location != null) {
                            constants.setContAlert(constants.getContAlert()+1);
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            Log.d(TAG, "OnComplete: latitude: " + latLng.latitude);
                            Log.d(TAG, "OnComplete: longitude: " + latLng.longitude);
                            myLocation.setLatLng(latLng);
                            if(constants.getContAlert()==2){
                                checkPoints();
                            }
                        }
                    }
                },
                Looper.myLooper()); // Looper.myLooper tells this to repeat forever until thread is destroyed
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        getLocation();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {}

    @Override
    public void onCreate() {
        super.onCreate();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        use.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        User user = d.toObject(User.class);
                        users.add(user);
                    }
                }
            }
        });
    }

    private void checkPoints() {
        int pos=0, i=0, max=0;
        for(User u:users) {
            if (u.getLat() != 0.0 && u.getLongi() != 0.0 && u.getPunteggio() != 0 && !u.getEmail().equalsIgnoreCase(loginUser.getEmail())) {
                int distance = calculateDistanceInKilometer(myLocation.getLatLng().latitude, myLocation.getLatLng().longitude, u.getLat(), u.getLongi());
                if(u.getPunteggio()>max && distance<30){
                    max=u.getPunteggio();
                    pos=i;
                }
            }
            i++;
        }
        if (max > loginUser.getPunteggio() && loginUser.getEmail()!=null) {
            myAlertDialog.notifyToPlay(users.get(pos));
        }

    }

    private int calculateDistanceInKilometer(double userLat, double userLng, double venueLat, double venueLng) {

        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(venueLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH_KM * c));
    }

}
