package com.example.newgame2.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.newgame2.spritesAndGraphics.Sprite;
import com.example.newgame2.spritesAndGraphics.SpriteSheet;

public class GrassTile extends Tile {
    private final Sprite sprite;

    public GrassTile(SpriteSheet spriteSheet, Rect mapLocation) {
        super(mapLocation);
        sprite = spriteSheet.getGrassSprite();

    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas, mapLocation.left, mapLocation.top);
    }
}
