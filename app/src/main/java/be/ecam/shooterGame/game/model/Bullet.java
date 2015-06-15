package be.ecam.shooterGame.game.model;

import android.graphics.Rect;

import be.ecam.shooterGame.game.main.GameMainActivity;

/**
 * Bullet class
 * with all the bullet's properties
 * as position, speeds and state
 *
 * Created by Ikram-David-Zoubida on 13/05/15
 * Reference : http://jamescho7.com/book
 */
public class Bullet {
    private int x, y, width, height, velX;
    private boolean visible;
    private Rect rect;
    private final static int MOVE_SPEED = 500;

    public Bullet(int startX, int startY, int width, int height) {
        this.x = startX;
        this.y = startY;
        this.width = width;
        this.height = height;
        velX = MOVE_SPEED;
        rect = new Rect((int)x, (int)y, (int)x + width, (int)y + height);
        visible = true;
    }

    public void update(float delta) {
        if (isOut() == true){ //if the bullet is out, it cannot be visible and shoot enemies out of the frame
            visible=false;
        }
        else{
            x += velX*delta;
        }
        updateRect();
    }

    //returns true if the bullet exceed the frame
    public boolean isOut() {
        return (x < 0 || x + width > GameMainActivity.GAME_WIDTH);
    }

    private void updateRect() {
        rect.set((int) x, (int) y, (int) x + width, (int) y + height);
    }

    public void onCollideWith(Enemy s) {
        System.out.println("Laser collides with enemy");
        s.setAlive(false);
        visible = false;
    }

    public boolean isVisible(){
        return visible;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVelX(){
        return velX;
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public void setWidth (int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
