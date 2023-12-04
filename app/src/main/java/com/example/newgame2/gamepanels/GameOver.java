package com.example.newgame2.gamepanels;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

//This class draws Game Over + the score on the screen when player dead
public class GameOver {
    public GameOver() {}

    //display game over and score
    public void draw(Canvas canvas, int enemyDeathCount){
        String gg = "Game Over";
        String score = "Your score was " + enemyDeathCount;

        Paint paint = new Paint();
        paint.setColor(Color.RED);  //red game over
        paint.setTextSize(150); //all text this size
        canvas.drawText(gg, 500, 200, paint);
        paint.setColor(Color.WHITE);    //white score display
        canvas.drawText(score, 500, 400, paint);
    }
}
