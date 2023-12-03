package com.example.newgame2.spritesAndGraphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.newgame2.R;
import com.example.newgame2.spritesAndGraphics.Sprite;

public class SpriteSheet {
    private Bitmap bitmap;

    public SpriteSheet(Context context) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_sheet, bitmapOptions);
    }

    public Sprite getPlayerSprite() {
        return new Sprite(this, new Rect(0,0,64,64));
    }

    public Sprite getEnemySprite() {
        return new Sprite(this, new Rect(64,0,128,64));
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Sprite getDesertSprite() {return new Sprite(this, new Rect(0,64,64,128));}

    public Sprite getLavaSprite() {return new Sprite(this, new Rect(64,64,128,128));}

    public Sprite getGrassSprite() {return new Sprite(this, new Rect(128,64,192,128));}

}
