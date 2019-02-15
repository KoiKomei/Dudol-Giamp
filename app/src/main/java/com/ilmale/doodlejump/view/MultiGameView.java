package com.ilmale.doodlejump.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;

import com.ilmale.doodlejump.EndGameMultiActivity;
import com.ilmale.doodlejump.R;
import com.ilmale.doodlejump.domain.Bullet;
import com.ilmale.doodlejump.domain.Platform;
import com.ilmale.doodlejump.engine.MultiEngine;

//view for the multi-player game

public class MultiGameView extends GameView {

    MultiEngine enginee;
    Bitmap boolBobDown;
    Bitmap boolBobUp;
    Bitmap bob2left;

    public MultiGameView(Context context, MultiEngine engine){
        super(context, engine);
        enginee = engine;
        boolBobUp = BitmapFactory.decodeResource(getResources(), R.drawable.balloonupleft);
        boolBobDown = BitmapFactory.decodeResource(getResources(), R.drawable.balloondownleft);
        bob2left = BitmapFactory.decodeResource(getResources(), R.drawable.bobleft);
    }

    public synchronized void draw() {
        // Make sure our drawing surface is valid or we crash
        if (ourHolder.getSurface().isValid()) {
            //Log.d(LOG_TAG, "drawing");
            // Lock the canvas ready to draw
            // Make the drawing surface our canvas item
            canvas = ourHolder.lockCanvas();

            if (gameEngine.isGameOver()) {

                //gameEngine.platforms.clear();

                Intent intent = new Intent(getContext(), EndGameMultiActivity.class);
                getContext().startActivity(intent);
//                // Draw the background
//                canvas.drawBitmap(bitmapBG, 0, 0, paint);
//
//                Paint gameOverPaint = new Paint();
//                gameOverPaint.setColor(Color.argb(255, 249, 129, 0));
//                gameOverPaint.setTextAlign(Paint.Align.CENTER);
//                gameOverPaint.setTextSize(100);
//                canvas.drawText("GAME OVER", canvas.getWidth() / 2, (canvas.getHeight() / 2) - 100, gameOverPaint);
//
//                Paint textPaint = new Paint();
//                textPaint.setColor(Color.argb(255, 249, 129, 0));
//                textPaint.setTextAlign(Paint.Align.CENTER);
//                textPaint.setTextSize(100);
//                canvas.drawText(""+utility.getPoints(), canvas.getWidth()/2,  (canvas.getHeight() / 2) + 100, textPaint);

            }

            else {

                Paint textPaint = new Paint();
                textPaint.setColor(Color.argb(255, 249, 129, 0));
                textPaint.setTextAlign(Paint.Align.CENTER);
                textPaint.setTextSize(100);
                //canvas.drawText("" + utility.getPoints(), canvas.getWidth() / 2, (canvas.getHeight() / 2) + 100, textPaint);

                // Draw the background
                canvas.drawBitmap(bitmapBG, 0, 0, paint);

                // Choose the brush color for drawing
                paint.setColor(Color.argb(255, 249, 129, 0));

                // Make the text a bit bigger
                paint.setTextSize(40);

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
                if (enginee.player2.pY > -100 && enginee.player2.pY < 2000 && enginee.isStart()){
                    canvas.drawBitmap(bob2left, enginee.player2.pX, enginee.player2.pY, paint);
                }
                else if (enginee.player2.pY < - 101) {
                    canvas.drawBitmap(boolBobUp, enginee.player2.pX, 0, paint);
                }
                else if (enginee.player2.pY > utility.getPixelHeight() - 100) {
                    canvas.drawBitmap(boolBobDown, enginee.player2.pX, utility.getPixelHeight() - 450, paint);
                }
                canvas.drawBitmap(bitmapJETPACK, gameEngine.jetpack.getpX(), gameEngine.jetpack.getpY(), paint);
                canvas.drawBitmap(bitmapEnemy, gameEngine.enemy.getpX(), gameEngine.enemy.getpY(), paint);

                // Display the current fps on the screen
                //canvas.drawText("FPS:" + fps, 30, 50, paint);
                //canvas.drawText("Device Record:" + records.getRecords().get(0), 30, 95, paint);
                //canvas.drawText("Your Points:" + utility.getPoints(), 30, 140, paint);

                canvas.drawText("Device Record:" + records.getRecords().get(0), 30, 50, paint);
                canvas.drawText("Your Points:" + utility.getPoints(), 30, 95, paint);

                try {
                    for (Bullet b : gameEngine.bullets) {
                        canvas.drawBitmap(bitmapBULLET, b.getpX(), b.getpY(), paint);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                if(!enginee.isStart()){
                    Paint waitPaint = new Paint();
                    waitPaint.setColor(Color.argb(255, 249, 129, 0));
                    waitPaint.setTextAlign(Paint.Align.CENTER);
                    waitPaint.setTextSize(60);
                    canvas.drawText("WAIT SECOND PLAYER", canvas.getWidth() / 2, (canvas.getHeight() / 2), waitPaint);
                }
            }
            // Draw everything to the screen and unlock the drawing surface
            ourHolder.unlockCanvasAndPost(canvas);
        }

    }
}
