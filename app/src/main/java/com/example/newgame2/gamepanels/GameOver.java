package com.example.newgame2.gamepanels;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

//This class draws Game Over on the screen when player dead
public class GameOver {
    public GameOver() {}

    public void draw(Canvas canvas){
        String text = "Game Over";

        float x = 800;
        float y = 200;
        float textSize = 150;

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(textSize);
        canvas.drawText(text, x, y, paint);
    }
}
