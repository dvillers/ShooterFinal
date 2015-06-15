package be.ecam.shooterGame.game.model;

import android.graphics.Rect;

import be.ecam.shooterGame.framework.util.RandomNumberGenerator;
import be.ecam.shooterGame.game.main.GameMainActivity;

/**
 * Enemy class to display enemies coming
 * from the right to the left of the screen
 * Plus it contain all the enemy's properties
 * as position, speeds and state
 *
 * Created by Ikram-David-Zoubida on 13/05/15
 * Reference : http://jamescho7.com/book
 */
public class Enemy {
    private int x, y;
    private int width, height, velX;
    private boolean visible = true;
    private boolean isAlive;
    private Rect rect;

    public Enemy(int width, int height) {

        this.width = width;
        this.height = height;

        y = RandomNumberGenerator.getRandIntBetween(0, GameMainActivity.GAME_HEIGHT - width);
        x = RandomNumberGenerator.getRandIntBetween(GameMainActivity.GAME_WIDTH, GameMainActivity.GAME_WIDTH*4);
        velX = RandomNumberGenerator.getRandIntBetween(100, 150);

        rect = new Rect((int) x, (int) y, (int) x + width, (int) y + height);

        isAlive=true;
    }

    public void update(float delta) {
        x -= this.velX*delta; //gives a speed to an enemy
        if (x <= -50) {       //when the enemy exceed the frame, we reset his position with a new one (from the right)
            reset();
        }
        updateRect();
    }

    private void updateRect() {
        rect.set((int) x, (int) y, (int) x + width, (int) y + height);
    }

    public void reset() {
        visible = true;
        x += 1000;
        y = RandomNumberGenerator.getRandIntBetween(0,GameMainActivity.GAME_HEIGHT-width);
    }

    public void onCollideWith() {
        visible = false;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public boolean isVisible() {
        return visible;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

}
