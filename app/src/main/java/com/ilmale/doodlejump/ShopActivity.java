package com.ilmale.doodlejump;

import android.arch.persistence.room.Room;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilmale.doodlejump.database.OurDatabase;
import com.ilmale.doodlejump.domain.LoginUser;

public class ShopActivity extends AppCompatActivity {

    private LoginUser loginUser = LoginUser.getInstance();
    private TextView money;
    private ImageView bob;
    private ImageView blueBob;
    private ImageView jungleBob;
    private ImageView bunnyBob;
    private int moneyBlueBob=500;
    private int moneyJungleBob=1000;
    private int moneyBunnyBob=1500;

    public static OurDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        db = Room.databaseBuilder(getApplicationContext(), OurDatabase.class,"userdb").allowMainThreadQueries().build();

        money = findViewById(R.id.money);
        money.setText(""+loginUser.getMoney());

        bob = findViewById(R.id.bob);

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
                        db.ourDao().updateBlue(true, loginUser.getEmail());
                        db.ourDao().updateMoney(loginUser.getEmail(), loginUser.getMoney()-moneyBlueBob, loginUser.getMoney());
                        loginUser.setMoney(loginUser.getMoney() - moneyBlueBob);
                        money.setText("" + loginUser.getMoney());
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
                        db.ourDao().updateJungle(true, loginUser.getEmail());
                        db.ourDao().updateMoney(loginUser.getEmail(), loginUser.getMoney()-moneyJungleBob, loginUser.getMoney());
                        loginUser.setMoney(loginUser.getMoney() - moneyJungleBob);
                        money.setText("" + loginUser.getMoney());
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
                        db.ourDao().updateBunny(true, loginUser.getEmail());
                        db.ourDao().updateMoney(loginUser.getEmail(), loginUser.getMoney()-moneyBunnyBob, loginUser.getMoney());
                        loginUser.setMoney(loginUser.getMoney() - moneyBunnyBob);
                        money.setText("" + loginUser.getMoney());
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

    private void initializeBobValue() {
        if(loginUser.getEmail()!=null){
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
            if(loginUser.isBlueBob()){
                blueBob.setImageResource(R.drawable.shopbluebought);
            }
            if(loginUser.isJungleBob()){
                jungleBob.setImageResource(R.drawable.shopjunglebought);
            }
            if(loginUser.isBunnyBob()){
                bunnyBob.setImageResource(R.drawable.shopbunnybought);
            }
        }
    }
}
