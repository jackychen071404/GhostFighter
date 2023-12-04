package com.example.newgame2.gamepanels;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

//This class draws Game Over on the screen when player dead
public class Restart {
    private Rect restartRect;
    private boolean isPressed;
    public Restart() {
        // Initialize variables if needed
        int left = 500;
        int top = 650;
        int right = left + 600;
        int bottom = top + 200;

        restartRect = new Rect(left, top, right, bottom);
    }

    public void draw(Canvas canvas){
        String gg = "Restart";

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(150);
        canvas.drawText(gg, 500, 800, paint);
        //canvas.drawRect(restartRect, paint);
    }

    public void update(double touch_x, double touch_y) {
        // Update the isPressed flag based on the touch event
        isPressed = restartRect.contains((int) touch_x, (int) touch_y);
        Log.d("tag",""+isPressed);
    }

    public boolean getIsPressed() {
        return isPressed;
    }
}
