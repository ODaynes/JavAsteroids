package JavAsteroids.game1;

/**
 * Created by ODaynes on 27/01/2017.
 */
class Action {
    int thrust;             // 0 = off, 1 = on
    int turn;               // -1 = turn left, 0 = no turn, 1 = turn right

    boolean shoot;          // scheduled to shoot?
    boolean shieldActive;   // shield active?
    boolean shipDead;       // ship dead?
}
