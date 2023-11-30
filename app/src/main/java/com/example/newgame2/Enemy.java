package com.example.newgame2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

//Enemy is an extension of GameObject
public class Enemy extends GameObject{
    private static final double max_speed = 200.0/GameLoop.MAX_UPS; //pixels per second/max_UPS
    private final Player player;    //to use as reference for Player distance
    private Bitmap enemyBitmap;     //stores image
    private Rect enemyRect;

    public Enemy(Context context, Player player, int x, int y) {
        super(x, y);
        this.player = player;

        //enemy image
        enemyBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.box);
        if (enemyBitmap == null) {
            Log.e("Enemy", "Failed to load enemy image");
        }
        enemyRect = new Rect(x, y, x+enemyBitmap.getWidth(), y+enemyBitmap.getHeight());
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(enemyBitmap, null, enemyRect, null);
    }

    public void update() {
        //calculate distance to player
        double distanceX = player.getX() - x;
        double distanceY = player.getY() - y;
        double distancePlayer = GameObject.getDistance(this,player);

        //calculate direction
        double directionX = distanceX/distancePlayer;
        double directionY = distanceY/distancePlayer;

        //set velocity, make sure velocity 0 when same position
        if(distancePlayer > 0) {
            velocityX = directionX*max_speed;
            velocityY = directionY*max_speed;
        }
        else {
            velocityX = 0;
            velocityY = 0;
        }

        //update position
        x += velocityX;
        y += velocityY;

        //set new position
        this.setPosition(x,y);
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
        enemyRect.set((int) x, (int) y, (int) (x + enemyBitmap.getWidth()), (int) (y + enemyBitmap.getHeight()));
    }

}
