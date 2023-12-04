package com.example.newgame2.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.newgame2.spritesAndGraphics.Sprite;
import com.example.newgame2.spritesAndGraphics.SpriteSheet;

//extends tile, make a lava tile
public class LavaTile extends Tile {
    private final Sprite sprite;

    public LavaTile(SpriteSheet spriteSheet, Rect mapLocation) {
        super(mapLocation);
        sprite = spriteSheet.getLavaSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas, mapLocation.left, mapLocation.top);
    }
}
