package com.ilmale.doodlejump.services;

import android.Manifest;
import android.animation.PropertyValuesHolder;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;

import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.ilmale.doodlejump.GameActivity;
import com.ilmale.doodlejump.MainActivity;
import com.ilmale.doodlejump.MyAlertDialog;
import com.ilmale.doodlejump.R;
import com.ilmale.doodlejump.Records;
import com.ilmale.doodlejump.database.OurDatabase;
import com.ilmale.doodlejump.database.User;
import com.ilmale.doodlejump.domain.LoginUser;
import com.ilmale.doodlejump.domain.MyLocation;

import java.util.List;

public class LocationService extends IntentService {

    private static final String TAG = "LocationService";
    private static final String CHANNEL_ID = "Locations Channel";

    private FusedLocationProviderClient mFusedLocationClient;

    public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;

    private OurDatabase db;
    private List<User> users;

    LoginUser loginUser = LoginUser.getInstance();
    MyLocation myLocation = MyLocation.getInstance();
    MyAlertDialog myAlertDialog = MyAlertDialog.getInstance();

    private boolean notificationSend = false;

    public LocationService() {
        super("location service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        while (true){
            if (intent == null) {

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location != null) {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            myLocation.setLatLng(latLng);
                            Log.d(TAG, "OnComplete: latitude: " + latLng.latitude);
                            Log.d(TAG, "OnComplete: longitude: " + latLng.longitude);
                            checkPoints();
                        }
                    }
                });

            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        myLocation.setLatLng(latLng);
                        Log.d(TAG, "OnComplete: latitude: " + latLng.latitude);
                        Log.d(TAG, "OnComplete: longitude: " + latLng.longitude);
                        checkPoints();
                    }
                }
            });
        }
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {}

    @Override
    public void onCreate() {
        super.onCreate();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        db = Room.databaseBuilder(getApplicationContext(), OurDatabase.class,"userdb").allowMainThreadQueries().build();
        users = db.ourDao().getUsers();
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
        if (max > loginUser.getPunteggio() && loginUser.getEmail()!=null && !notificationSend) {
            myAlertDialog.notifyToPlay(users.get(pos));
            notificationSend=true;
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
