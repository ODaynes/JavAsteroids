package JavAsteroids.game1;

import JavAsteroids.Utilities.ImageManager;

import java.awt.*;
import java.io.IOException;
import java.util.Random;

/**
 * Created by ODaynes on 20/01/2017.
 */
class Constants {

    // Define player window
    static final int FRAME_HEIGHT = 576;
    static final int FRAME_WIDTH = 1024;
    static final Dimension FRAME_SIZE = new Dimension(
     Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);

    // Define update variables
    static final int DELAY = 20;
    static final double DT = DELAY / 1000.0;

    // Define initial ship lives
    static final int PLAYER_INITIAL_LIVES = 3;
    static final int ENEMY_INITIAL_LIVES = 1;

    // load images and store as constants to use later
    static Image ROCKET1, ENEMY_SHIP1, ASTEROID1, MILKYWAY1, MILKYWAY2, BULLET1, HEART1, SHIELD1, AMMO1, GAMEOVER, TRANSPARENT;
    static {
        try {
            ROCKET1         = ImageManager.loadImage("../JavAsteroids/images/rocket1");
            ENEMY_SHIP1     = ImageManager.loadImage("../JavAsteroids/images/enemy_rocket1");
            ASTEROID1       = ImageManager.loadImage("../JavAsteroids/images/asteroid1");
            MILKYWAY1       = ImageManager.loadImage("../JavAsteroids/images/milkyway1");
            MILKYWAY2       = ImageManager.loadImage("../JavAsteroids/images/milkyway2");
            BULLET1         = ImageManager.loadImage("../JavAsteroids/images/bullet3");
            HEART1          = ImageManager.loadImage("../JavAsteroids/images/heart2");
            SHIELD1         = ImageManager.loadImage("../JavAsteroids/images/shield2");
            AMMO1           = ImageManager.loadImage("../JavAsteroids/images/ammo1");
            GAMEOVER        = ImageManager.loadImage("../JavAsteroids/images/game_over");
            TRANSPARENT     = ImageManager.loadImage("../JavAsteroids/images/transparent");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Random rand = new Random();
}
