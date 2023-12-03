package com.example.newgame2.gamepanels;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

//This class draws Game Over on the screen when player dead
public class GameOver {
    public GameOver() {}

    public void draw(Canvas canvas, int enemyDeathCount){
        String gg = "Game Over";
        String deaths = "Your score was " + enemyDeathCount;

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(150);
        canvas.drawText(gg, 500, 200, paint);
        paint.setColor(Color.WHITE);
        canvas.drawText(deaths, 500, 400, paint);
    }
}
