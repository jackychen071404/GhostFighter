package com.example.newgame2.gameobjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.example.newgame2.GameLoop;
import com.example.newgame2.gamepanels.Joystick;
import com.example.newgame2.R;
import com.example.newgame2.gamepanels.HealthBar;

//MAIN CHARACTER OF GAME, PLAYER IS EXTENSION OF GameObject
public class Player extends GameObject {
    public static final int MAX_HEALTH = 10;    //MAX HP
    private static final double max_speed = 400.0/ GameLoop.MAX_UPS; //pixels per second/max_UPS
    private final Joystick joystick;
    private Bitmap playerBitmap; //this stores images
    private Rect playerRect;
    private HealthBar healthBar;    //healthbar
    private int health;     //health points of character

    public Player(Context context, Joystick joystick, int x, int y) {
        super(x, y);
        this.joystick = joystick;
        this.healthBar = new HealthBar(this);
        this.health = MAX_HEALTH;   //set starting HP to MAX

        //get the image of the player
        playerBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.box);
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

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
        playerRect.set((int) x, (int) y, (int) (x + playerBitmap.getWidth()), (int) (y + playerBitmap.getHeight()));
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(playerBitmap, null, playerRect, null);
        healthBar.draw(canvas);
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(int hp) {
        //no negative health!
        if(hp >= 0)
            this.health = hp;
    }
}
