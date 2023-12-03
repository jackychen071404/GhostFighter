package com.example.newgame2.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.newgame2.spritesAndGraphics.Sprite;
import com.example.newgame2.spritesAndGraphics.SpriteSheet;

//extends tile, make a desert tile
public class DesertTile extends Tile {
    private final Sprite sprite;

    public DesertTile(SpriteSheet spriteSheet, Rect mapLocation) {
        super(mapLocation);
        sprite = spriteSheet.getDesertSprite();

    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas, mapLocation.left, mapLocation.top);
    }
}
