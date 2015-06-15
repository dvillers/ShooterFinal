package be.ecam.shooterGame.game.state;

import android.util.Log;
import android.view.MotionEvent;

import be.ecam.shooterGame.framework.util.Painter;
import be.ecam.shooterGame.framework.util.UIButton;
import be.ecam.shooterGame.game.main.Assets;
import be.ecam.shooterGame.game.main.GameMainActivity;

/**
 * "Welcome" screen
 * Displays information regarding the game
 *
 * Created by Ikram-David-Zoubida on 13/05/15
 * Reference : http://jamescho7.com/book
 */
public class MenuState extends State {

    // declare UIButton object for each button
    private UIButton playButton, scoreButton, exitButton ;

    @Override
    public void init() {
        // initialize the button Rects at the proper coordinates
        playButton = new UIButton((GameMainActivity.GAME_WIDTH / 2) - (Assets.startbutton.getWidth()/2), (GameMainActivity.GAME_HEIGHT/2) + 20, (GameMainActivity.GAME_WIDTH / 2) + (Assets.startbutton.getWidth()/2), (GameMainActivity.GAME_HEIGHT/2) + (Assets.startbutton.getHeight()) + 20, Assets.startbutton, Assets.startDownbutton);
        scoreButton = new UIButton((GameMainActivity.GAME_WIDTH / 2) - (Assets.scorebutton.getWidth()/2), (GameMainActivity.GAME_HEIGHT/2) + 90, (GameMainActivity.GAME_WIDTH / 2) + (Assets.scorebutton.getWidth()/2), (GameMainActivity.GAME_HEIGHT/2) + (Assets.scorebutton.getHeight())+ 90, Assets.scorebutton, Assets.scoreDownbutton);
        exitButton = new UIButton((GameMainActivity.GAME_WIDTH / 2) - (Assets.exitbutton.getWidth()/2), (GameMainActivity.GAME_HEIGHT/2) + 160, (GameMainActivity.GAME_WIDTH / 2) + (Assets.exitbutton.getWidth()/2), (GameMainActivity.GAME_HEIGHT/2) + (Assets.scorebutton.getHeight()) + 160, Assets.exitbutton, Assets.exitDownbutton);
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(Painter g) {
        g.drawImage(Assets.menu, 0, 0);
        g.drawImage(Assets.title,(GameMainActivity.GAME_WIDTH / 2) - (325/2), 10);
        playButton.render(g);
        scoreButton.render(g);
        exitButton.render(g);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            playButton.onTouchDown(scaledX, scaledY);
            scoreButton.onTouchDown(scaledX, scaledY);
            exitButton.onTouchDown(scaledX,scaledY);
        }

        if (e.getAction() == MotionEvent.ACTION_UP) {
            // If the play button is active and the release was within the play button
            if (playButton.isPressed(scaledX, scaledY)) {
                // Button has been released.
                playButton.cancel();
                // Perform an action here!
                Log.d("MenuState", "Play Button Pressed!");
                //transition to PlayState
                setCurrentState(new PlayState());

            // If score button is active and the release was within the score button
            } else if (scoreButton.isPressed(scaledX, scaledY)) {
                // Button has been released.
                scoreButton.cancel();
                //log message
                Log.d("MenuState", "Score Button Pressed!");
                //transition to ScoreState
                setCurrentState(new ScoreState());

            // If exit button is active and the release was within the exit button
            } else if (exitButton.isPressed(scaledX, scaledY)) {
                // Button has been released.
                exitButton.cancel();
                //log message
                Log.d("MenuState", "Exit Button Pressed!");
                //exiting the game
                GameMainActivity.sGame.exit();//System.exit(0);

            // If the finger was released anywhere else:
            } else {
                // Cancel all actions.
                playButton.cancel();
                scoreButton.cancel();
                exitButton.cancel();
            }
        }

        return true;
    }
}
