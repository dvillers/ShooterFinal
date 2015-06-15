package be.ecam.shooterGame.game.state;

import android.view.MotionEvent;

import be.ecam.shooterGame.framework.util.Painter;
import be.ecam.shooterGame.game.main.GameMainActivity;

/**
 * Abstract class to create a "Blueprint" for other States
 *
 * Created by Ikram-David-Zoubida on 13/05/15.
 * Reference : http://jamescho7.com/book
 */

public abstract class State {

    public void setCurrentState(State newState) { // transitioning to another state
        GameMainActivity.sGame.setCurrentState(newState);
    }

    public abstract void init();                  // it initialize any game objects that will be used throughout the game state.

    public abstract void update(float delta);     // to update every game object inside the game state
                                                  // it will receive a float value called delta,
                                                  // representing the amount of time that has passed since the previous iteration of update.
                                                  // This value will typically be 17 milliseconds in 60 FPS

    public abstract void render(Painter g);       // to render game images to the screen

    public abstract boolean onTouch(MotionEvent e, int scaledX, int scaledY); // called when the player touch the screen

}
