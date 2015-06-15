package be.ecam.shooterGame.game.state;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;

import java.util.ArrayList;

import be.ecam.shooterGame.framework.util.Painter;
import be.ecam.shooterGame.framework.util.RandomNumberGenerator;
import be.ecam.shooterGame.game.main.Assets;
import be.ecam.shooterGame.game.main.GameMainActivity;
import be.ecam.shooterGame.game.model.Bullet;
import be.ecam.shooterGame.game.model.Enemy;
import be.ecam.shooterGame.game.model.Meteor;
import be.ecam.shooterGame.game.model.Ship;

/**
 * Game play screen
 * Displays the game shooter
 *
 * Created by Ikram-David-Zoubida on 14/05/15
 * Reference : http://jamescho7.com/book
 */
public class PlayState extends State {

    //private UIButton pauseButton;
    private Meteor meteor, meteor2, meteor3;
    private Ship playerShip;
    private Enemy enemyShip;
    private Bullet bullet;
    private ArrayList<Bullet> bulletsList = new ArrayList<Bullet>();
    private ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
    private static final int SHIP_WIDTH = Assets.player.getWidth();
    private static final int SHIP_HEIGHT = Assets.player.getHeight();
    private static final int BULLET_DIAMETER = Assets.laserShoot.getHeight();
    private static final int SCORE_WIDTH = 200;
    //private Font scoreFont;
    private int playerScore = 0;
    private int enemyNumber;
    private int level=1;
    private int upperRand;
    private int lowerRand;
    private int count = 0;

    @Override
    public void init() {
        //pauseButton = new UIButton(10, 20, 10+(Assets.pausebutton.getWidth()), 20 + (Assets.pausebutton.getHeight()), Assets.pausebutton, Assets.pauseDownbutton);

        playerShip = new Ship(0, GameMainActivity.GAME_HEIGHT/2, SHIP_WIDTH, SHIP_HEIGHT);

        //Check if the enemyList is empty and go to list generator
        meteor = new Meteor(100,100, Assets.meteor.getWidth(), Assets.meteor.getHeight());
        meteor2 = new Meteor (500,50, Assets.meteor.getWidth(), Assets.meteor.getHeight());
        meteor3 = new Meteor (700,350, Assets.meteor.getWidth(), Assets.meteor.getHeight());
        if (enemyList.isEmpty()==true){
            enemyGenerator();
        }

    }

    @Override
    public void update(float delta) {
        playerShip.update(delta);

        //System.out.println(enemyList.size());//Test for the size of the list
        updateEnemies(delta);
        updateBullets(delta);
        updateMeteors(delta);

        if (enemyList.size()<=6){
            System.out.println("NEW LIST WITH MORE ENEMIES");
            enemyGenerator();
        }

        if (!playerShip.isAlive()) {
            System.out.println("GAME OVER");
            setCurrentState(new GameOverState(playerScore));
        }

    }

    //update the bullets and for each bullet it detects if there is a collision with the enemies
    private void updateBullets(float delta) {
        if(bulletsList.isEmpty()==false) {
            for (int i = 0; i<bulletsList.size(); i++){
                Bullet B = (Bullet) bulletsList.get(i);
                if (B.isVisible() == true) {
                    B.update(delta);
                    for (int j = 0; j<enemyList.size(); j++){
                        Enemy E = (Enemy) enemyList.get(j);
                        if (bulletCollides(E, B)) {
                            playerScore++;
                            B.onCollideWith(E);
                            E.onCollideWith();
                            Assets.playSound(Assets.Explosion);
                        }
                    }
                }
            }
        }
    }

    //Update the enemies and if an enemy collide with the player => gameOver!
    private void updateEnemies(float delta) {
        for (int j = 0; j<enemyList.size(); j++){
            Enemy E = (Enemy) enemyList.get(j);
            if (E.isVisible() == true) {
                E.update(delta);
                if (playerShipCollides(E)){
                    playerShip.onCollideWith(E);
                    System.out.println("Player KILLED by the enemy Ship");
                    Assets.playSound(Assets.Explosion);
                }
            }
        }
    }

    //updates meteors
    private void updateMeteors(float delta) {
        meteor.update(delta);
        meteor2.update(delta);
        meteor3.update(delta);
        if(meteorCollides(meteor)){
            playerShip.onCollideWith(meteor);
            System.out.println("Player KILLED by the 1st Meteor");
            Assets.playSound(Assets.MeteorExplosion);
        } else if(meteorCollides(meteor2)){
            playerShip.onCollideWith(meteor);
            System.out.println("Player KILLED by the 2nd Meteor");
            Assets.playSound(Assets.MeteorExplosion);
        } else if(meteorCollides(meteor3)){
            playerShip.onCollideWith(meteor);
            System.out.println("Player KILLED by the 3nd Meteor");
            Assets.playSound(Assets.MeteorExplosion);
        }
    }

    private void enemyGenerator(){
        level+=1;
        lowerRand=1+level*10;
        upperRand=3+level*10;
        enemyNumber = RandomNumberGenerator.getRandIntBetween(lowerRand, upperRand);
        for (int i=0; i<enemyNumber; i++){
            enemyShip = new Enemy(SHIP_WIDTH, SHIP_HEIGHT);
            enemyList.add(enemyShip);
        }
    }

    @Override
    public void render(Painter g) {
        g.drawImage(Assets.background, 0, 0, Assets.background.getWidth(), Assets.background.getHeight());
        //pauseButton.render(g);

        //Draw Meteors
        renderMeteor(g);
        // Draw Player Ship
        renderPlayerShip(g);
        // Draw bullet
        renderbullet(g);
        //Draw Enemy
        renderEnemy(g);
        // Draw UI
        renderScore(g);
    }

    public void renderPlayerShip(Painter g){
        g.drawImage(Assets.player, (int)playerShip.getX(), (int)playerShip.getY(), Assets.player.getWidth(), Assets.player.getHeight());
    }

    public void renderbullet(Painter g){
        for (int i = 0; i<bulletsList.size(); i++){
            Bullet b = (Bullet) bulletsList.get(i);
            if (b.isVisible() == true) {
                g.drawImage(Assets.laserShoot, b.getX(), b.getY(), Assets.laserShoot.getWidth(),Assets.laserShoot.getHeight());
            } else {
                g.drawImage(Assets.explosion,b.getX(), b.getY(), Assets.explosion.getWidth(), Assets.explosion.getHeight());
                bulletsList.remove(i);
            }
        }
    }


    private void renderMeteor(Painter g){
        g.drawImage(Assets.meteor, (int)meteor.getX(), (int)meteor.getY(), 100, 100);
        g.drawImage(Assets.meteor, (int)meteor2.getX(), (int)meteor2.getY(), 100,100);
        g.drawImage(Assets.meteor, (int)meteor3.getX(), (int)meteor3.getY(), 100,100);
    }

    public void renderEnemy(Painter g){
        for (int i = 0; i<enemyList.size(); i++){
            Enemy e = (Enemy) enemyList.get(i);
            if (e.isVisible() == true) {
                g.drawImage(Assets.enemy, e.getX(), e.getY(), Assets.enemy.getWidth(), Assets.enemy.getHeight());
            } else {
                enemyList.remove(i);
            }
        }
    }

    public void renderScore(Painter g){
        g.setColor(Color.WHITE);
        g.setFont(Typeface.DEFAULT_BOLD, 30); // Sets scoreFont as current font
        //String playerScoreStr = String.valueOf(playerScore); //convert playerScore into a string
        g.drawString("Score : ", GameMainActivity.GAME_WIDTH - SCORE_WIDTH , 30);
        g.drawString("" + playerScore,GameMainActivity.GAME_WIDTH - SCORE_WIDTH + 100, 30); // Draws String using current font
    }

    public void shoot() {
        bullet = new Bullet((int)playerShip.getX() + SHIP_WIDTH /2, (int)playerShip.getY() + SHIP_HEIGHT /2, BULLET_DIAMETER, BULLET_DIAMETER);
        bulletsList.add(bullet);
        Assets.playSound(Assets.Laser);
    }

    private boolean bulletCollides(Enemy enemy, Bullet bullet) {
        return Rect.intersects(enemy.getRect(), bullet.getRect());
    }

    private boolean playerShipCollides(Enemy enemy) {
        return Rect.intersects(enemy.getRect(), playerShip.getRect());
    }

    private boolean meteorCollides(Meteor meteor){
        return Rect.intersects(meteor.getRect(), playerShip.getRect());
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (count == 10) {
            shoot();
            count = 0;
        } else {
            count++;
        }

        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            //pauseButton.onTouchDown(scaledX, scaledY);
        } else if (e.getAction() == MotionEvent.ACTION_UP) {
            playerShip.stop();
        }

        if (e.getAction() == MotionEvent.ACTION_MOVE) {

            playerShip.setX((float)scaledX);
            playerShip.setY((float)scaledY);
        }

        return true;
    }
}
