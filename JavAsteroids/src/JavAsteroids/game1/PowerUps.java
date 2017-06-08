package JavAsteroids.game1;

import JavAsteroids.Utilities.Vector2D;

/**
 * Created by ODaynes on 01/03/2017.
 */
public class PowerUps extends GameObject {

    private int type; // type of power up

    PowerUps(double x, double y, double vx, double vy, int rad, int type) {
        super(new Vector2D(x, y),       // position
                new Vector2D(vx, vy),   // velocity
                rad);                   // radius

        this.type = type;               // set type

        // set image
        if(type == 0){ // HEALTH
            setImage(Constants.HEART1);
        } else if (type == 1) { // SHIELD
            setImage(Constants.SHIELD1);
        } else if(type == 2) { // BULLETS
            setImage(Constants.AMMO1);
        }
    }

    // update power up
    public void update() {
        super.update();
    }

    // apply effect of power up and kills object
    void hit(PlayerShip other) {
        if(this.type == 0) {
            other.addLife();
        } else if(this.type == 1) {
            other.increaseShieldDuration(20);
        } else if(this.type == 2){
            other.increaseBulletClip(1);
        } else {
            Game.incScore(10);
        }

        this.dead = true;
    }


}
