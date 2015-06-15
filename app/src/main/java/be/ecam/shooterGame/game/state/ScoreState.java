package be.ecam.shooterGame.game.state;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.MotionEvent;

import be.ecam.shooterGame.framework.util.Painter;
import be.ecam.shooterGame.game.main.Assets;
import be.ecam.shooterGame.game.main.GameMainActivity;

/**
 * Created by Ikram on 15/06/15
 */
public class ScoreState extends State {

    private String highScore;

    @Override
    public void init() {
        highScore = GameMainActivity.getHighScore() + "";
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Painter g) {
        g.drawImage(Assets.scoreBack, 0, 0, Assets.scoreBack.getWidth(), Assets.scoreBack.getHeight());
        g.drawImage(Assets.explosion, 110, 150);
        g.drawImage(Assets.explosion, 110, 150);
        g.setColor(Color.WHITE);
        g.setFont(Typeface.DEFAULT_BOLD,50);
        g.drawString("The All-Time High Score...", 120, 175);
        g.setFont(Typeface.DEFAULT_BOLD, 70);
        g.drawString(highScore , 330, 295);
        g.setFont(Typeface.DEFAULT_BOLD, 30);
        g.drawString("Touch the screen", 500, 430);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            setCurrentState(new MenuState());
        }
        return true;
    }
}
