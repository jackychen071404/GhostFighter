package com.example.newgame2;

import com.example.newgame2.gameobjects.GameObject;

public class GameDisplay {
    private double gameToDisplayXDifference;
    private double gameToDisplayYDifference;
    private double displayCenterX;
    private double displayCenterY;
    private double gameCenterX;
    private double gameCenterY;
    private GameObject centerObject;

    public GameDisplay(int width, int height, GameObject centerObject) {
        this.centerObject = centerObject;

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
}
