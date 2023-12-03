package com.example.newgame2.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.newgame2.spritesAndGraphics.SpriteSheet;

abstract class Tile {
    protected final Rect mapLocation;

    public Tile(Rect mapLocation) {
        this.mapLocation = mapLocation;
    }

    public enum TileType {
        DESERT,   //0
        LAVA, //1
        GRASS   //2
    }

    public static Tile getTile(int type, SpriteSheet spriteSheet, Rect mapLocation) {
       switch(TileType.values()[type]) {
           case DESERT:
               return new DesertTile(spriteSheet, mapLocation);
           case LAVA:
               return new LavaTile(spriteSheet, mapLocation);
           case GRASS:
               return new GrassTile(spriteSheet, mapLocation);
           default:
               return null;
       }
    }

    public abstract void draw(Canvas canvas);
}
