package com.example.newgame2.spritesAndGraphics;

import android.graphics.Canvas;
import android.graphics.Rect;

//sprite class for sprites based on the sprite_sheet
public class Sprite {

    private final SpriteSheet spriteSheet;
    private final Rect rect;

    public Sprite(SpriteSheet spriteSheet, Rect rect) {
        this.spriteSheet = spriteSheet;
        this.rect = rect;
    }

    public void draw(Canvas canvas, int x, int y) {
        canvas.drawBitmap(spriteSheet.getBitmap(), rect, new Rect(x,y,x+getWidth(),y+getHeight()), null);
    }

    public int getWidth() {
        return rect.width();
    }
    public int getHeight() {
        return rect.height();
    }
}
