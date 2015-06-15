package be.ecam.shooterGame.game.model;

import android.graphics.Rect;

import be.ecam.shooterGame.game.main.GameMainActivity;

/**
 * Player class
 * with the playerShip properties
 * as position, speeds and state
 *
 * Created by Ikram-David-Zoubida on 13/05/15
 * Reference : http://jamescho7.com/book
 * */
public class Ship {
    private float x, y;
    private int width, height, velY = 0, velX =0;
    private Rect rect;
    private final static int MOVE_SPEED = 150;

    private boolean isAlive;

    public Ship(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        rect = new Rect((int)x, (int)y, (int)x + width, (int)y + height);

        isAlive=true;
    }

    public void update(float delta) {
        //adds velY to the y once per frame => has the effect of moving the playership to a y-position of y + velY
        //limit the moving of the ship only inside the game to never pass the frame
        y += velY*delta ;
        if (y < 0) {
            y = 0;
        } else if (y + height > GameMainActivity.GAME_HEIGHT) {
            y = GameMainActivity.GAME_HEIGHT - height;
        }
        x += velX*delta;
        if (x < 0) {
            x = 0;
        } else if (x + width > GameMainActivity.GAME_WIDTH) {
            x = GameMainActivity.GAME_WIDTH - width;
        }
        updateRect();
    }

    //by collision with an enemy the player is killed by setting his setAlive variable to false
    public void onCollideWith(Enemy s) {
        setAlive(false);
    }

    //by collision with an meteor the player is killed by setting his setAlive variable to false
    public void onCollideWith(Meteor m) {
        setAlive(false);
    }

    //takes the updated coordinates of the playership
    //and moves the bounding box to the same position
    private void updateRect() {
        rect.set((int)x, (int)y, (int)x + width, (int)y + height);
    }

    public void stop() {
        velY = 0;
        velX = 0;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x=x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y=y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rect getRect() {
        return rect;
    }
}
