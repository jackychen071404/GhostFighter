package com.example.newgame2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Player {
    private double y;
    private double x;
    private Bitmap playerBitmap; //this stores images
    private Rect playerRect;
    public Player(Context context, int x, int y) {
        this.x = x;
        this.y = y;
        //get the image of the player
        playerBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gardevoir);
        if (playerBitmap == null) {
            Log.e("Player", "Failed to load player image");
        }
        playerRect = new Rect(x, y, playerBitmap.getWidth(), playerBitmap.getHeight());
    }
    public void draw(Canvas canvas) {
        canvas.drawBitmap(playerBitmap, null, playerRect, null);
    }

    public void update() {
    }
}
