package com.example.newgame2.gameobjects;

import android.content.Context;
import android.graphics.Canvas;

import com.example.newgame2.GameDisplay;
import com.example.newgame2.GameLoop;
import com.example.newgame2.spritesAndGraphics.Sprite;

//Enemy is an extension of GameObject
public class Enemy extends GameObject {
    private static final double max_speed = 200.0/ GameLoop.MAX_UPS; //pixels per second/max_UPS
    private static final double SPAWNS_PER_MIN = 20;
    private static final double UPDATES_UNTIL_SPAWN = GameLoop.MAX_UPS/(SPAWNS_PER_MIN/60);
    private static double nextSpawn = UPDATES_UNTIL_SPAWN;
    private final Player player;    //to use as reference for Player distance
    private Sprite sprite;  //stores image

    public Enemy(Context context, Player player, int x, int y, Sprite sprite) {
        super(x, y);
        this.player = player;
        this.sprite = sprite;
    }

    //check if new enemy should spawn
    public static boolean spawn() {
        if(nextSpawn <= 0) {
            nextSpawn += UPDATES_UNTIL_SPAWN;
            return true;
        }
        else {
            nextSpawn--;
            return false;
        }
    }

    //checks if enemy is touching player
    public boolean touching(GameObject a) {
        if(getDistance(this, a) <= 5)  //leave some room for glitches so 5 instead of 0
            return true;
        return false;
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        sprite.draw(canvas,
                (int) gameDisplay.gameToDisplayX(getX() - sprite.getWidth()/2),
                (int) gameDisplay.gameToDisplayY(getY()) - sprite.getHeight()/2);
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
    }

}
