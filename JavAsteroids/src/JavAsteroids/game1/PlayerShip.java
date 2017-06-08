package JavAsteroids.game1;

import JavAsteroids.Utilities.SoundManager;
import JavAsteroids.Utilities.Vector2D;


import static JavAsteroids.game1.Constants.*;

/**
 * Created by ODaynes on 27/01/2017.
 */
public class PlayerShip extends Ship {

    // physics based constants
    private static final double MAG_ACC = 200;
    private static final double DRAG = 0.01;

    // game based constant
    private static final int MAX_SHIELD_DURATION = 200;

    private int shieldDuration = 100;
    private int invulnerability = 75;
    private int bulletCount = 0;
    private int max_bullet_count = 5;



    // constructor

    public PlayerShip(BasicController ctrl) {
        super(new Vector2D(FRAME_WIDTH/2.0, FRAME_HEIGHT/2.0),  // position
                new Vector2D(0, 0),                             // velocity
                RADIUS,                                         // radius
                PLAYER_INITIAL_LIVES                            // lives
        );

        this.ctrl = ctrl;
        this.ctrl.action().shieldActive = false;
        this.ctrl.action().shipDead = false;

        direction = new Vector2D(0.0, 1.0);
        setImage(Constants.ROCKET1);
    }

    public void update() {

        // update direction
        if(ctrl.action().turn != 0) {
            direction.rotate(ctrl.action().turn * DT * STEER_RATE);
        }

        // update velocity
        velocity.addScaled(direction, MAG_ACC * DT * ctrl.action().thrust).mult(1 - DRAG);

        // update position
        position.addScaled(velocity, DT).wrap(FRAME_WIDTH, FRAME_HEIGHT);

        // attempt to fire bullet if bullets are available
        if(ctrl.action().shoot) {
            if(bulletCount < max_bullet_count) {
                makeBullet();
            }
            ctrl.action().shoot = false;
        }

    }

    // create a bullet and increment bullet count
    // play bullet firing sound

    public void makeBullet() {
        bullet = new Bullet(new Vector2D(position).addScaled(direction,20),
                new Vector2D(velocity).addScaled(direction, 175),
                7, 0);
        bulletCount += 1;
        SoundManager.fire();
    }

    // decrement bullet count
    void bulletDied() {
        bulletCount -= 1;
    }

    // returns whether shield is active or not
    boolean isShieldActive() { return ctrl.action().shieldActive; }

    // sets invincibility to boolean parameter
    void setInvincible(boolean b) {
        ctrl.action().shieldActive = b;
    }

    // gets duration of shield
    int getShieldDuration() { return shieldDuration; }

    // decrease shield duration
    void decreaseShieldDuration(int points) { shieldDuration -= points; }

    // increase shield duration
    void increaseShieldDuration(int points) {
        shieldDuration += points;
        if(shieldDuration > MAX_SHIELD_DURATION) {
            shieldDuration = MAX_SHIELD_DURATION;
        }
    }

    // get invulnerability time
    int getInvulnerabilityTime() {
        return invulnerability;
    }

    // decrease invulnerability time
    void decreaseInvulnerabilityTime() {
        invulnerability--;
    }

    // reset invulnerability time
    void resetInvulnerabilityTime() {
        invulnerability = 50;
    }

    // increase bullet clip size
    void increaseBulletClip(int bullets) {
        max_bullet_count += bullets;
    }

}
