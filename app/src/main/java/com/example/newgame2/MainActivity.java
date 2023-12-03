package com.example.newgame2;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    private Game game;

    /*
    Main entry point of applicataion
      */  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set window to fullscreen
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set content view to game for rendering
        game = new Game(this);
        setContentView(game);
    }

    //THIS CODE IS TO MAKE IT SO THAT THE GAME AUTOMATICALLY PAUSES WHENEVER APP MINIMIZED
    @Override
    protected void onStart() {
      super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //pause
    @Override
    protected void onPause() {
        game.pause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        //do nothing to make sure nothing happens when back button pressed
    }
}