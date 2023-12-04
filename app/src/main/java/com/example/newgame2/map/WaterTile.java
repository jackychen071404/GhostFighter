package com.example.newgame2.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.newgame2.spritesAndGraphics.Sprite;
import com.example.newgame2.spritesAndGraphics.SpriteSheet;

//extends tile, make a water tile
public class WaterTile extends Tile {
    private final Sprite sprite;

    public WaterTile(SpriteSheet spriteSheet, Rect mapLocation) {
        super(mapLocation);
        sprite = spriteSheet.getWaterSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas, mapLocation.left, mapLocation.top);
    }
}
