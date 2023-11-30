package com.example.newgame2.gamepanels;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Joystick {
    private Paint outerPaint;
    private Paint innerPaint;
    private int oCircleCenterX;
    private int oCircleCenterY;
    private int iCircleCenterX;
    private int iCircleCenterY;
    private int outerRadius;
    private int innerRadius;
    private double CenterTouchDistance;
    private boolean isPressed;
    private double actuatorX;
    private double actuatorY;

    public Joystick(int centerPositionX, int centerPositionY, int outerRadius, int innerRadius) {
        oCircleCenterX = centerPositionX;
        oCircleCenterY = centerPositionY;
        iCircleCenterX = centerPositionX;
        iCircleCenterY = centerPositionY;
        this.outerRadius = outerRadius;
        this.innerRadius = innerRadius;
        outerPaint = new Paint();
        outerPaint.setColor(Color.GRAY);
        outerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        innerPaint = new Paint();
        innerPaint.setColor(Color.WHITE);
        innerPaint.setStyle(Paint.Style.FILL_AND_STROKE);


    }
    public void draw(Canvas canvas) {
        canvas.drawCircle(
            oCircleCenterX,
            oCircleCenterY,
            outerRadius,
            outerPaint
        );
        canvas.drawCircle(
                iCircleCenterX,
                iCircleCenterY,
                innerRadius,
                innerPaint
        );
    }

    public void update() {
        updateInnerCirclePosition();
    }

    private void updateInnerCirclePosition() {
        iCircleCenterX = (int) (oCircleCenterX + actuatorX*outerRadius);
        iCircleCenterY = (int) (oCircleCenterY + actuatorY*outerRadius);
    }

    //from game.java, this will be able to see if the joystick has been pressed
    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public boolean isPressed(double touch_x, double touch_y) {
        //distance formula, center of outer joystick to touch
        CenterTouchDistance = Math.sqrt(
                Math.pow(oCircleCenterX - touch_x, 2) + Math.pow(oCircleCenterY - touch_y, 2)
        );
        return CenterTouchDistance < outerRadius;
    }

    public boolean getIsPressed() {
        return isPressed;
    }
    //set actuator's x and y value, so the radial value is between 0 and 1.
    //0 is not pulling joystick, 1 is extent of joystick
    public void setActuator(double touch_x, double touch_y) {
        double deltaX = touch_x - oCircleCenterX;
        double deltaY = touch_y - oCircleCenterY;
        double deltaDistance = Math.sqrt(
                Math.pow(deltaX, 2) + Math.pow(deltaY, 2)
        );
        if (deltaDistance < outerRadius) {
            actuatorX = deltaX/outerRadius;
            actuatorY = deltaY/outerRadius;
        } else {
            actuatorX = deltaX/deltaDistance;
            actuatorY = deltaY/deltaDistance;
        }
    }

    public double getActuatorX() {
        return actuatorX;
    }

    public double getActuatorY() {
        return actuatorY;
    }

    public void resetActuator() {
        actuatorX = 0.0;
        actuatorY = 0.0;
    }
}
