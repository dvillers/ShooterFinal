package be.ecam.shooterGame.game.main;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;
import java.io.InputStream;

/**
 * Class that holds all the resources (images & sound files)
 * that will be used in the game
 *
 * Created by Ikram-David-Zoubida on 13/05/15
 * Reference : http://jamescho7.com/book
 */
public class Assets {

    private static SoundPool soundPool;
    public static Bitmap welcome, title, player, enemy, explosion, laserShoot, meteor ;
    public static Bitmap scorebutton, scoreDownbutton, startbutton, startDownbutton, exitbutton, exitDownbutton ;
    public static Bitmap pausebutton, pauseDownbutton, background, menu, gameOver, scoreBack;
    public static int Explosion, Laser, MeteorExplosion, GameOver;

    // loads all the resources in the game thanks to loadBitmap() and loadSound()
    public static void load() {
        welcome = loadBitmap("welcome.png", false);
        title = loadBitmap("shooter.png", false);
        player = loadBitmap("playerShip1_blue.png", false);
        enemy = loadBitmap("enemyRed1.png", false);
        explosion = loadBitmap("explosion.png", false);
        laserShoot = loadBitmap ("laserBlue01.png", false);
        meteor = loadBitmap("meteor.png", false);
        scorebutton = loadBitmap("score.png", false);
        scoreDownbutton = loadBitmap("score-down.png", false);
        startbutton = loadBitmap("start.png", false);
        startDownbutton = loadBitmap("start-down.png", false);
        exitbutton = loadBitmap("exit.png", false);
        exitDownbutton = loadBitmap("exit-down.png", false);
        pausebutton = loadBitmap("pause.png",false);
        pauseDownbutton = loadBitmap("pause-down.png",false);
        background = loadBitmap("background.png", false);
        menu = loadBitmap("menu.png", false);
        gameOver = loadBitmap("GameOver.png", false);
        scoreBack = loadBitmap("scoreBack.png", false);


        Explosion = loadSound("Explosion.wav");
        Laser = loadSound("Laser_Shoot.wav");
        MeteorExplosion = loadSound("MeteorExplosion.wav");
        GameOver = loadSound("game-over.wav");
    }

    //search the resources packages for the requested image file
    //and returned it as an image (bitmap)
    private static Bitmap loadBitmap(String filename, boolean transparency) {
        InputStream inputStream = null;
        try {
            inputStream = GameMainActivity.assets.open(filename); //opens an image file from the assets folder
        }catch (IOException e) {
            System.out.println("Error while reading:" + filename);
            System.out.println("\n The "+ filename +"'s file doesn't exists");
            e.printStackTrace();
        }
        Options options = new Options(); // Options object specifies how that image should be stored in memory
        if (transparency) {
            options.inPreferredConfig = Config.ARGB_8888; //transparent images, greater memory consumption
        } else {
            options.inPreferredConfig = Config.RGB_565;  //no transparency, less memory consumption
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, new Options());
        return bitmap;
    }

    //search the resources packages for the requested sound file
    //the sound file would be loaded into the RAM thanks to the SoundPool
    private static int loadSound(String filename) {
        int soundID = 0;
        if (soundPool == null) {
            soundPool = new SoundPool(25, AudioManager.STREAM_MUSIC, 0);
        }
        try {
            soundID = soundPool.load(GameMainActivity.assets.openFd(filename), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return soundID;
    }

    public static void playSound(int soundID) {
        soundPool.play(soundID, 1, 1, 1, 0, 1);
    }
}
