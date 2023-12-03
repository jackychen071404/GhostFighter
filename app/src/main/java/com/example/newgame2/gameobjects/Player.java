package com.example.newgame2.gameobjects;

import android.content.Context;
import android.graphics.Canvas;

import com.example.newgame2.GameDisplay;
import com.example.newgame2.GameLoop;
import com.example.newgame2.spritesAndGraphics.Sprite;
import com.example.newgame2.gamepanels.Joystick;
import com.example.newgame2.gamepanels.HealthBar;

//MAIN CHARACTER OF GAME, PLAYER IS EXTENSION OF GameObject
public class Player extends GameObject {
    public static final int MAX_HEALTH = 10;    //MAX HP
    private static final double max_speed = 400.0/ GameLoop.MAX_UPS; //pixels per second/max_UPS
    private final Joystick joystick;
    private HealthBar healthBar;    //healthbar
    private int health;     //health points of character
    private Sprite sprite;  //stores image

    public Player(Context context, Joystick joystick, int x, int y, Sprite sprite) {
        super(x, y);
        this.joystick = joystick;
        this.healthBar = new HealthBar(this);
        this.health = MAX_HEALTH;   //set starting HP to MAX
        this.sprite = sprite;
    }

    public void update() {
        //update velocity based on joystick
        velocityX = joystick.getActuatorX()*max_speed;
        velocityY = joystick.getActuatorY()*max_speed;

        //update position
        x += velocityX;
        y += velocityY;

        //set new position
        this.setPosition(x,y);

        //if player touching lava (out of bounds), die instantly
        if(x<64 || x>50*64 || y<64 || y>50*64) {
            this.setHealth(0);  // die >:(
        }
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        sprite.draw(canvas,
                (int) gameDisplay.gameToDisplayX(getX() - sprite.getWidth()/2),
                (int) gameDisplay.gameToDisplayY(getY()) - sprite.getHeight()/2);
        healthBar.draw(canvas, gameDisplay);
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(int hp) {
        //no negative health!
        if(hp >= 0)
            this.health = hp;
    }
}
