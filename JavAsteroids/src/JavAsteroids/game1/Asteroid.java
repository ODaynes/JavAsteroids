package JavAsteroids.game1;

import JavAsteroids.Utilities.Vector2D;

import static JavAsteroids.game1.Constants.*;

/**
 * Created by ODaynes on 20/01/2017.
 */

class Asteroid extends GameObject {

    private static final double MAX_SPEED = 100;

    private GameObject killedBy = null;

    private Asteroid(double x, double y, double vx, double vy, int rad) {
        super(new Vector2D(x, y),       // position
                new Vector2D(vx, vy),   // velocity
                rad);                // RADIUS
        setImage(Constants.ASTEROID1);
    }

    // update asteroid position

    public void update() {
        position.x += velocity.x * DT;
        position.y += velocity.y * DT;
        position.x = (position.x + FRAME_WIDTH) % FRAME_WIDTH;
        position.y = (position.y + FRAME_HEIGHT) % FRAME_HEIGHT;
    }

    // make a random large asteroid

    static Asteroid makeRandomBigAsteroid() {
        double x = (FRAME_WIDTH - 20) * Math.random();
        double y = (FRAME_HEIGHT * 20) * Math.random();

        double vx = Math.random() * MAX_SPEED;
        double vy = Math.random() * MAX_SPEED;

        return new Asteroid(x, y, vx, vy, 20);
    }

    // make a random medium sized asteroid

    static Asteroid makeRandomMediumAsteroid(Asteroid parent) {

        int randV1 = (int)(Math.random() * ((30 - 20) + 1) + 20);
        int randV2 = (int)(Math.random() * ((30 - 20) + 1) + 20);
        double ang = Math.random() * 360;
        Vector2D pos = new Vector2D(parent.position).add(randV1, randV2);
        Vector2D vel = new Vector2D(parent.velocity).rotate(ang);

        return new Asteroid(pos.x, pos.y, vel.x, vel.y, 15);
    }

    // make a random small asteroid

    static Asteroid makeRandomSmallAsteroid(Asteroid parent) {

        int randV1 = (int)(Math.random() * ((30 - 20) + 1) + 20);
        int randV2 = (int)(Math.random() * ((30 - 20) + 1) + 20);
        double ang = Math.random() * 360;
        Vector2D pos = new Vector2D(parent.position).add(randV1, randV2);
        Vector2D vel = new Vector2D(parent.velocity).rotate(ang);

        return new Asteroid(pos.x, pos.y, vel.x, vel.y, 10);
    }

    // keep track of who destroyed the asteroid - used to determine if points should be rewarded
    void setKilledBy(GameObject killer) {
        killedBy = killer;
    }

    // retrieve asteroid killer
    GameObject getKilledBy() {
        return killedBy;
    }
}
