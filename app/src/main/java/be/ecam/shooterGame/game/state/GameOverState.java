package be.ecam.shooterGame.game.state;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.MotionEvent;

import be.ecam.shooterGame.framework.util.Painter;
import be.ecam.shooterGame.game.main.Assets;
import be.ecam.shooterGame.game.main.GameMainActivity;

/**
 * Game over screen
 * Displays the score when the player has been killed
 *
 * Created by Ikram-David-Zoubida on 14/05/15
 * Reference : http://jamescho7.com/book
 */
public class GameOverState extends State {

    private String playerScore;
    private String highScoreMessage = "You can do better !!!";

    public GameOverState (int playerScore){
        this.playerScore = playerScore + ""; // Convert int to String
        Assets.playSound(Assets.GameOver);
        if (playerScore > GameMainActivity.getHighScore()) {
            GameMainActivity.setHighScore(playerScore);
            highScoreMessage = "GREAT!! Best Score ! ";
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Painter g) {
        g.drawImage(Assets.gameOver, 0, 0);
        g.setColor(Color.WHITE);
        g.setFont(Typeface.DEFAULT_BOLD,35);
        g.drawString("Score : " + playerScore, 5, 30);
        g.drawString(highScoreMessage, 120, 80);
        g.drawString("Touch the screen ", 500, 430);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            setCurrentState(new MenuState()); //by any key press, it will send to the Menu
        }
        return true;
    }
}
