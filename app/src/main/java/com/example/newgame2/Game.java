package com.example.newgame2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.core.content.ContextCompat;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Player player;
    private final Joystick joystick;
    private final Enemy enemy;
    private GameLoop gameLoop;
    private Context context;
    public Game(Context context) {
        super(context);

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        this.context = context;
        this.gameLoop = new GameLoop(this, surfaceHolder);
        this.joystick = new Joystick(200,900,70,40);
        this.player = new Player(getContext(),joystick,0,0);
        this.enemy = new Enemy(getContext(),player,0,0);
        setFocusable(true); //events are dispatched to the focused component
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (joystick.isPressed((double) event.getX(), (double) event.getY())) {
                    joystick.setIsPressed(true);
                }
                return true; //event has been handled
            case MotionEvent.ACTION_MOVE:
                if(joystick.getIsPressed()) {
                    joystick.setActuator((double)event.getX(),(double)event.getY());
                }
                return true;
            case MotionEvent.ACTION_UP:
                joystick.setIsPressed(false);
                joystick.resetActuator();
                return true;
        }
        return super.onTouchEvent(event);
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawUPS(canvas);
        drawFPS(canvas);
        this.player.draw(canvas);
        this.joystick.draw(canvas);
        this.enemy.draw(canvas);
    }
    public void drawUPS(Canvas canvas) {
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.cyan);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText( "UPS: " + averageUPS, 100, 100, paint);
        Bitmap playerBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gard);
        canvas.drawText("getWidth " + playerBitmap.getHeight(), 100,300,paint);
    }
    public void drawFPS(Canvas canvas) {
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.cyan);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText( "FPS: " + averageFPS, 100, 200, paint);
    }

    public void update() {
        joystick.update();
        player.update();
        enemy.update();
    }
}
