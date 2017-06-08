package JavAsteroids.game1;

import JavAsteroids.Utilities.SoundManager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by ODaynes on 27/01/2017.
 */
class KeyController extends KeyAdapter implements BasicController {
    private Action action;

    KeyController() {
        action = new Action();
    }

    public Action action() {
        return action;
    }

    // handle key presses
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode() == KeyEvent.VK_R){
            Game.restart();
        }

        // if player is dead, only the reset action should work

        if(action.shipDead) {
            return;
        }

        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                action.thrust = 1;
                SoundManager.startThrust();
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                action.turn = -1;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                action.turn = +1;
                break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_ENTER:
                action.shoot = true;
                break;
            case KeyEvent.VK_S:
                action.shieldActive = !action.shieldActive;
                break;

            // CHEATS - DEBUG

            case KeyEvent.VK_1:
                Game.increaseLevel();
                break;
            case KeyEvent.VK_2:
                Game.increaseLives();
                break;
            case KeyEvent.VK_3:
                Game.incScore(10);
                break;
            case KeyEvent.VK_4:
                Game.generateRandomEnemyShip();
                break;
            case KeyEvent.VK_5:
                Game.generateAsteroid();
                break;
            }
        }

    // handle released keys

    public void keyReleased(KeyEvent e) {

        if(action.shipDead) {
            SoundManager.stopThrust();
            return;
        }

        int key = e.getKeyCode();

        switch(key) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                action.thrust = 0;
                SoundManager.stopThrust();
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                action.turn = 0;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                action.turn = 0;
                break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_ENTER:
                action.shoot = false;
                break;
        }

    }
}
