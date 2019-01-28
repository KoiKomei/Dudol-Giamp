package com.ilmale.doodlejump.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.ilmale.doodlejump.Constants;
import com.ilmale.doodlejump.MainActivity;
import com.ilmale.doodlejump.R;
import com.ilmale.doodlejump.domain.AbstractGameObject;
import com.ilmale.doodlejump.domain.Bullet;
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

        // Declare an item of type Bitmap
        Bitmap bitmapBG;
        Bitmap bitmapBobLeft;
        Bitmap bitmapBobRight;
        Bitmap bitmapBobUp;
        Bitmap bitmapPlatform;
        Bitmap bitmapBULLET;
        Bitmap bitmapHAT;
        Bitmap bitmapJETPACK;
        Bitmap bitmapSPRINGS;
        Bitmap bitmapSHIELD;
        Bitmap bitmapEnemy;

        GameEngine gameEngine;
        private Constants constants = Constants.getInstance();

        public GameView(Context context, GameEngine engine) {
            super(context);

            gameEngine = engine;

            // Initialize ourHolder and paint objects
            ourHolder = getHolder();
            paint = new Paint();

            // Initialize bitmaps
            bitmapBobLeft = BitmapFactory.decodeResource(getResources(), R.drawable.bobleft);
            bitmapBobRight = BitmapFactory.decodeResource(getResources(), R.drawable.bobright);
            bitmapBobUp = BitmapFactory.decodeResource(getResources(), R.drawable.bobup);
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

            while (playing) {
                Log.d(LOG_TAG, "Running while");

                // Capture the current time in milliseconds in startFrameTime
                long startFrameTime = System.currentTimeMillis();

                // Update the frame
                update();

                // Draw the frame
                draw();

                // Calculate the fps this frame
                // We can then use the result to time animations and more.
                timeThisFrame = System.currentTimeMillis() - startFrameTime;
                if (timeThisFrame > 0) {
                    fps = 1000 / timeThisFrame;
                }
            }
        }

        public void update() {
            Log.d(LOG_TAG, "updating gameview");
            gameEngine.update();
        }

        public void draw() {
            // Make sure our drawing surface is valid or we crash
            if (ourHolder.getSurface().isValid()) {
                Log.d(LOG_TAG, "drawing");
                // Lock the canvas ready to draw
                // Make the drawing surface our canvas item
                canvas = ourHolder.lockCanvas();

                // Draw the background color
                canvas.drawBitmap(bitmapBG, 0, 0, paint);

                // Choose the brush color for drawing
                paint.setColor(Color.argb(255,  249, 129, 0));

                // Make the text a bit bigger
                paint.setTextSize(45);

                // Display the current fps on the screen
                canvas.drawText("FPS:" + fps, 20, 40, paint);
                canvas.drawText("" + constants.getPoints(), constants.getPixelWidth()-200 , 40, paint);

                for (Platform p: gameEngine.getPlatforms()) {
                    Log.d(LOG_TAG, "platx:"+p.getpX()+", platy:"+p.getpY());
                    canvas.drawBitmap(bitmapPlatform, p.getpX(), p.getpY(), paint);
                    if(p.hasSprings()){
                        Log.d(LOG_TAG, "SPRINGS");
                        canvas.drawBitmap(bitmapSPRINGS, p.getpX()+(bitmapPlatform.getWidth()/2-bitmapSPRINGS.getWidth()/2), p.getpY()-bitmapSPRINGS.getHeight(), paint);
                    }

                }

                // Draw bob at bobXPosition, bobYPosition
                if (gameEngine.player.getVelX() > 0) canvas.drawBitmap(bitmapBobLeft, gameEngine.player.getpX(), gameEngine.player.getpY(), paint);
                else canvas.drawBitmap(bitmapBobRight, gameEngine.player.getpX(), gameEngine.player.getpY(), paint);

                canvas.drawBitmap(bitmapJETPACK, gameEngine.jetpack.getpX(), gameEngine.jetpack.getpY(), paint);
                canvas.drawBitmap(bitmapEnemy, gameEngine.enemy.getpX(), gameEngine.enemy.getpY(), paint);
                for (Bullet b: gameEngine.bullets){
                    canvas.drawBitmap(bitmapBULLET, b.getpX(), b.getpY(), paint);
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

        public Bitmap getBitmapBobLeft() { return bitmapBobLeft; }

        public Bitmap getBitmapBobRight() { return bitmapBobRight; }

        public Bitmap getBitmapPlatform() {
            return bitmapPlatform;
        }

        public Bitmap getBitmapBULLET() {
            return bitmapBULLET;
        }

        public Bitmap getBitmapHAT() {
            return bitmapHAT;
        }

        public Bitmap getBitmapJETPACK() {
            return bitmapJETPACK;
        }

        public Bitmap getBitmapSPRINGS() {
            return bitmapSPRINGS;
        }

        public Bitmap getBitmapSHIELD() {
        return bitmapSHIELD;
    }

        public Bitmap getBitmapEnemy() {
            return bitmapEnemy;
        }

}
