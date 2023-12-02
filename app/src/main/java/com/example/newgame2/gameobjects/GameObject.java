package com.example.newgame2.gameobjects;

import android.graphics.Canvas;

//GENERAL FOUNDATION FOR ALL OBJECTS IN GAME
public abstract class GameObject {
    protected double y;
    protected double x;
    protected double velocityX;
    protected double velocityY;

    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
    }

    //find distance between two objects
    protected static double getDistance(GameObject a, GameObject b)
    {
        return Math.sqrt(Math.pow(a.getX()-b.getX(),2) + Math.pow(a.getY()-b.getY(),2));
    }

    public abstract void draw(Canvas canvas);
    public abstract void update();

    //
    public double getX() {return x;}
    public double getY() {return y;}
    public double getVelocityX() {return velocityX;}
    public double getVelocityY() {return velocityY;}
}
