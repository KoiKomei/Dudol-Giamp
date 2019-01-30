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

    private int contShopBlueBob=1;
    private int contShopJungleBob=1;
    private int contShopBunnyBob=1;
    public static OurDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        db = Room.databaseBuilder(getApplicationContext(), OurDatabase.class,"userdb").allowMainThreadQueries().build();

        loginUser.setMoney(100000);



        money = findViewById(R.id.money);
        money.setText(""+loginUser.getMoney());

        bob = findViewById(R.id.bob);
        blueBob = findViewById(R.id.blueBob);
        jungleBob = findViewById(R.id.jungleBob);
        bunnyBob = findViewById(R.id.bunnyBob);

        bob.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                bob.setColorFilter(Color.argb(100, 0, 0, 0));
                blueBob.setColorFilter(Color.argb(0, 0, 0, 0));
                jungleBob.setColorFilter(Color.argb(0, 0, 0, 0));
                bunnyBob.setColorFilter(Color.argb(0, 0, 0, 0));
                db.ourDao().updateBunny(false,loginUser.getEmail());
                db.ourDao().updateBlue(false,loginUser.getEmail());
                db.ourDao().updateJungle(false,loginUser.getEmail());
                return false;
            }
        });

        blueBob.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(loginUser.getMoney()>=moneyBlueBob && contShopBlueBob==1){
                    blueBob.setColorFilter(Color.argb(100, 0, 0, 0));
                    bob.setColorFilter(Color.argb(0, 0, 0, 0));
                    jungleBob.setColorFilter(Color.argb(0, 0, 0, 0));
                    bunnyBob.setColorFilter(Color.argb(0, 0, 0, 0));
                    db.ourDao().updateBlue(true,loginUser.getEmail());
                    db.ourDao().updateBunny(false,loginUser.getEmail());
                    db.ourDao().updateJungle(false,loginUser.getEmail());
                    contShopBlueBob=0;
                    contShopJungleBob=1;
                    contShopBunnyBob=1;
                    loginUser.setMoney(loginUser.getMoney()-moneyBlueBob);
                    money.setText(""+loginUser.getMoney());
                }
                return false;
            }
        });

        jungleBob.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(loginUser.getMoney()>=moneyJungleBob && contShopJungleBob==1){
                    jungleBob.setColorFilter(Color.argb(100, 0, 0, 0));
                    blueBob.setColorFilter(Color.argb(0, 0, 0, 0));
                    bob.setColorFilter(Color.argb(0, 0, 0, 0));
                    bunnyBob.setColorFilter(Color.argb(0, 0, 0, 0));
                    db.ourDao().updateJungle(true,loginUser.getEmail());
                    db.ourDao().updateBlue(false,loginUser.getEmail());
                    db.ourDao().updateBunny(false,loginUser.getEmail());
                    contShopJungleBob=0;
                    contShopBlueBob=1;
                    contShopBunnyBob=1;
                    loginUser.setMoney(loginUser.getMoney()-moneyJungleBob);
                    money.setText(""+loginUser.getMoney());
                }
                return false;
            }
        });

        bunnyBob.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(loginUser.getMoney()>=moneyBunnyBob && contShopBunnyBob==1){
                    bunnyBob.setColorFilter(Color.argb(100, 0, 0, 0));
                    blueBob.setColorFilter(Color.argb(0, 0, 0, 0));
                    jungleBob.setColorFilter(Color.argb(0, 0, 0, 0));
                    bob.setColorFilter(Color.argb(0, 0, 0, 0));
                    db.ourDao().updateBunny(true,loginUser.getEmail());
                    db.ourDao().updateBlue(false,loginUser.getEmail());
                    db.ourDao().updateJungle(false,loginUser.getEmail());
                    contShopBunnyBob=0;
                    contShopBlueBob=1;
                    contShopJungleBob=1;
                    loginUser.setMoney(loginUser.getMoney()-moneyBunnyBob);
                    money.setText(""+loginUser.getMoney());
                }
                return false;
            }
        });


    }



}
