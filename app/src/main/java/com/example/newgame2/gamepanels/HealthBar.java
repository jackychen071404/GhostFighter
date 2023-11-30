package com.example.newgame2.gamepanels;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.newgame2.gameobjects.Player;

//HealthBar shows health bar
public class HealthBar {

    private Player player;
    private int width, height, margin;
    private Paint borderPaint, healthPaint;

    public HealthBar(Player player) {
        this.player = player;

        //set size of health bar here
        this.width = 500;
        this.height = 30;
        this.margin = 5;

        //set health bar color here
        this.borderPaint = new Paint();
        this.healthPaint = new Paint();
        borderPaint.setColor(Color.WHITE);
        healthPaint.setColor(Color.GREEN);
    }

    public void draw(Canvas canvas) {
        //position of bar on screen (top right corner of bar)
        float x = 1900;
        float y = 30;

        float HP_Percent = (float) player.getHealth()/player.MAX_HEALTH;

        //border of health bar
        float borderLeft = x - width;
        float borderRight = x;
        float borderBottom = y + height;
        float borderTop = y;

        canvas.drawRect(borderLeft,borderTop,borderRight,borderBottom,borderPaint);

        //the health bar
        float healthLeft = borderLeft + margin;
        float healthRight = healthLeft + (borderRight-margin-healthLeft)*HP_Percent; //only display remaining health
        float healthBottom = borderBottom - margin;
        float healthTop = y + margin;

        canvas.drawRect(healthLeft,healthTop,healthRight,healthBottom,healthPaint);
    }
}
