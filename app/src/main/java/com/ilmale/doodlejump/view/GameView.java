package com.ilmale.doodlejump.view;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ilmale.doodlejump.Constants;
import com.ilmale.doodlejump.EndGameActivity;
import com.ilmale.doodlejump.R;
import com.ilmale.doodlejump.Records;
import com.ilmale.doodlejump.database.OurDatabase;
import com.ilmale.doodlejump.domain.Bullet;
import com.ilmale.doodlejump.domain.LoginUser;
import com.ilmale.doodlejump.domain.Platform;
import com.ilmale.doodlejump.engine.GameEngine;

public class GameView extends SurfaceView implements Runnable{

        private static final String LOG_TAG = GameView.class.getSimpleName();

        // This is our thread
        Thread gameThread = null;

        // This is new. We need a SurfaceHolder
        //When we use Paint and Canvas in a thread
        // We will see it in action in the draw method soon.
        SurfaceHolder ourHolder;

        // A boolean which we will set and unset
        // when the game is running- or not.
        volatile boolean playing;

        // A Canvas and a Paint item
        Canvas canvas;
        Paint paint;

        // This variable tracks the game frame rate
        long fps;

        // This is used to help calculate the fps
        private long timeThisFrame;

        //database to get bob
        public static OurDatabase db;

        // Declare an item of type Bitmap
        Bitmap bitmapBG;
        Bitmap bitmapBobLeft;
        Bitmap bitmapBobRight;
        Bitmap bitmapBobUp;
        Bitmap bitmapBobJetLeft;
        Bitmap bitmapBobJetRight;
        Bitmap bitmapPlatform;
        Bitmap bitmapBULLET;
        Bitmap bitmapJETPACK;
        Bitmap bitmapSPRINGS;
        Bitmap bitmapEnemy;

        protected boolean BlueBob=false;
        protected boolean JungleBob=false;
        protected boolean BunnyBob=false;

        protected LoginUser loginUser = LoginUser.getInstance();


        GameEngine gameEngine;
        protected Constants constants = Constants.getInstance();
        protected Records records = Records.getInstance();

        public GameView(Context context, GameEngine engine) {
            super(context);

            gameEngine = engine;

            // Initialize ourHolder and paint objects
            ourHolder = getHolder();
            paint = new Paint();

            db = Room.databaseBuilder(context, OurDatabase.class,"userdb").allowMainThreadQueries().build();

            initializeBobValue();

            // Initialize bitmaps of Bob
            bitmapBobLeft = BitmapFactory.decodeResource(getResources(), R.drawable.bobleft);
            bitmapBobRight = BitmapFactory.decodeResource(getResources(), R.drawable.bobright);
            bitmapBobUp = BitmapFactory.decodeResource(getResources(), R.drawable.bobup);
            bitmapBobJetLeft = BitmapFactory.decodeResource(getResources(), R.drawable.bobleftjet);
            bitmapBobJetRight = BitmapFactory.decodeResource(getResources(), R.drawable.bobrightjet);

            if(BlueBob){
                bitmapBobLeft = BitmapFactory.decodeResource(getResources(), R.drawable.blueleft);
                bitmapBobRight = BitmapFactory.decodeResource(getResources(), R.drawable.blueright);
                bitmapBobUp = BitmapFactory.decodeResource(getResources(), R.drawable.blueup);
                bitmapBobJetLeft = BitmapFactory.decodeResource(getResources(), R.drawable.blueleftjet);
                bitmapBobJetRight = BitmapFactory.decodeResource(getResources(), R.drawable.bluerightjet);
            }
            if(BunnyBob){
                bitmapBobLeft = BitmapFactory.decodeResource(getResources(), R.drawable.bunnyleft);
                bitmapBobRight = BitmapFactory.decodeResource(getResources(), R.drawable.bunnyright);
                bitmapBobUp = BitmapFactory.decodeResource(getResources(), R.drawable.bunnyup);
                bitmapBobJetLeft = BitmapFactory.decodeResource(getResources(), R.drawable.bunnyleftjet);
                bitmapBobJetRight = BitmapFactory.decodeResource(getResources(), R.drawable.bunnyrightjet);
            }
            if(JungleBob){
                bitmapBobLeft = BitmapFactory.decodeResource(getResources(), R.drawable.jungleleft);
                bitmapBobRight = BitmapFactory.decodeResource(getResources(), R.drawable.jungleright);
                bitmapBobUp = BitmapFactory.decodeResource(getResources(), R.drawable.jungleup);
                bitmapBobJetLeft = BitmapFactory.decodeResource(getResources(), R.drawable.jungleleftjet);
                bitmapBobJetRight = BitmapFactory.decodeResource(getResources(), R.drawable.junglerightjet);
            }

            // Initialize bitmaps
            bitmapBG = BitmapFactory.decodeResource(getResources(), R.drawable.background);
            bitmapPlatform = BitmapFactory.decodeResource(getResources(), R.drawable.plat1);
            bitmapEnemy = BitmapFactory.decodeResource(getResources(), R.drawable.enemy1);
            bitmapSPRINGS = BitmapFactory.decodeResource(getResources(), R.drawable.molla);
            bitmapJETPACK = BitmapFactory.decodeResource(getResources(), R.drawable.jetpack);
            bitmapBULLET = BitmapFactory.decodeResource(getResources(), R.drawable.bullet);;

            gameEngine.player.setHeight(bitmapBobLeft.getHeight());
            gameEngine.player.setWidth(bitmapBobLeft.getWidth());
            for(Platform p: gameEngine.getPlatforms()){
                p.setHeight(bitmapPlatform.getHeight());
                p.setWidth(bitmapPlatform.getWidth());
            }
            gameEngine.enemy.setHeight(bitmapEnemy.getHeight());
            gameEngine.enemy.setWidth(bitmapEnemy.getWidth());
            gameEngine.jetpack.setHeight(bitmapJETPACK.getHeight());
            gameEngine.jetpack.setWidth(bitmapJETPACK.getHeight());

            playing = true;
        }

        @Override
        public void run() {
            //QUESTO FIXA IL BUG DELL INSTANT GAMEOVER
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (playing) {
                //Log.d(LOG_TAG, "Running while");

                // Capture the current time in milliseconds in startFrameTime
                long startFrameTime = System.currentTimeMillis();

                draw();

                if(!gameEngine.isGameOver()) {
                    // Update the frame
                    update();
                }

                // Calculate the fps this frame
                // We can then use the result to time animations and more.
                timeThisFrame = System.currentTimeMillis() - startFrameTime;
                if (timeThisFrame > 0) {
                    fps = 1000 / timeThisFrame;
                }
            }
        }

        public void update() {
            //Log.d(LOG_TAG, "updating gameview");
            gameEngine.update();
        }

        public synchronized void draw() {
            // Make sure our drawing surface is valid or we crash
            if (ourHolder.getSurface().isValid()) {
                //Log.d(LOG_TAG, "drawing");
                // Lock the canvas ready to draw
                // Make the drawing surface our canvas item
                canvas = ourHolder.lockCanvas();

                if (gameEngine.isGameOver()) {
                    Intent intent = new Intent(getContext(), EndGameActivity.class);
                    getContext().startActivity(intent);
                }

                else {

                    Paint textPaint = new Paint();
                    textPaint.setColor(Color.argb(255, 249, 129, 0));
                    textPaint.setTextAlign(Paint.Align.CENTER);
                    textPaint.setTextSize(100);
                    canvas.drawText("" + constants.getPoints(), canvas.getWidth() / 2, (canvas.getHeight() / 2) + 100, textPaint);

                    // Draw the background
                    canvas.drawBitmap(bitmapBG, 0, 0, paint);

                    // Choose the brush color for drawing
                    paint.setColor(Color.argb(255, 249, 129, 0));

                    // Make the text a bit bigger
                    paint.setTextSize(45);

                    // Display the current fps on the screen
                    //canvas.drawText("FPS:" + fps, 20, 40, paint);
                    canvas.drawText("Your Points:" + constants.getPoints(), 20, 85, paint);
                    canvas.drawText("Record:" + records.getRecords().get(0), 20, 40, paint);

                    for (Platform p : gameEngine.getPlatforms()) {
                        //Log.d(LOG_TAG, "platx:" + p.getpX() + ", platy:" + p.getpY());
                        //canvas.drawRect(p.getpX(), p.getpY(), p.getpX() + p.getWidth(), p.getpY() + p.getHeight(), paint);
                        canvas.drawBitmap(bitmapPlatform, p.getpX(), p.getpY(), paint);
                        if (p.hasSprings()) {
                            //Log.d(LOG_TAG, "SPRINGS");
                            canvas.drawBitmap(bitmapSPRINGS, p.getpX() + (bitmapPlatform.getWidth() / 2 - bitmapSPRINGS.getWidth() / 2), p.getpY() - bitmapSPRINGS.getHeight(), paint);
                        }

                    }

                    // Draw bob at bobXPosition, bobYPosition with jetpack and shootmode
                    if (gameEngine.player.hasJetpack()) {
                        boolean shootMode = false;
                        try {
                            for (Bullet b : gameEngine.bullets) {
                                if (b.getpY() > 0) {
                                    shootMode = true;
                                    break;
                                }
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        if (!shootMode) {
                            if (gameEngine.player.getVelX() > 0)
                                canvas.drawBitmap(bitmapBobJetLeft, gameEngine.player.getpX(), gameEngine.player.getpY(), paint);
                            else
                                canvas.drawBitmap(bitmapBobJetRight, gameEngine.player.getpX(), gameEngine.player.getpY(), paint);
                        } else {
                            canvas.drawBitmap(bitmapBobUp, gameEngine.player.getpX(), gameEngine.player.getpY(), paint);
                        }
                    } else {
                        boolean shootMode = false;
                        try {
                            for (Bullet b : gameEngine.bullets) {
                                if (b.getpY() > 0) {
                                    shootMode = true;
                                    break;
                                }
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        if (!shootMode) {
                            if (gameEngine.player.getVelX() > 0)
                                canvas.drawBitmap(bitmapBobLeft, gameEngine.player.getpX(), gameEngine.player.getpY(), paint);
                            else
                                canvas.drawBitmap(bitmapBobRight, gameEngine.player.getpX(), gameEngine.player.getpY(), paint);
                        } else {
                            canvas.drawBitmap(bitmapBobUp, gameEngine.player.getpX(), gameEngine.player.getpY(), paint);
                        }
                    }

                    canvas.drawBitmap(bitmapJETPACK, gameEngine.jetpack.getpX(), gameEngine.jetpack.getpY(), paint);
                    canvas.drawBitmap(bitmapEnemy, gameEngine.enemy.getpX(), gameEngine.enemy.getpY(), paint);
                    try {
                        for (Bullet b : gameEngine.bullets) {
                            canvas.drawBitmap(bitmapBULLET, b.getpX(), b.getpY(), paint);
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                // Draw everything to the screen and unlock the drawing surface
                ourHolder.unlockCanvasAndPost(canvas);
            }

        }

        // If SimpleGameEngine Activity is paused/stopped/shutdown our thread.
        public void pause() {
            Log.d(LOG_TAG, "Pausing game");
            playing = false;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                Log.e("Error:", "joining thread");
            }
        }

        // If SimpleGameEngine Activity is started then start our thread.
        public void resume() {
            Log.d(LOG_TAG, "Resuming game");
            playing = true;
            gameThread = new Thread(this);
            gameThread.start();
        }

        // The SurfaceView class implements onTouchListener
        // So we can override this method and detect screen touches.
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            canvas.drawBitmap(bitmapBobUp, gameEngine.player.getpX(), gameEngine.player.getpY(), paint);
            gameEngine.shoot();
            return super.onTouchEvent(event);
        }

    private void initializeBobValue() {
        if(loginUser.getEmail()!=null){
            if(loginUser.isEquippedBlueBob()){
                BlueBob = true;
                BunnyBob = false;
                JungleBob = false;
            }
            else if(loginUser.isEquippedJungleBob()){
                JungleBob = true;
                BlueBob = false;
                BunnyBob = false;
            }
            else if(loginUser.isEquippedBunnyBob()){
                BunnyBob = true;
                BlueBob = false;
                JungleBob = false;
            }
        }
    }



}
