package com.example.newgame2.gameobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.newgame2.GameDisplay;
import com.example.newgame2.GameLoop;
import com.example.newgame2.gameobjects.Enemy;
import com.example.newgame2.gameobjects.GameObject;
import com.example.newgame2.gameobjects.Player;

//class attack extends gameobject and makes a circle around player destroying enemies
public class Attack extends GameObject {
    private final Player player;
    private Paint paint;    //for color of attack
    private int radius; //for size of attack
    private static final double DESPAWN_FREQUENCY = 10;  //CHANGE ATTACK DESPAWN RATE
    private static final double UPDATES_UNTIL_DESPAWN = GameLoop.MAX_UPS/DESPAWN_FREQUENCY;
    private static double nextDespawn = UPDATES_UNTIL_DESPAWN;

    public Attack(Player player) {
        super(player.getX(),player.getY());
        this.player = player;

        //attack color
        paint = new Paint();
        paint.setColor(Color.YELLOW);

        //attack range
        radius = 150;
    }

    //check if should despawn
    public static boolean despawn() {
        if(nextDespawn <= 0) {
            nextDespawn += UPDATES_UNTIL_DESPAWN;
            return true;
        }
        else {
            nextDespawn--;
            return false;
        }
    }

    //check if attack is touching another game object (like enemy)
    public boolean touching(GameObject a) {
        if(getDistance(this, a) <= 5+radius)  //leave some room for glitches so 5 instead of 0
            return true;
        return false;
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        //size of attack around player, convert so relative to player centered screen
        canvas.drawCircle(
                (float) gameDisplay.gameToDisplayX(player.getX()),
                (float) gameDisplay.gameToDisplayY(player.getY()),
                radius, paint);
    }

    @Override
    public void update() {
        //make attack stick to player
        x = player.getX();
        y = player.getY();
    }
}
