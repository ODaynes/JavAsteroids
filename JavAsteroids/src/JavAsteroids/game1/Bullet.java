package JavAsteroids.game1;

import JavAsteroids.Utilities.Vector2D;

/**
 * Created by ODaynes on 03/02/2017.
 */
class Bullet extends GameObject {

    private int life;           // how long a bullet is alive
    private int parent_type;    // who fired the bullet, 0 is player, 1 is enemy ship

    // constructor

    public Bullet(Vector2D position, Vector2D velocity, int radius, int parent) {
        super(new Vector2D(position), new Vector2D(velocity), radius);
        life = 250;
        this.parent_type = parent;
        setImage(Constants.BULLET1);
    }

    // update position of bullet, kill bullet if life expires

    public void update() {
        super.update();
        life -= 1;
        if(life <= 0) {
            killBullet();
        }
    }

    // make bullet dead, ends collisions with this object

    private void killBullet() {
        this.dead = true;
    }

    // returns a code of what created the bullet

    public int getParentType() {
        return parent_type;
    }
}
