package com.example.newgame2;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;

    public GameThread(SurfaceHolder surfaceHolder, GameView gameView) {
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        Canvas canvas;

        while (running) {
            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    private void drawSprite(Canvas canvas, int x, int y) {
        // Load the sprite image from the drawable resources
        Bitmap sprite = BitmapFactory.decodeResource(getResources(), R.drawable.sprite);

        // Calculate the sprite's source rectangle based on the current frame
        int frameWidth = sprite.getWidth() / numberOfFrames;
        int frameHeight = sprite.getHeight();
        Rect sourceRect = new Rect(currentFrame * frameWidth, 0, (currentFrame + 1) * frameWidth, frameHeight);

        // Calculate the sprite's destination rectangle based on the x and y coordinates
        Rect destRect = new Rect(x, y, x + frameWidth, y + frameHeight);

        // Draw the sprite onto the canvas
        canvas.drawBitmap(sprite, sourceRect, destRect, null);
    }

    public void update() {
        // Update the sprite's position and animation frame
        spriteX += 5;
        spriteY += 5;
        currentFrame = (currentFrame + 1) % numberOfFrames;
    }

}
