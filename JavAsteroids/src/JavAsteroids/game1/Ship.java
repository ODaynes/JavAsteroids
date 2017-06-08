package JavAsteroids.game1;

import JavAsteroids.Utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by ODaynes on 04/03/2017.
 */
abstract class Ship extends GameObject {

    static int RADIUS = 16;                         // radius is 16 for all ships
    static final double STEER_RATE = 2 * Math.PI;   // define steer rate

    Vector2D direction;      // store position
    BasicController ctrl;    // store controls
    Bullet bullet;           // store bullet

    // constructor
    Ship(Vector2D position, Vector2D velocity, double radius, int lives) {
        super(position, velocity, radius, lives);
    }

    @Override
    void draw(Graphics2D g) {
        double imW = this.im.getWidth(null);
        double imH = this.im.getHeight(null);
        AffineTransform t = new AffineTransform();
        t.rotate(direction.angle(), 0, 0);
        t.scale(radius*2 / imW, radius *2 / imH);
        t.translate(-imW / 2.0, -imH / 2.0);
        AffineTransform t0 = g.getTransform();
        g.translate(position.x, position.y);
        g.drawImage(this.im, t, null);
        g.setTransform(t0);
    }

    // update method must be defined for each ship object
    abstract void update();

    // makeBullet method must be defined for each ship object
    abstract void makeBullet();

    // gets bullet
    Bullet getBullet() { return bullet; }

    // makes bullet null
    void voidBullet() {
        bullet = null;
    }

    // kills ship controls
    void setCtrlDead() {
        ctrl.action().shipDead = true;
    }
}
