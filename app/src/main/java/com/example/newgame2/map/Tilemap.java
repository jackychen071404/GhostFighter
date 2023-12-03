package com.example.newgame2.map;

import static com.example.newgame2.map.MapLayout.COLUMNS;
import static com.example.newgame2.map.MapLayout.ROWS;
import static com.example.newgame2.map.MapLayout.TILE_HEIGHT_PIXELS;
import static com.example.newgame2.map.MapLayout.TILE_WIDTH_PIXELS;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.newgame2.GameDisplay;
import com.example.newgame2.spritesAndGraphics.SpriteSheet;

public class Tilemap {
    private final MapLayout mapLayout;
    private Tile[][] tilemap;
    private SpriteSheet spriteSheet;
    private Bitmap mapBitmap;

    public Tilemap(SpriteSheet spriteSheet) {
        mapLayout = new MapLayout();
        this.spriteSheet = spriteSheet;
        makeTilemap();
    }

    private void makeTilemap() {
        int[][] layout = mapLayout.getLayout();
        tilemap = new Tile[ROWS][COLUMNS];
        for(int row=0;row<ROWS; row++) {
            for(int col=0; col<COLUMNS; col++) {
                tilemap[row][col] = Tile.getTile(layout[row][col], spriteSheet, getRect(row, col));
            }
        }
        
        Bitmap.Config config = Bitmap.Config.ARGB_8888;     //quality of map image
        mapBitmap = Bitmap.createBitmap(COLUMNS*TILE_WIDTH_PIXELS, ROWS*TILE_HEIGHT_PIXELS, config);

        Canvas mapCanvas = new Canvas(mapBitmap);
        for(int row=0;row<ROWS; row++) {
            for(int col=0; col<COLUMNS; col++) {
                tilemap[row][col].draw(mapCanvas);
            }
        }
    }

    private Rect getRect(int row, int col) {
        return new Rect(col*TILE_WIDTH_PIXELS, row*TILE_HEIGHT_PIXELS, (col+1)*TILE_WIDTH_PIXELS, (row+1)*TILE_HEIGHT_PIXELS);
    }

    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        canvas.drawBitmap(mapBitmap,gameDisplay.getScreenSize(), gameDisplay.SCREEN_SIZE, null);
    }
}
