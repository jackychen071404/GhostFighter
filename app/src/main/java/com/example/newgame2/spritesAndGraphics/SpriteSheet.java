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
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.box, bitmapOptions);
    }

    public Sprite getPlayerSprite() {
        return new Sprite(this, new Rect(0,0,32,32));
    }

    public Sprite getEnemySprite() {
        return new Sprite(this, new Rect(0,0,32,32));
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
