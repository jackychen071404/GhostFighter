package com.example.newgame2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.core.content.ContextCompat;

import com.example.newgame2.gameobjects.Attack;
import com.example.newgame2.gameobjects.Enemy;
import com.example.newgame2.gameobjects.Player;
import com.example.newgame2.gamepanels.GameOver;
import com.example.newgame2.gamepanels.Joystick;
import com.example.newgame2.gamepanels.Restart;
import com.example.newgame2.map.Tilemap;
import com.example.newgame2.spritesAndGraphics.SpriteSheet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Player player;
    private final Joystick joystick;
    private List<Enemy> enemyList = new ArrayList<Enemy>(); //for the enemy spawns
    private List<Attack> attackList = new ArrayList<Attack>();  //for the attack around player
    private GameLoop gameLoop;
    private Context context;
    private GameOver gameOver;
    private Restart restart;
    private boolean restartIsDrawn;
    private int joystickPointerId = 0;
    private int numAttack = 0;
    private GameDisplay gameDisplay;
    private final Tilemap tilemap;
    private int screenWidth;
    private int screenHeight;
    private int enemyDeathCount = 0;    //counter to know when to change speed
    private int enemySpawnCount = 0;    //counter for score

    public Game(Context context) {
        super(context);

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        this.context = context;
        this.gameLoop = new GameLoop(this, surfaceHolder);

        //display metrics
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        //initialize game panels (for UI)
        this.gameOver = new GameOver();
        this.joystick = new Joystick(200,900,70,40);
        this.restart = new Restart();

        //initialize game objects
        SpriteSheet spriteSheet = new SpriteSheet(context);
        //player spawn location center of map
        this.player = new Player(getContext(),joystick,25*64,25*64, spriteSheet.getPlayerSprite());

        //initialize game display
        gameDisplay = new GameDisplay(screenWidth, screenHeight, player);

        //initialize map
        tilemap = new Tilemap(spriteSheet);

        setFocusable(true); //events are dispatched to the focused component
    }

    @Override
    //for whenever screen is touched
    public boolean onTouchEvent(MotionEvent event) {
        if(restartIsDrawn)restart.update((double)event.getX(),(double)event.getY());
        switch(event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (joystick.getIsPressed()){
                    //joystick was pressed before  -> attack
                    numAttack++;
                }
                else if (joystick.isPressed((double) event.getX(), (double) event.getY())) {
                    //joystick pressed & store Id
                    joystickPointerId = event.getPointerId(event.getActionIndex());
                    joystick.setIsPressed(true);
                }
                else if (restart.getIsPressed()&&restartIsDrawn) {
                    restart();
                }
                else {
                    //joystick not pressed -> attack
                    numAttack++;
                }
                return true; //event has been handled
            case MotionEvent.ACTION_MOVE:
                //joystick pressed then moved
                if(joystick.getIsPressed()) {
                    joystick.setActuator((double)event.getX(),(double)event.getY());
                }
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if(joystickPointerId == event.getPointerId(event.getActionIndex())) {
                    //joystick let go
                    joystick.setIsPressed(false);
                    joystick.resetActuator();
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    private void restart() {
        player.setNewHealth(10);
        player.setPosition(25*64,25*64);
        enemySpawnCount = 0;
        enemyList.clear();
        enemyDeathCount = 0;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //to avoid crash when app is exited
        if(gameLoop.getState().equals(Thread.State.TERMINATED)) {
            gameLoop = new GameLoop(this, holder);
        }
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        //draw map
        tilemap.draw(canvas,gameDisplay);

        //can display FPS and UPS by uncommenting below lines
        /*
        drawUPS(canvas);
        drawFPS(canvas);
         */

        //draw in joystick
        this.joystick.draw(canvas);

        //draw game objects
        for(Enemy enemy: enemyList) {
            enemy.draw(canvas, gameDisplay);
        }
        for(Attack attack: attackList) {
            attack.draw(canvas, gameDisplay);
        }
        this.player.draw(canvas, gameDisplay);

        //GAME OVER
        if(player.getHealth() <= 0) {
            gameOver.draw(canvas, enemyDeathCount);
            restart.draw(canvas);
            restartIsDrawn = true;
        }
    }

    //CONSTANTLY UPDATE STATUS OF GAME
    public void update() {
        //STOP GAME WHEN GAME OVER
        //if(player.getHealth() <= 0) {
        //    return; //stop updating
        //}
        if (!(player.getHealth() <= 0)) {
            restartIsDrawn = false;

            //update
            joystick.update();
            player.update();

            //when click -> numattack++, attack spawns, numattack--;
            while (numAttack > 0) {
                attackList.add(new Attack(player));
                numAttack--;
            }

            //spawn&update enemies
            if (Enemy.spawn()) {
                //counter for score
                enemySpawnCount++;

                SpriteSheet spriteSheet = new SpriteSheet(context);
                enemyList.add(new Enemy(getContext(), player,
                        (int) (Math.random() * screenWidth),
                        (int) (Math.random() * screenHeight),   //infinite enemy spawn at random location on screen
                        spriteSheet.getEnemySprite(), enemySpawnCount));
            }
            for (Enemy enemy : enemyList) {
                enemy.update();
            }

            //update attack
            for (Attack attack : attackList) {
                attack.update();
            }

            //check for enemy collision with player/attack
            Iterator<Enemy> enemyIterator = enemyList.iterator();
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();
                if (enemy.touching(player)) {
                    enemyIterator.remove();     //remove enemy if touching player
                    player.setHealth((int) (player.getHealth() - 1));
                    continue;
                }

                Iterator<Attack> attackIterator = attackList.iterator();
                while (attackIterator.hasNext()) {
                    Attack attack = attackIterator.next();
                    //if attack around for despawn time
                    if (attack.despawn()) {
                        attackIterator.remove();
                    }
                    //if collide
                    if (attack.touching(enemy)) {
                        enemyIterator.remove(); //remove enemy because die if touch attack
                        enemyDeathCount++;
                        break;
                    }
                }
            }
        }

        //update display
        gameDisplay.update();
    }

    //NOTE: the below functions are to display the FPS and UPS for testing
    public void drawUPS(Canvas canvas) {
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.cyan);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText( "UPS: " + averageUPS, 100, 100, paint);
        Bitmap playerBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_sheet);
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

    public void pause() {
        gameLoop.stopLoop();
    }
}
