package com.example.newgame2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.newgame2.gameobjects.Enemy;
import com.example.newgame2.gameobjects.GameObject;
import com.example.newgame2.gameobjects.Player;

public class Attack extends GameObject {
    private final Player player;
    private Paint paint;
    private int radius;
    private static final double DESPAWN_FREQUENCY = 10;  //CHANGE ATTACK DESPAWN RATE
    private static final double UPDATES_UNTIL_DESPAWN = GameLoop.MAX_UPS/DESPAWN_FREQUENCY;
    private static double nextDespawn = UPDATES_UNTIL_DESPAWN;

    public Attack(Player player) {
        super(player.getX(),player.getY());
        this.player = player;

        //attack color
        paint = new Paint();
        paint.setColor(Color.WHITE);

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

    protected boolean touching(GameObject a) {
        if(getDistance(this, a) <= 5+radius)  //leave some room for glitches so 5 instead of 0
            return true;
        return false;
    }

    public void draw(Canvas canvas) {
        //size of attack around player
        canvas.drawCircle((float) player.getX(), (float) player.getY(), radius, paint);
    }

    @Override
    public void update() {
        //make attack stick to player
        x = player.getX();
        y = player.getY();
    }
}
