package com.ilmale.doodlejump.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ilmale.doodlejump.engine.GameEngine;
import com.ilmale.doodlejump.engine.Object;
import com.ilmale.doodlejump.engine.ObjectType;
import com.ilmale.doodlejump.engine.Platform;
import com.ilmale.doodlejump.engine.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable{

        // This is our thread
        Thread gameThread = null;

        // This is new. We need a SurfaceHolder
        //When we use Paint and Canvas in a thread
        // We will see it in action in the draw method soon.
        SurfaceHolder ourHolder;

        // A boolean which we will set and unset
        // when the game is running- or not.
        volatile boolean playing;

        // A Canvas and a Paint object
        Canvas canvas;
        Paint paint;

        // This variable tracks the game frame rate
        long fps;

        // This is used to help calculate the fps
        private long timeThisFrame;

        // Declare an object of type Bitmap
        Bitmap bitmapBob;

        Bitmap bitmapPlatform;

        Bitmap bitmapBULLET;
        Bitmap bitmapHAT;
        Bitmap bitmapJETPACK;
        Bitmap bitmapSPRINGS;
        Bitmap bitmapSHIELD;

        Bitmap bitmapEnemy;

        GameEngine ge;

        public GameView(Context context) {
            // The next line of code asks the
            // SurfaceView class to set up our object.
            // How kind.
            super(context);

            ge =(GameEngine) context;
            // Initialize ourHolder and paint objects
            ourHolder = getHolder();
            paint = new Paint();

        }


        @Override
        public void run() {
            while (playing) {
                // Capture the current time in milliseconds in startFrameTime
                long startFrameTime = System.currentTimeMillis();

                // Update the frame
                update();

                // Draw the frame
                draw();

                // Calculate the fps this frame
                // We can then use the result to
                // time animations and more.
                timeThisFrame = System.currentTimeMillis() - startFrameTime;
                if (timeThisFrame > 0) {
                    fps = 1000 / timeThisFrame;
                }
            }
        }


        public void update() {

        }

        public void draw() {

            // Make sure our drawing surface is valid or we crash
            if (ourHolder.getSurface().isValid()) {
                // Lock the canvas ready to draw
                // Make the drawing surface our canvas object
                canvas = ourHolder.lockCanvas();

                // Draw the background color
                canvas.drawColor(Color.argb(255,  26, 128, 182));

                // Choose the brush color for drawing
                paint.setColor(Color.argb(255,  249, 129, 0));

                // Make the text a bit bigger
                paint.setTextSize(45);

                // Display the current fps on the screen
                canvas.drawText("FPS:" + fps, 20, 40, paint);

                // Draw bob at bobXPosition, bobYPosition
                canvas.drawBitmap(bitmapBob, ge.player.getpX(), ge.player.getpY(), paint);

                for (Platform p: ge.platforms) {
                    canvas.drawBitmap(bitmapPlatform, p.getpX(), p.getpY(), paint);
                    if(p.hasSprings()){
                        canvas.drawBitmap(bitmapSPRINGS, p.getpX()+bitmapPlatform.getWidth()/2, p.getpY(), paint);
                    }

                }

                // Draw everything to the screen
                // and unlock the drawing surface
                ourHolder.unlockCanvasAndPost(canvas);
            }

        }

        // If SimpleGameEngine Activity is paused/stopped
        // shutdown our thread.
        public void pause() {
            playing = false;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                Log.e("Error:", "joining thread");
            }
        }

        // If SimpleGameEngine Activity is started then
        // start our thread.
        public void resume() {
            playing = true;
            gameThread = new Thread();
            gameThread.start();
        }

        // The SurfaceView class implements onTouchListener
        // So we can override this method and detect screen touches.


        @Override
        public boolean onTouchEvent(MotionEvent event) {
            ge.Shoot();
            return super.onTouchEvent(event);
        }



        public Bitmap getBitmapBob() {
            return bitmapBob;
        }

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
