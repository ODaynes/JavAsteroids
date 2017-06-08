package JavAsteroids.game1;

/**
 * Created by ODaynes on 04/03/2017.
 */
public class RotateNShoot implements BasicController {

    Action action = new Action();

    public Action action() {
        // shoot bullet, rotate right, move
        action.shoot = true;
        action.turn = 1;
        action.thrust = 1;
        return action;
    }
}
