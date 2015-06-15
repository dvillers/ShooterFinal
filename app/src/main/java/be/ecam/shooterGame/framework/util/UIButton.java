package be.ecam.shooterGame.framework.util;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * This class contains all of the logic needed to create a button
 * and to handle button presses and button rendering.
 * So it allows to create, render and handle buttons much easier
 *
 * Created by Ikram-David-Zoubida on 14/06/15
 * Reference : http://jamescho7.com/book
 */
public class UIButton {
    private Rect buttonRect;
    private boolean buttonDown = false;
    private Bitmap buttonImage, buttonDownImage;

    public UIButton(int left, int top, int right, int bottom, Bitmap buttonImage, Bitmap buttonPressedImage) {
        buttonRect = new Rect(left, top, right, bottom);
        this.buttonImage = buttonImage;
        this.buttonDownImage = buttonPressedImage;
    }

    public void render(Painter g) {
        Bitmap currentButtonImage;
        if (buttonDown){
            currentButtonImage = buttonDownImage;
        } else {
            currentButtonImage = buttonImage;
        }
        //Bitmap currentButtonImage = buttonDown ? buttonDownImage : buttonImage;

        g.drawImage(currentButtonImage, buttonRect.left, buttonRect.top, buttonRect.width(), buttonRect.height());
    }

    public void onTouchDown(int touchX, int touchY) {
        if (buttonRect.contains(touchX, touchY)) {
            buttonDown = true;
        } else {
            buttonDown = false;
        }
    }

    public void cancel() {
        buttonDown = false;
    }

    public boolean isPressed(int touchX, int touchY) {
        return buttonDown && buttonRect.contains(touchX, touchY);
    }
}
