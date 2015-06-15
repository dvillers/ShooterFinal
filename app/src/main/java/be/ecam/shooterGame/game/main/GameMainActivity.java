package be.ecam.shooterGame.game.main;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

/**
 * Starting point of the game with the main method
 *
 * Created by Ikram-David-Zoubida on 12/05/15.
 * Reference : http://jamescho7.com/book
 */
public class GameMainActivity extends Activity {

    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 450;
    public static GameView sGame;
    public static AssetManager assets;
    private View decorView;

    private static SharedPreferences prefs;
    private static final String highScoreKey = "highScoreKey";
    private static int highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getPreferences(Activity.MODE_PRIVATE); //only our application can access the preference's content
        highScore = retrieveHighScore();
        assets = getAssets();
        sGame = new GameView(this, GAME_WIDTH, GAME_HEIGHT);
        setContentView(sGame);
        //to prevent to have the screen turns off when it has not been touched for several seconds
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //to recover the decor
        decorView = getWindow().getDecorView();
    }

    //is called when the player’s score is greater than the saved high score
    public static void setHighScore(int highScore) {
        GameMainActivity.highScore = highScore;
        Editor editor = prefs.edit();
        editor.putInt(highScoreKey, highScore);
        editor.commit(); //Editor object commits the changes to the new highScore by overwriting the existing values
    }

    //it retrieves the integer associated with highScoreKey (the highscore)
    //if there is no associated value, we use the default value of zero
    private int retrieveHighScore() {
        return prefs.getInt(highScoreKey, 0);
    }

    //it allows us to retrieve the high score without going into the file system => faster
    public static int getHighScore() {
        return highScore;
    }

    //hide system UI
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            // cache les deux barres + interaction possible
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            // cache la barre du bas + interaction possible
                            View.SYSTEM_UI_FLAG_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}

