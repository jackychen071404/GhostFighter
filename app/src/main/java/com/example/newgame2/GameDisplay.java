package com.example.newgame2;

import android.graphics.Rect;

import com.example.newgame2.gameobjects.GameObject;

public class GameDisplay {
    public final Rect SCREEN_SIZE;
    private double gameToDisplayXDifference;
    private double gameToDisplayYDifference;
    private double displayCenterX;
    private double displayCenterY;
    private double gameCenterX;
    private double gameCenterY;
    private GameObject centerObject;

    public GameDisplay(int width, int height, GameObject centerObject) {
        this.centerObject = centerObject;
        SCREEN_SIZE = new Rect(0,0,width,height);

        displayCenterX = width/2;
        displayCenterY = height/2;
    }

    public void update() {
        gameCenterX = centerObject.getX();
        gameCenterY = centerObject.getY();

        gameToDisplayXDifference = displayCenterX - gameCenterX;
        gameToDisplayYDifference = displayCenterY - gameCenterY;
    }

    public double gameToDisplayX(double x) {
        return x + gameToDisplayXDifference;
    }

    public double gameToDisplayY(double y) {
        return y + gameToDisplayYDifference;
    }

    public Rect getScreenSize() {
        return new Rect((int) (gameCenterX - displayCenterX), (int)(gameCenterY - displayCenterY), (int)(gameCenterX + displayCenterX), (int)(gameCenterY + displayCenterY));
    }
}
