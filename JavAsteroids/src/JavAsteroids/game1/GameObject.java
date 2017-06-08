package JavAsteroids.game1;

import JavAsteroids.Utilities.SoundManager;
import JavAsteroids.Utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static JavAsteroids.game1.Constants.DT;
import static JavAsteroids.game1.Constants.FRAME_HEIGHT;
import static JavAsteroids.game1.Constants.FRAME_WIDTH;

/**
 * Created by ODaynes on 03/02/2017.
 */
abstract class GameObject {

    Vector2D position, velocity;    // track position and velocity
    boolean dead;                   // is gameobject dead?
    double radius;                  // radius
    private int lives;              // hits
    Image im;                       // image to be drawn for object

    // constructor
    GameObject(Vector2D position, Vector2D velocity, double radius) {
        this.position = position;
        this.velocity = velocity;
        this.radius = radius;
        this.dead = false;
        this.lives = 1;
    }

    // constructor
    GameObject(Vector2D position, Vector2D velocity, double radius, int lives) {
        this.position = position;
        this.velocity = velocity;
        this.radius = radius;
        this.dead = false;
        this.lives = lives;
    }

    // object has been hit, decrement lives, kill object if lives fall below one
    private void hit() {
        lives--;
        if(this instanceof PlayerShip) ((PlayerShip)this).resetInvulnerabilityTime();

        if(lives < 1) {
            if(this instanceof PlayerShip) {
                ((PlayerShip) this).setCtrlDead();
            }
            dead = true;
        }

    }

    // update position of object
    void update() {
        position.addScaled(velocity, DT);
        position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
    }

    // draw object image to screen
    void draw(Graphics2D g) {
        if(im != null) {
            double imW = im.getWidth(null);
            double imH = im.getHeight(null);
            AffineTransform t = new AffineTransform();
            t.scale(radius * 2 / imW, radius * 2 / imH);
            t.translate(-imW / 2.0, -imH / 2.0);
            AffineTransform t0 = g.getTransform();
            g.translate(position.x, position.y);
            g.drawImage(im, t, null);
            g.setTransform(t0);
        }
    }

    // used for early debugging
    @Override
    public String toString() {
        return "GameObject{" +
                "position=" + position +
                ", velocity=" + velocity +
                ", dead=" + dead +
                ", radius=" + radius +
                '}';
    }

    // returns if this is overlapping with other
    boolean overlap(GameObject other) {
        Double dist1 = position.dist(other.position);
        double combinedRadius = radius + other.radius;
        return dist1 <= combinedRadius;
    }

    // handles collisions between this and other
    void collisionHandling(GameObject other) {
        if((this.getClass() != other.getClass()) && this.overlap(other)) {

            // playership and powerups
            if(this.getClass() == PlayerShip.class && other.getClass() == PowerUps.class) { // ship and powerup
                ((PowerUps)other).hit((PlayerShip)this);
                SoundManager.extraShip();
            } else if(other.getClass() == PlayerShip.class && this.getClass() == PowerUps.class) { // powerup and ship
                ((PowerUps)this).hit((PlayerShip)other);
                SoundManager.extraShip();
            }

            // enemyship and child bullets
            else if(this.getClass() == EnemyShip.class && other.getClass() == Bullet.class) {
                if(((Bullet)other).getParentType() != 1) {
                    this.hit();
                    other.hit();
                    SoundManager.asteroids();
                }
            }
            else if(other.getClass() == EnemyShip.class && this.getClass() == Bullet.class) {
                if(((Bullet)this).getParentType() != 1) {
                    this.hit();
                    other.hit();
                    SoundManager.asteroids();
                }
            }
            else {
                this.hit();
                other.hit();
                SoundManager.asteroids();
            }
        }
    }

    // sets gameobject image
    void setImage(Image image) {
        this.im = image;
    }

    // gets lives
    int getLives() {
        return lives;
    }

    // increments lives
    void addLife() {
        lives++;
    }

    // increments lives by n
    void addLives(int n) {
        lives += n;
    }

}
