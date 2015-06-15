package be.ecam.shooterGame.game.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import be.ecam.shooterGame.framework.util.InputHandler;
import be.ecam.shooterGame.framework.util.Painter;
import be.ecam.shooterGame.game.state.LoadState;
import be.ecam.shooterGame.game.state.State;

/**
 * The central class for our game.
 * It will contain the game loop
 * and the methods to start and exit the game
 *
 * Created by Ikram-David-Zoubida on 12/05/15.
 *
 * Reference : http://jamescho7.com/book
 */
public class GameView extends SurfaceView implements Runnable {

    private Bitmap gameImage;
    private Rect gameImageSrc;
    private Rect gameImageDst;
    private Canvas gameCanvas;
    private Painter graphics;

    private Thread gameThread;
    private volatile boolean running = false;
    private volatile State currentState;

    private InputHandler inputHandler;

    public GameView(Context context, int gameWidth, int gameHeight) {
        super(context);
        gameImage = Bitmap.createBitmap(gameWidth, gameHeight, Bitmap.Config.RGB_565);
        gameImageSrc = new Rect(0, 0, gameImage.getWidth(), gameImage.getHeight());
        gameImageDst = new Rect();
        gameCanvas = new Canvas(gameImage);
        graphics = new Painter(gameCanvas);

        //implementing a SurfaceHolder Callback to be informed when the surface has been created
        //and when the surface has been destroyed
        SurfaceHolder holder = getHolder();
        holder.addCallback(new Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                initInput();
                //we make sure currentState is null before creating a new LoadState
                if (currentState == null) {
                    setCurrentState(new LoadState());
                }
                initGame();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                // TODO Auto-generated method stub
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                pauseGame();
            }

        });
    }

    // it first checks if inputHandler is null and creates one if necessary
    // then sets the inputHandler as the OnTouchListener of the GameView
    private void initInput() {
        if (inputHandler == null) {
            inputHandler = new InputHandler();
        }
        setOnTouchListener(inputHandler);
    }

    public void setCurrentState(State newState) {
        System.gc();        // calls to clean up any unused objects that are taking up valuable space in memory
        newState.init();    // calls the init() method of the current State
        currentState = newState;
        inputHandler.setCurrentState(currentState);
    }

    private void initGame() {
        running = true;
        gameThread = new Thread(this, "Game Thread");
        gameThread.start();
    }

    // allows to stop executing when the application is about to pause
    private void pauseGame() {
        running = false;
        while (gameThread.isAlive()) {
            try {
                gameThread.join();
                break;
            } catch (InterruptedException e) {
            }
        }
    }

    private void updateAndRender(long delta) {
        currentState.update(delta / 1000f);  //delta:1000f allows to have the value given by the update in seconds
        currentState.render(graphics);       //will return the Graphics object of the JPanel
        renderGameImage();
    }

    private void renderGameImage() {
        Canvas screen = getHolder().lockCanvas(); //it locks the Canvas for drawing to have only one Thread to draw at a time
        if (screen != null) {
            screen.getClipBounds(gameImageDst);   //this informs the Rect object how big the screen is
            screen.drawBitmap(gameImage, gameImageSrc, gameImageDst, null);
            getHolder().unlockCanvasAndPost(screen); //it unlocks the Canvas and end the drawing
        }
    }

    @Override
    public void run(){
        // These variables should sum up to 17 on every iteration :
        long updateDurationMillis = 0; // Measures both update AND render
        long sleepDurationMillis = 0;  // Measures sleep

        while (running) {
            long beforeUpdateRender = System.nanoTime(); // fix a point in time => So before calling update&render methods, we check the time and store it inside beforeUpdateRender.
            long deltaMillis = updateDurationMillis + sleepDurationMillis; // this should be 17 ms in most cases but if updateDurationMillis were to take an abnormally long time, this number can be higher

            updateAndRender(deltaMillis);

            updateDurationMillis = (System.nanoTime() - beforeUpdateRender) / 1000000L; // Once the update&render methods finish executing, we calculate the duration of the two steps by checking the time once more (System.nanoTime()) and subtracting the original time (beforeUpdateRender). This result in nanoseconds is divided by a million to convert it into the same result milliseconds (a nanosecond is a millionth of a millisecond)
            sleepDurationMillis = Math.max(2, 17 - updateDurationMillis); //take the max number between 2 and the 17-time of duration for the update and render

            try {
                Thread.sleep(sleepDurationMillis); //it sleeps only the time of 17 minus time for update&render
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    //exiting the game
    public void exit() {
        System.exit(0);
    }
}
