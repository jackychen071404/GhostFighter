package com.example.newgame2.gameobjects;

import android.content.Context;
import android.graphics.Canvas;

import com.example.newgame2.GameDisplay;
import com.example.newgame2.GameLoop;
import com.example.newgame2.spritesAndGraphics.Sprite;

//Enemy is an extension of GameObject, sprite that goes to towards player to cause damage
public class Enemy extends GameObject {
    private static final int SPEED_CHANGE = 50; //speed enemy changes by as game goes on
    private static int speed = 200;
    private static double finalSpeed;
    private static final double SPAWNS_PER_MIN = 20;
    private static final double UPDATES_UNTIL_SPAWN = GameLoop.MAX_UPS/(SPAWNS_PER_MIN/60);
    private static double nextSpawn = UPDATES_UNTIL_SPAWN;
    private final Player player;    //to use as reference for Player distance
    private Sprite sprite;  //stores image

    public Enemy(Context context, Player player, int x, int y, Sprite sprite, int spawnCount) {
        super(x, y);
        this.player = player;
        this.sprite = sprite;

        //increase speed for every 10 enemies that have spawned
        int speedChange = spawnCount/10;
        for(int i = 0; i<speedChange; i++)
        {
            speed+=SPEED_CHANGE;
        }
        this.finalSpeed = speed/ GameLoop.MAX_UPS; //pixels per second/max_UPS
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
        if(getDistance(this, a) <= 64)  //leave some room for glitches so 64 instead of 0
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
            velocityX = directionX*finalSpeed;
            velocityY = directionY*finalSpeed;
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
