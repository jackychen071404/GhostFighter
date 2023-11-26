package com.example.newgame2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Player {
    private static final double max_speed = 400.0/GameLoop.MAX_UPS; //pixels per second/max_UPS
    private double y;
    private double x;
    private Bitmap playerBitmap; //this stores images
    private Rect playerRect;
    private double velocityX;
    private double velocityY;
    public Player(Context context, int x, int y) {
        this.x = x;
        this.y = y;
        //get the image of the player
        playerBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gard);
        if (playerBitmap == null) {
            Log.e("Player", "Failed to load player image");
        }
        playerRect = new Rect(x, y, x+playerBitmap.getWidth(), y+playerBitmap.getHeight());
    }
    public void draw(Canvas canvas) {
        canvas.drawBitmap(playerBitmap, null, playerRect, null);
    }

    public void update(Joystick joystick) {
        velocityX = joystick.getActuatorX()*max_speed;
        velocityY = joystick.getActuatorY()*max_speed;
        x += velocityX;
        y += velocityY;
        this.setPosition(x,y);
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
        playerRect.set((int) x, (int) y, (int) (x + playerBitmap.getWidth()), (int) (y + playerBitmap.getHeight()));
    }
}
