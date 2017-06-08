package JavAsteroids.game1;

import JavAsteroids.Utilities.JEasyFrame;
import JavAsteroids.Utilities.SoundManager;

import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import static JavAsteroids.game1.Constants.*;

/**
 * Created by ODaynes on 20/01/2017.
 */
public class Game {

    private static int n_initial_asteroids = 2; // large asteroids to be spawned
    private static int active_enemies;          // n_initial_asteroids * 7 as large splits into two mediums which each split into two smalls
    private static int level = 1;               // start on level 1
    private static int levelToNextShip = 3;     // enemy ships spawn every three levels

    private List<GameObject> gameObjs;          // a list of every game object to be updated
    private static List<GameObject> alive;      // a list of every alive object at the end of collision checks, contents transferred to gameObjs

    private static BasicController ctrl;        // controls for player ship
    private static PlayerShip player;           // player ship

    private static int score = 0;               // current game score
    private static int highScore = 0;           // game high score


    // gets number of active enemies
    static int getActiveEnemies() {
        return active_enemies;
    }

    // gets current level
    static int getLevel() {
        return level;
    }

    // retrieves duration of player shield
    int getPlayerShipShieldDuration() { return player.getShieldDuration(); }

    // increments the game level
    static void increaseLevel() {
        level += 1;
    }

    // increments number of lives
    static void increaseLives() { player.addLives(1); }

    // generates a single large asteroid
    static void generateAsteroid() {
        addGameObject(Asteroid.makeRandomBigAsteroid());
        active_enemies += 7;
    }

    // generates multiple large asteroids
    private static void generateMoreAsteroids(int n) {
        n_initial_asteroids += n;
        active_enemies = n_initial_asteroids * 7;
        for(int i = 0; i < n_initial_asteroids; i++) {
            addGameObject(Asteroid.makeRandomBigAsteroid());
        }
    }

    // generates a random enemy ship in random position and travelling in random direction
    static void generateRandomEnemyShip() {
        BasicController enemyCtrl = new RotateNShoot();
        int spawnX = rand.nextInt(FRAME_WIDTH);
        int spawnY = rand.nextInt(FRAME_HEIGHT);
        int travelX = rand.nextInt(2);
        if(travelX == 0) travelX = 100;
        else travelX = -100;
        int travelY = rand.nextInt(2);
        if(travelY == 0) travelY = 100;
        else travelY = -100;
        alive.add(new EnemyShip(enemyCtrl, spawnX, spawnY, travelX, travelY));
        active_enemies++;
    }


    // gets the current game score
    int getScore() {
        return score;
    }

    // gets the high score
    int getHighScore() {
        return highScore;
    }

    // sets the high score
    void setHighScore(int score) {
        highScore = score;
    }

    // increase current game score by n points (ten for asteroid, fifty for enemy ship)
    static void incScore(int n) {
        score += n;
    }

    // gets number of player lives
    int getLives() {
        return player.getLives();
    }

    // adds game object to alive list
    private static void addGameObject(GameObject o) {
        alive.add(o);
    }

    // gets list of game objects
    List<GameObject> getGameObjs() {
        return gameObjs;
    }

    // initialise game variables
    private Game() {
        gameObjs = new ArrayList<>();
        active_enemies = n_initial_asteroids * 7;
        for(int i = 0; i < n_initial_asteroids; i++) {
            gameObjs.add(Asteroid.makeRandomBigAsteroid());
        }
        ctrl = new KeyController();
        player = new PlayerShip(ctrl);
        gameObjs.add(player);
    }

    // reset game variables
    static void restart() {
        alive.clear();
        ctrl.action().shipDead = false;
        ctrl.action().thrust = 0;
        ctrl.action().turn = 0;
        player = new PlayerShip(ctrl);
        addGameObject(player);
        n_initial_asteroids = 2;
        active_enemies = n_initial_asteroids * 7;
        for(int i = 0; i < n_initial_asteroids; i++) {
            addGameObject(Asteroid.makeRandomBigAsteroid());
        }
        level = 1;
        levelToNextShip = 3;
        score = 0;
    }

    // run the game
    public static void main(String[] args) throws Exception {
        Game game = new Game();
        View view = new View(game);
        new JEasyFrame(view, "JavAsteroids").addKeyListener((KeyListener) game.ctrl);

        // pre-load sound manager to prevent loading mid-game
        SoundManager.extraShip();
        while (true) {
            game.update();
            view.repaint();
            Thread.sleep(DELAY);
        }
    }

    // update game, deals with game logic

    private void update() {

        synchronized (Game.class) {

            alive = new ArrayList<>();

            // if level is a multiple of three, spawn 1 - 3 ships
            if(getLevel() == levelToNextShip) {
                int enemyShipsToSpawn;

                if(getLevel() == 3) {
                    enemyShipsToSpawn = 1;
                } else {
                    int enemyShipSpawnChance = Constants.rand.nextInt(100);
                    // 15% chance of spawning three ships
                    if (enemyShipSpawnChance < 15) {
                        enemyShipsToSpawn = 3;
                    // 25% chance of spawning two ships
                    } else if (enemyShipSpawnChance >= 15 && enemyShipSpawnChance < 40) {
                        enemyShipsToSpawn = 2;
                    // 60% chance of spawning one ship
                    } else enemyShipsToSpawn = 1;
                }

                // spawn ships
                for(int i = 0; i < enemyShipsToSpawn; i++) {
                    generateRandomEnemyShip();
                }

                levelToNextShip += 3;
            }

            // count down shield duration if shield active
            if(player.getShieldDuration() > 0 && player.isShieldActive()) {
                player.decreaseShieldDuration(1);
            }

            // invulnerability is reset after every hit and next level
            if(player.getInvulnerabilityTime() > 0) {
                player.decreaseInvulnerabilityTime();
            }

            // kill shield if shield duration expired
            if(player.getShieldDuration() < 1) {
                player.setInvincible(false);
            }

            // increase level if all enemies destroyed
            if(getActiveEnemies() < 1) {
                increaseLevel();
                generateMoreAsteroids(2);
                player.resetInvulnerabilityTime();
            }


            // collision handling / game logic

            for(GameObject x: gameObjs)
            {
                for(GameObject y: gameObjs)
                {
                    if(x.equals(y) || x.dead || y.dead) continue; // same object or one is already dead
                    if((x.getClass() == PowerUps.class && (y.getClass() == Asteroid.class)) ||
                            (y.getClass() == PowerUps.class && x.getClass() == Asteroid.class)) continue; // asteroids and power-ups do not collide
                    if((!x.getClass().equals(y.getClass())) ) // if they're different class
                    {
                        if(x.overlap(y)) // if they're overlapping
                        {
                            if((x.getClass().equals(PlayerShip.class) || y.getClass().equals(PlayerShip.class)) && (player.isShieldActive() || player.getInvulnerabilityTime() > 0)) continue; // no collisions with player if shield active

                            if((x.getClass().equals(EnemyShip.class) &&  y.getClass().equals(Asteroid.class)) ||
                                    (y.getClass().equals(EnemyShip.class) &&  x.getClass().equals(Asteroid.class))) {
                                continue; // enemy ship and asteroid won't collide
                            }

                            // handle collisions between objects which can collide
                            x.collisionHandling(y);

                            // keep track of what killed the asteroid
                            if((x.getClass().equals(Asteroid.class) && !y.getClass().equals(Asteroid.class))
                                    || (!x.getClass().equals(Asteroid.class) && y.getClass().equals(Asteroid.class)))
                            {
                                if(x.getClass().equals(Asteroid.class)) {
                                    ((Asteroid)(x)).setKilledBy(y);
                                }

                                if(y.getClass().equals(Asteroid.class)) {
                                    ((Asteroid)(y)).setKilledBy(x);
                                }
                            }

                        }
                    }
                }
            }

            // remove dead objects from game
            for (GameObject o : gameObjs) {
                if (o.dead) {
                    if (o instanceof Bullet) {
                        // decrement active bullet count
                        if(((Bullet)o).getParentType() == 0) player.bulletDied();
                    }
                    if(o instanceof Asteroid) {
                        // decrement enemy count
                        active_enemies--;
                        // increase score if asteroid killed by direct player contact
                        if(((Asteroid)o).getKilledBy().getClass().equals(PlayerShip.class)) incScore(10);
                        // increase score if player bullet kills asteroid
                        if(((Asteroid)o).getKilledBy().getClass().equals(Bullet.class)) {
                            if(((Bullet)(((Asteroid)o).getKilledBy())).getParentType() == 0) { // a horrible number of brackets
                                incScore(10);
                            }
                        }
                        // random chance of dropping a power up
                        int spawnDrop = Constants.rand.nextInt(100);
                        if((spawnDrop >= 0 && spawnDrop < 5) && score >= 400) { // drop health
                            alive.add(new PowerUps(o.position.x, o.position.y, o.velocity.x, o.velocity.y, 10, 0));
                        }
                        if((spawnDrop >= 10 && spawnDrop < 20) && score >= 100) { // drop shield
                            alive.add(new PowerUps(o.position.x, o.position.y, o.velocity.x, o.velocity.y, 10, 1));
                        }
                        if((spawnDrop >= 20 && spawnDrop < 30) && score >= 100) { // drop bullets
                            alive.add(new PowerUps(o.position.x, o.position.y, o.velocity.x, o.velocity.y, 10, 2));
                        }

                        // big asteroids spawn medium asteroids
                        if(o.radius == 20) {
                            alive.add(Asteroid.makeRandomMediumAsteroid((Asteroid)o));
                            alive.add(Asteroid.makeRandomMediumAsteroid((Asteroid)o));
                        }
                        // medium asteroids spawn small asteroids
                        if(o.radius == 15) {
                            alive.add(Asteroid.makeRandomSmallAsteroid((Asteroid)o));
                            alive.add(Asteroid.makeRandomSmallAsteroid((Asteroid)o));
                        }
                    }
                    // decrease number of enemies, increase score
                    if(o instanceof EnemyShip) {
                        active_enemies--;
                        incScore(50);
                    }
                } else {
                    // add bullet from ships into game
                    if(o instanceof Ship) {
                        if(((Ship)o).getBullet() != null) {
                            alive.add(((Ship)o).getBullet());
                            ((Ship)o).voidBullet();
                        }
                    }
                    alive.add(o);
                }
            }

            gameObjs = alive; // replace list of game objects with all objects still alive

            gameObjs.forEach(GameObject::update); // update all game objects
        }
    }
}

