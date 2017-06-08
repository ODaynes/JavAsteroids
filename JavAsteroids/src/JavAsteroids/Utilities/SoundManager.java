package JavAsteroids.Utilities;

// change package name to fit your own package structure!

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import java.io.File;

// SoundManager for Asteroids

public class SoundManager {

	static int nBullet = 0;
	static boolean thrusting = false;

	// this may need modifying
	final static String path = "sounds/";

	// note: having too many clips open may cause
	// "LineUnavailableException: No Free Voices"
	public final static Clip[] bullets = new Clip[15];

	// pre-load all of the sounds

	public static Clip bangLarge = SoundManager.getClip("bangLarge");
	public static Clip bangMedium = SoundManager.getClip("bangMedium");
	public static Clip bangSmall = SoundManager.getClip("bangSmall");
	public static Clip beat1 = SoundManager.getClip("beat1");
	public static Clip beat2 = SoundManager.getClip("beat2");
	public static Clip extraShip = SoundManager.getClip("extraShip");
	public static Clip fire = SoundManager.getClip("fire");
	public static Clip saucerBig = SoundManager.getClip("saucerBig");
	public static Clip saucerSmall = SoundManager.getClip("saucerSmall");
	public static Clip thrust = SoundManager.getClip("thrust");

 	static {
		for (int i = 0; i < bullets.length; i++) bullets[i] = getClip("fire");
	}

	// methods which do not modify any fields

	public static void play(Clip clip) {
		clip.setFramePosition(0);
		clip.start();
	}

	public static Clip getClip(String filename) {
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
			AudioInputStream sample = AudioSystem.getAudioInputStream(new File(path
					+ filename + ".wav"));
			clip.open(sample);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clip;
	}

	// methods which modify (static) fields

	public static void fire() {
		// fire the n-th bullet and increment the index
		Clip clip = bullets[nBullet];
		clip.setFramePosition(0);
		clip.start();
		nBullet = (nBullet + 1) % bullets.length;
	}

	public static void startThrust() {
		if (!thrusting) {
			thrust.loop(-1);
			thrusting = true;
		}
	}

	public static void stopThrust() {
		thrust.loop(0);
		thrust.stop();
		thrusting = false;
	}

	public static void asteroids() {
		play(bangMedium);
	}
	public static void extraShip() {
		play(extraShip);
	}

}
