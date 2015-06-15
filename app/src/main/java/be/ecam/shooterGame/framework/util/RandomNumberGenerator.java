package be.ecam.shooterGame.framework.util;

import java.util.Random;

/**
 * This generate a random number between 2 values by using the getRandIntBetween method
 * or less than a number by the getRandInt method
 *
 * Created by Ikram-David-Zoubida on 13/05/15
 * Reference : http://jamescho7.com/book
 */
public class RandomNumberGenerator {
    private static Random rand = new Random();

    public static int getRandIntBetween(int lowerBound, int upperBound) {
        return rand.nextInt(upperBound - lowerBound) + lowerBound;
    }

    public static int getRandInt(int upperBound) {
        return rand.nextInt(upperBound);
    }
}
