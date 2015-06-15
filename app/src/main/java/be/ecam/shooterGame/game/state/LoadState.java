package be.ecam.shooterGame.game.state;

import android.view.MotionEvent;

import be.ecam.shooterGame.framework.util.Painter;
import be.ecam.shooterGame.game.main.Assets;

/**
 * This class represents the loading screen of our game
 * The initial state
 *
 * Created by Ikram-David-Zoubida on 13/05/15
 * Reference : http://jamescho7.com/book
 */
public class LoadState extends State  {

    @Override
    public void init() {
        Assets.load();
        System.out.println("Loaded Successfully");
    }

    @Override
    public void update(float delta) {
        setCurrentState(new MenuState()); // allows transition from the LoadState to the MenuState
    }

    @Override
    public void render(Painter g) {

    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        return false;
    }
}
