package com.example.newgame2;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {
    public static final double MAX_UPS = 30.0; //determine our ideal UPS
    private static final double UPS_PERIOD = 1000/MAX_UPS; //the inverse of frequency, every millisecond
    private Game game;
    private SurfaceHolder surfaceHolder;
    private boolean Running = false;
    private double averageUPS;
    private double averageFPS;

    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;
    }

    public double getAverageUPS() {
        return averageUPS;
    }

    public double getAverageFPS() {
        return averageFPS;
    }

    public void startLoop() {
        Running = true;
        start();
    }

    @Override
    public void run() {
        super.run();

        int updateCount = 0;
        int frameCount = 0;

        long startTime;
        long elapsedTime;
        long sleepTime;

        Canvas canvas = null;
        startTime = System.currentTimeMillis(); //get the time of start of counting
        while(Running) {

            try {
                canvas = surfaceHolder.lockCanvas(); //update and render
                synchronized (surfaceHolder) { //check that all threads are running at same time
                    game.update();
                    updateCount++;
                    game.draw(canvas);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally { //finally keyword will run regardless of try and catch
                if(canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        frameCount++;
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            //Pause game loop to not exceed UPS
            elapsedTime = System.currentTimeMillis() - startTime;
            sleepTime = (long)(updateCount * UPS_PERIOD - elapsedTime);
            if (sleepTime > 0) {
                try {
                    sleep(sleepTime); //cease the thread if UPS is exceeded. Needs a try/catch
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            //skip frames to stay with target UPS
            while(sleepTime < 0 && updateCount < MAX_UPS-1) {
                game.update();
                updateCount++;
                elapsedTime = System.currentTimeMillis() - startTime;
                sleepTime = (long) (updateCount*UPS_PERIOD - elapsedTime);
            }

            //Calculate average UPS and FPS
            elapsedTime = System.currentTimeMillis() - startTime; //this line calculates time passed since start
            if (elapsedTime >= 1000) {
                averageUPS = updateCount / (0.001 * elapsedTime);
                averageFPS = frameCount / (0.001 * elapsedTime);
                updateCount = 0;
                frameCount = 0; //reset these counts
                startTime = System.currentTimeMillis();
            }

        }
    }
}
