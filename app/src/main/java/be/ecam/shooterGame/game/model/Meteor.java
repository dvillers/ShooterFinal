package be.ecam.shooterGame.game.model;

import android.graphics.Rect;

import be.ecam.shooterGame.framework.util.RandomNumberGenerator;
import be.ecam.shooterGame.game.main.GameMainActivity;

/**
 * Meteor class to display asteroid coming
 * from the right to the left of the screen
 * with the same speed
 *
 * Created by Ikram-David-Zoubida on 13/05/15
 * Reference : http://jamescho7.com/book
 */
public class Meteor {
    private float x, y;
    private int width, height;
    private static final int VELX = -50;
    private Rect rect;

    public Meteor(float x, float y, int width, int height){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;

        rect = new Rect((int)x, (int)y, (int)x + width, (int)y + height);
    }

    public void update(float delta) {
        x += VELX * delta;
        if (x <= -200) {
            // Reset to the right
            x = RandomNumberGenerator.getRandIntBetween(1000, 1800);
            y = RandomNumberGenerator.getRandIntBetween(0, GameMainActivity.GAME_HEIGHT-height);
        }
        updateRect();
    }

    private void updateRect() {
        rect.set((int)x, (int)y, (int)x + width, (int)y + height);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Rect getRect() {
        return rect;
    }
}
