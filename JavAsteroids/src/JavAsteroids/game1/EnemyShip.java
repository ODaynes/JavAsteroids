package JavAsteroids.game1;

import JavAsteroids.Utilities.SoundManager;
import JavAsteroids.Utilities.Vector2D;
import static JavAsteroids.game1.Constants.*;


/**
 * Created by ODaynes on 04/03/2017.
 */
class EnemyShip extends Ship {

    // bullet time counted with bulletTimer
    private int bulletTimer = 0;
    // bullet fires every bulletLimit updates
    private int bulletLimit = 30;

    // Constructor

    EnemyShip(BasicController ctrl, int startX, int startY, int travelX, int travelY) {

        super(new Vector2D(startX, startY),         // positioned in x and y pos
                new Vector2D(travelX, travelY),     // travels in x and y direction
                RADIUS,                             // radius
                ENEMY_INITIAL_LIVES                 // one hit
        );

        this.ctrl = ctrl;                           // set control
        this.ctrl.action().shieldActive = false;    // set shield inactive
        this.ctrl.action().shipDead = false;        // set ship alive

        direction = new Vector2D(0.0, 1.0);         // facing down

        setImage(Constants.ENEMY_SHIP1);            // set image to enemy ship image
    }

    void update() {

        // update direction
        if (ctrl.action().turn != 0) {
            direction.rotate(ctrl.action().turn * DT * STEER_RATE);
        }

        // update position
        position.addScaled(velocity, DT).wrap(FRAME_WIDTH, FRAME_HEIGHT);

        // fire bullets every 30 updates
        if (ctrl.action().shoot) {
            bulletTimer = bulletTimer + 1;
            if (bulletTimer >= bulletLimit) {
                makeBullet();
                bulletTimer = 0;
            }
        }
    }

    // create a bullet, play bullet firing sound

    void makeBullet() {
        bullet = new Bullet(new Vector2D(position).addScaled(direction, 20),
                new Vector2D(velocity).addScaled(direction, 175),
                7, 1
        );
        SoundManager.fire();
    }
}

