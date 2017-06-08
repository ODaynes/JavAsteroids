package JavAsteroids.game1;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by ODaynes on 20/01/2017.
 */
class View extends JComponent {

    private Game game;                          // used to get information from current game state
    private Image bg = Constants.MILKYWAY1;     // set background
    private AffineTransform bgTransf;           // set background trasform

    // constructor

    View(Game game) {
        this.game = game;

        double imWidth = bg.getWidth(null);
        double imHeight = bg.getHeight(null);
        double stretchx = (imWidth > Constants.FRAME_WIDTH ? 1 : Constants.FRAME_WIDTH/imWidth);
        double stretchy = (imHeight > Constants.FRAME_HEIGHT ? 1 : Constants.FRAME_HEIGHT/imHeight);

        bgTransf = new AffineTransform();
        bgTransf.scale(stretchx, stretchy);
    }

    @Override
    public void paintComponent(Graphics g0) {

        // paint background
        Graphics2D g = (Graphics2D) g0;
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(bg, bgTransf, null);

        // paint all active game objects
        synchronized (Game.class) {
            for (GameObject o : game.getGameObjs()) {
                if(o instanceof PlayerShip) {
                    if(((PlayerShip)o).isShieldActive()) {
                        g.setColor(Color.CYAN);
                        g.fillOval((int)(o.position.x-1.5*o.radius), (int)(o.position.y-1.5*o.radius), (int)(3*o.radius), (int)(3*o.radius));
                    }
                }
                o.draw(g);
            }
        }

        int width = getWidth();
        int height = getHeight();
        int lives = game.getLives();
        int score = game.getScore();
        int highScore = game.getHighScore();

        // draw hud / info bar
        g.setColor(Color.PINK);
        g.setFont(new Font("TimesRoman", Font.BOLD, 18));
        g.drawString("Lives: " + String.valueOf(lives), 10, 20);
        g.drawString("Score: " + String.valueOf(score), width / 6, 20);
        g.drawString("High Score: " + String.valueOf(highScore), width / 6 * 2, 20);
        g.drawString("Level: " + String.valueOf(Game.getLevel()), width / 6 * 3, 20);
        g.drawString("Shield: " + String.valueOf(game.getPlayerShipShieldDuration()), width / 6 * 4, 20);
        g.drawString("Enemies: " + String.valueOf(Game.getActiveEnemies()), width / 6 * 5, 20);

        // draw game over if lives less than one
        if(lives <= 0) {
            g.drawImage(Constants.GAMEOVER, width /2 - Constants.GAMEOVER.getWidth(null) /2, height /2 - Constants.GAMEOVER.getHeight(null) /2, null);
            g.setColor(Color.RED);
            if(score >= highScore) {
                game.setHighScore(score);
                g.drawString("NEW HIGH SCORE: " + game.getHighScore(), width / 2 - 90, height / 2 + 75);
            }
            g.drawString("Press 'R' to restart", width / 2 - 75, height / 2 + 100);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return Constants.FRAME_SIZE;
    }
}
