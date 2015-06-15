package be.ecam.shooterGame.framework.util;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import be.ecam.shooterGame.game.main.GameMainActivity;
import be.ecam.shooterGame.game.state.State;

/**
 * Listen for user to touch events
 * Dispatches the game's state classes to handle the touch on the screen
 * So it will be notified whenever the player touches the GameView
 *
 * Created by Ikram-David-Zoubida on 13/05/15.
 * Reference : http://jamescho7.com/book
 */
public class InputHandler implements OnTouchListener {
    private State currentState;

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int scaledX = (int) ((event.getX() / v.getWidth()) * GameMainActivity.GAME_WIDTH); //values is scaled with respect to the the game resolution
        int scaledY = (int) ((event.getY() / v.getHeight()) * GameMainActivity.GAME_HEIGHT);
        return currentState.onTouch(event, scaledX, scaledY);  //return true if we responded to the touch event, false otherwise.
    }
}
