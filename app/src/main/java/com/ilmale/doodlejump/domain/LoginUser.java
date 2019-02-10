package com.ilmale.doodlejump.domain;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ilmale.doodlejump.MainActivity;
import com.ilmale.doodlejump.RegisterActivity;
import com.ilmale.doodlejump.database.OurDatabase;
import com.ilmale.doodlejump.database.Possiede;
import com.ilmale.doodlejump.database.User;

import java.util.Collections;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class LoginUser {

    private static final LoginUser ourInstance = new LoginUser();
    private static final String LOG_TAG = LoginUser.class.getSimpleName();

    public static LoginUser getInstance() {
        return ourInstance;
    }

    private LoginUser() {}

    Context context;

    private FirebaseFirestore fs= FirebaseFirestore.getInstance();
    private CollectionReference use=fs.collection("User");
    private CollectionReference poss=fs.collection("Possiede");

    private String username = null;
    private String password = null;
    private String email = null;
    private int money = 0;
    private int punteggio = 0;
    private double lat = 0.0;
    private double longi = 0.0;
    private boolean BlueBob=false;
    private boolean BunnyBob=false;
    private boolean JungleBob=false;
    private boolean equippedBob=false;
    private boolean equippedBlueBob=false;
    private boolean equippedBunnyBob=false;
    private boolean equippedJungleBob=false;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMoney(){return money;}

    public void setMoney(int money){this.money=money;}

    public int getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(int punteggio) {
        this.punteggio = punteggio;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public boolean isBlueBob() {
        return BlueBob;
    }

    public void setBlueBob(boolean blueBob) {
        BlueBob = blueBob;
    }

    public boolean isBunnyBob() {
        return BunnyBob;
    }

    public void setBunnyBob(boolean bunnyBob) {
        BunnyBob = bunnyBob;
    }

    public boolean isJungleBob() {
        return JungleBob;
    }

    public void setJungleBob(boolean jungleBob) {
        JungleBob = jungleBob;
    }

    public boolean isEquippedBob() {
        return equippedBob;
    }

    public void setEquippedBob(boolean equippedBob) {
        this.equippedBob = equippedBob;
        this.equippedBlueBob = false;
        this.equippedJungleBob = false;
        this.equippedBunnyBob = false;
        setBobEquipped();
    }

    public boolean isEquippedBlueBob() {
        return equippedBlueBob;
    }

    public void setEquippedBlueBob(boolean equippedBlueBob) {
        this.equippedBlueBob = equippedBlueBob;
        this.equippedBob = false;
        this.equippedJungleBob = false;
        this.equippedBunnyBob = false;
        setBobEquipped();
    }

    public boolean isEquippedBunnyBob() {
        return equippedBunnyBob;
    }

    public void setEquippedBunnyBob(boolean equippedBunnyBob) {
        this.equippedBunnyBob = equippedBunnyBob;
        this.equippedBob = false;
        this.equippedBlueBob = false;
        this.equippedJungleBob = false;
        setBobEquipped();
    }

    public boolean isEquippedJungleBob() {
        return equippedJungleBob;
    }

    public void setEquippedJungleBob(boolean equippedJungleBob) {
        this.equippedJungleBob = equippedJungleBob;
        this.equippedBob = false;
        this.equippedBlueBob = false;
        this.equippedBunnyBob = false;
        setBobEquipped();
    }

    public void initializeLoginUser(Context context) {
        this.context=context;
        final SharedPreferences pref = context.getSharedPreferences("LOGIN_PREF", context.MODE_PRIVATE);
        String emailPref  = pref.getString("email", null);
        if(emailPref!=null){
            setLoginUser(emailPref);
        }

    }

    public void login(final String emailUser) {
        final SharedPreferences pref = context.getSharedPreferences("LOGIN_PREF", context.MODE_PRIVATE);
        final SharedPreferences.Editor editorPref = pref.edit();
        editorPref.putString("email", emailUser);
        editorPref.commit();
    }

    public void logout() {
        final SharedPreferences pref = context.getSharedPreferences("LOGIN_PREF", context.MODE_PRIVATE);
        final SharedPreferences.Editor editorPref = pref.edit();
        editorPref.putString("email", null);
        editorPref.commit();
        resetLoginUser();
    }

    public void setLoginUser(final String emailUser) {
        use.whereEqualTo("email", emailUser).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        User u = d.toObject(User.class);
                        setEmail(emailUser);
                        setUsername(u.getUsername());
                        setMoney(u.getMoney());
                        setPassword(u.getPassword());
                        setPunteggio(u.getPunteggio());
                        setLat(u.getLat());
                        setLongi(u.getLongi());
                    }
                }
            }
        });
        poss.whereEqualTo("email", email).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot d:list){
                        Possiede possiede=d.toObject(Possiede.class);
                        setBlueBob(possiede.isBluebob());
                        setBunnyBob(possiede.isBunnybob());
                        setJungleBob(possiede.isJunglebob());
                    }
                }
            }
        });
        login(emailUser);
    }

    public void resetLoginUser() {
        setEmail(null);
        setUsername(null);
        setPassword(null);
        setMoney(0);
        setPunteggio(0);
        setLat(0.0);
        setLongi(0.0);
        setEquippedBlueBob(false);
        setEquippedBunnyBob(false);
        setEquippedJungleBob(false);
        setBlueBob(false);
        setBunnyBob(false);
        setJungleBob(false);
    }

    public void initializeBobEquipped(){
        final SharedPreferences pref = context.getSharedPreferences("EQUIPPEDBOB_PREF", MODE_PRIVATE);
        int equippedBob = pref.getInt("bob", 0);
        if(equippedBob==0){
            setEquippedBob(true);
        }
        else if(equippedBob==1){
            setEquippedBlueBob(true);
        }
        else if(equippedBob==2){
            setEquippedBunnyBob(true);
        }
        else if(equippedBob==3){
            setEquippedJungleBob(true);
        }

    }

    public void setBobEquipped(){
        final SharedPreferences pref = context.getSharedPreferences("EQUIPPEDBOB_PREF", context.MODE_PRIVATE);
        final SharedPreferences.Editor editorPref = pref.edit();
        int equippedBob=0;
        if(isEquippedBob()){
            equippedBob=0;
        }
        else if(isEquippedBlueBob()){
            equippedBob=1;
        }
        else if(isEquippedBunnyBob()){
            equippedBob=2;
        }
        else if(isEquippedJungleBob()){
            equippedBob=3;
        }
        editorPref.putInt("bob", equippedBob);
        editorPref.commit();
    }
}
