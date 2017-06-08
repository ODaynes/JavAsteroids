package JavAsteroids.Utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ODaynes on 26/02/2017.
 */
public class ImageManager {
    private final static String path = "images/";

    private final static String ext = ".png";

    private static Map<String, Image> images = new HashMap<>();

    public static Image getImage(String s) {
        return images.get(s);
    }

    public static Image loadImage(String fname) throws IOException {
        BufferedImage img = ImageIO.read(new File(path + fname + ext));
        images.put(fname, img);
        return img;
    }

    public static void loadImages(String[] fNames) throws IOException {
        for(String s: fNames) {
            loadImage(s);
        }
    }

    public static void loadImages(Iterable<String> fNames) throws IOException {
        for(String s: fNames) {
            loadImage(s);
        }
    }
}
