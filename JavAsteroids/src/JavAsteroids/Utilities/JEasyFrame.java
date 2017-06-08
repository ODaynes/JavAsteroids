package JavAsteroids.Utilities;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ODaynes on 20/01/2017.
 */
public class JEasyFrame extends JFrame {
    public Component comp;
    public JEasyFrame(Component comp, String title) {
        super(title);
        this.comp = comp;
        getContentPane().add(BorderLayout.CENTER, comp);
        pack();
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();
    }
}
