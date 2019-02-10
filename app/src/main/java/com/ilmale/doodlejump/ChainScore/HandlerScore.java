package com.ilmale.doodlejump.ChainScore;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ilmale.doodlejump.database.OurDatabase;
import com.ilmale.doodlejump.database.User;
import com.ilmale.doodlejump.domain.MyLocation;

import java.util.ArrayList;
import java.util.List;

public abstract class HandlerScore {

    protected final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;

    protected HandlerScore successor;
    private List<User> users = new ArrayList<>();
    private MyLocation myLocation = MyLocation.getInstance();

    private FirebaseFirestore fs= FirebaseFirestore.getInstance();
    private CollectionReference use=fs.collection("User");

    abstract protected int getAllowable();
    abstract protected String getString();

    public HandlerScore(Context context) {
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

    public void setSuccessor(HandlerScore successor) {
        this.successor = successor;
    }

    public String processScore(int points){
        if (points > checkPoints(this.getAllowable())) {
            return this.getString();
        } else if (successor != null) {
            successor.processScore(points);
        }
        return "Your are the best of your seat";
    }

    public int checkPoints(int dist) {
        int max=0;
        for(User u:users) {
            int distance = calculateDistanceInKilometer(myLocation.getLatLng().latitude, myLocation.getLatLng().longitude, u.getLat(), u.getLongi());
            if(u.getPunteggio()>max && distance<dist){
                max=u.getPunteggio();
            }
        }
        return max;
    }

    public int calculateDistanceInKilometer(double userLat, double userLng, double venueLat, double venueLng) {

        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(venueLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH_KM * c));
    }

}
