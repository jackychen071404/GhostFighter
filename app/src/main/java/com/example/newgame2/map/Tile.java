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
        GRASS,   //2
        WATER   //3
    }

    public static Tile getTile(int type, SpriteSheet spriteSheet, Rect mapLocation) {
       switch(TileType.values()[type]) {
           //create the tile based on their respective 0,1,2,3
           case DESERT:
               return new DesertTile(spriteSheet, mapLocation);
           case LAVA:
               return new LavaTile(spriteSheet, mapLocation);
           case GRASS:
               return new GrassTile(spriteSheet, mapLocation);
           case WATER:
               return new WaterTile(spriteSheet, mapLocation);
           default:
               //if not 0,1,2,3 then default to null to avoid errors
               return null;
       }
    }

    public abstract void draw(Canvas canvas);
}
