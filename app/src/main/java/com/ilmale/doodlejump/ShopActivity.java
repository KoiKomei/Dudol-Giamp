package com.ilmale.doodlejump;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ilmale.doodlejump.database.OurDatabase;
import com.ilmale.doodlejump.database.Possiede;
import com.ilmale.doodlejump.database.User;
import com.ilmale.doodlejump.domain.LoginUser;

import java.util.List;

public class ShopActivity extends AppCompatActivity {

    private static final String LOG_TAG = ShopActivity.class.getSimpleName();

    private LoginUser loginUser = LoginUser.getInstance();
    private AudioManager audioManager = AudioManager.getInstance();
    private TextView money;
    private ImageView bob;
    private ImageView blueBob;
    private ImageView jungleBob;
    private ImageView bunnyBob;
    private int moneyBlueBob=500;
    private int moneyJungleBob=1000;
    private int moneyBunnyBob=1500;

    private FirebaseFirestore fs= FirebaseFirestore.getInstance();
    private CollectionReference use=fs.collection("User");
    private CollectionReference negozio=fs.collection("Negozio");
    private CollectionReference poss=fs.collection("Possiede");

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        money = findViewById(R.id.money);
        money.setText(""+loginUser.getMoney());

        ImageButton menu = findViewById(R.id.menu_shop);

        bob = findViewById(R.id.bob);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               launchMainActivity(view);
            }
        });

        if(loginUser.getEmail()!=null) {

            blueBob = findViewById(R.id.blueBob);
            jungleBob = findViewById(R.id.jungleBob);
            bunnyBob = findViewById(R.id.bunnyBob);

            initializeBobValue();

            bob.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    bob.setColorFilter(Color.argb(100, 0, 0, 0));
                    blueBob.setColorFilter(Color.argb(0, 0, 0, 0));
                    jungleBob.setColorFilter(Color.argb(0, 0, 0, 0));
                    bunnyBob.setColorFilter(Color.argb(0, 0, 0, 0));
                    loginUser.setEquippedBob(true);
                    return false;
                }
            });

            blueBob.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (loginUser.getMoney() >= moneyBlueBob && !loginUser.isBlueBob()) {
                        blueBob.setColorFilter(Color.argb(100, 0, 0, 0));
                        bob.setColorFilter(Color.argb(0, 0, 0, 0));
                        jungleBob.setColorFilter(Color.argb(0, 0, 0, 0));
                        bunnyBob.setColorFilter(Color.argb(0, 0, 0, 0));
                        blueBob.setImageResource(R.drawable.shopbluebought);
                        loginUser.setBlueBob(true);
                        loginUser.setEquippedBlueBob(true);
                        loginUser.login();
                        money.setText("" + (loginUser.getMoney()-moneyBlueBob));
                        setBlueBob();
                        updateMoney(moneyBlueBob);
                    }
                    else if(loginUser.isBlueBob()){
                        blueBob.setColorFilter(Color.argb(100, 0, 0, 0));
                        bob.setColorFilter(Color.argb(0, 0, 0, 0));
                        jungleBob.setColorFilter(Color.argb(0, 0, 0, 0));
                        bunnyBob.setColorFilter(Color.argb(0, 0, 0, 0));
                        loginUser.setEquippedBlueBob(true);
                    }
                    return false;
                }
            });

            jungleBob.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (loginUser.getMoney() >= moneyJungleBob && !loginUser.isJungleBob()) {
                        jungleBob.setColorFilter(Color.argb(100, 0, 0, 0));
                        blueBob.setColorFilter(Color.argb(0, 0, 0, 0));
                        bob.setColorFilter(Color.argb(0, 0, 0, 0));
                        bunnyBob.setColorFilter(Color.argb(0, 0, 0, 0));
                        jungleBob.setImageResource(R.drawable.shopjunglebought);
                        loginUser.setJungleBob(true);
                        loginUser.setEquippedJungleBob(true);
                        loginUser.login();
                        money.setText("" + (loginUser.getMoney()-moneyJungleBob));
                        setJungleBob();
                        updateMoney(moneyJungleBob);
                    }
                    else if(loginUser.isJungleBob()){
                        jungleBob.setColorFilter(Color.argb(100, 0, 0, 0));
                        blueBob.setColorFilter(Color.argb(0, 0, 0, 0));
                        bob.setColorFilter(Color.argb(0, 0, 0, 0));
                        bunnyBob.setColorFilter(Color.argb(0, 0, 0, 0));
                        loginUser.setEquippedJungleBob(true);
                    }
                    return false;
                }
            });

            bunnyBob.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (loginUser.getMoney() >= moneyBunnyBob && !loginUser.isBunnyBob()) {
                        bunnyBob.setColorFilter(Color.argb(100, 0, 0, 0));
                        blueBob.setColorFilter(Color.argb(0, 0, 0, 0));
                        jungleBob.setColorFilter(Color.argb(0, 0, 0, 0));
                        bob.setColorFilter(Color.argb(0, 0, 0, 0));
                        bunnyBob.setImageResource(R.drawable.shopbunnybought);
                        loginUser.setBunnyBob(true);
                        loginUser.setEquippedBunnyBob(true);
                        loginUser.login();
                        money.setText("" + (loginUser.getMoney()-moneyBunnyBob));
                        setBunnyBob();
                        updateMoney(moneyBunnyBob);
                    }
                    else if(loginUser.isBunnyBob()){
                        bunnyBob.setColorFilter(Color.argb(100, 0, 0, 0));
                        blueBob.setColorFilter(Color.argb(0, 0, 0, 0));
                        jungleBob.setColorFilter(Color.argb(0, 0, 0, 0));
                        bob.setColorFilter(Color.argb(0, 0, 0, 0));
                        loginUser.setEquippedBunnyBob(true);
                    }
                    return false;
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        audioManager.playBg_audio();
    }

    @Override
    public void onBackPressed() {
        audioManager.setCanStopBgAudio(false);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(audioManager.isCanStopBgAudio()){
            audioManager.pauseBg_audio();
        }
        audioManager.setCanStopBgAudio(true);
    }

    private void initializeBobValue() {
        if(loginUser.getEmail()!=null){
            if(loginUser.isBlueBob()){
                blueBob.setImageResource(R.drawable.shopbluebought);
            }
            if(loginUser.isJungleBob()){
                jungleBob.setImageResource(R.drawable.shopjunglebought);
            }
            if(loginUser.isBunnyBob()){
                bunnyBob.setImageResource(R.drawable.shopbunnybought);
            }
            if(loginUser.isEquippedBob()){
                bob.setColorFilter(Color.argb(100, 0, 0, 0));
                blueBob.setColorFilter(Color.argb(0, 0, 0, 0));
                jungleBob.setColorFilter(Color.argb(0, 0, 0, 0));
                bunnyBob.setColorFilter(Color.argb(0, 0, 0, 0));
            }
            if(loginUser.isEquippedBlueBob()){
                bob.setColorFilter(Color.argb(0, 0, 0, 0));
                blueBob.setColorFilter(Color.argb(100, 0, 0, 0));
                jungleBob.setColorFilter(Color.argb(0, 0, 0, 0));
                bunnyBob.setColorFilter(Color.argb(0, 0, 0, 0));
            }
            else if(loginUser.isEquippedJungleBob()){
                bob.setColorFilter(Color.argb(0, 0, 0, 0));
                blueBob.setColorFilter(Color.argb(0, 0, 0, 0));
                jungleBob.setColorFilter(Color.argb(100, 0, 0, 0));
                bunnyBob.setColorFilter(Color.argb(0, 0, 0, 0));
            }
            else if(loginUser.isEquippedBunnyBob()){
                bob.setColorFilter(Color.argb(0, 0, 0, 0));
                blueBob.setColorFilter(Color.argb(0, 0, 0, 0));
                jungleBob.setColorFilter(Color.argb(0, 0, 0, 0));
                bunnyBob.setColorFilter(Color.argb(100, 0, 0, 0));
            }
        }
    }

    private void setBlueBob(){
        poss.whereEqualTo("email", loginUser.getEmail()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot d:list){
                        Possiede possiede=d.toObject(Possiede.class);
                        possiede.setBluebob(true);
                        String id=d.getId();
                        poss.document(id).set(possiede);
                    }
                }
            }
        });
    }

    private void setJungleBob(){
        poss.whereEqualTo("email", loginUser.getEmail()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot d:list){
                        Possiede possiede=d.toObject(Possiede.class);
                        possiede.setJunglebob(true);
                        String id=d.getId();
                        poss.document(id).set(possiede);
                    }
                }
            }
        });
    }

    private void setBunnyBob(){
        poss.whereEqualTo("email", loginUser.getEmail()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot d:list){
                        Possiede possiede=d.toObject(Possiede.class);
                        possiede.setBunnybob(true);
                        String id=d.getId();
                        poss.document(id).set(possiede);
                    }
                }
            }
        });
    }

    private void updateMoney(final int money){
        use.whereEqualTo("email", loginUser.getEmail()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot d:list){
                        User user=d.toObject(User.class);
                        int oldValue = loginUser.getMoney();
                        int newValue = oldValue - money;
                        user.setMoney(newValue);
                        String id=d.getId();
                        use.document(id).set(user);
                        loginUser.setMoney(newValue);
                    }
                }
            }
        });
    }

    public void launchMainActivity(View view) {
        audioManager.setCanStopBgAudio(false);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
