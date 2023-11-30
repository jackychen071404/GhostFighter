package com.example.newgame2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

//MAIN CHARACTER OF GAME, PLAYER IS EXTENSION OF GameObject
public class Player extends GameObject{
    private static final double max_speed = 400.0/GameLoop.MAX_UPS; //pixels per second/max_UPS
    private final Joystick joystick;
    private Bitmap playerBitmap; //this stores images
    private Rect playerRect;

    public Player(Context context, Joystick joystick, int x, int y) {
        super(x, y);
        this.joystick = joystick;

        //get the image of the player
        playerBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gard);
        if (playerBitmap == null) {
            Log.e("Player", "Failed to load player image");
        }
        playerRect = new Rect(x, y, x+playerBitmap.getWidth(), y+playerBitmap.getHeight());
    }

    public void update() {
        //update velocity based on joystick
        velocityX = joystick.getActuatorX()*max_speed;
        velocityY = joystick.getActuatorY()*max_speed;

        //update position
        x += velocityX;
        y += velocityY;

        //set new position
        this.setPosition(x,y);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(playerBitmap, null, playerRect, null);
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
        playerRect.set((int) x, (int) y, (int) (x + playerBitmap.getWidth()), (int) (y + playerBitmap.getHeight()));
    }
}
