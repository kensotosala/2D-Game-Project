package utilz;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

public class LoadSave {

	public final String PLAYER_ATLAS = "resources/player_sprites_test.png";
	public final String LEVEL_ATLAS = "resources/OutsideSpritesSonic2.png";
	public final String MENU_BUTTONS = "resources/button_atlas.png";
	public final String MENU_BACKGROUND = "resources/menu_background.png";
	public final String PAUSE_BACKGROUND = "resources/pause_menu.png";
	public final String SOUND_BUTTONS = "resources/sound_button.png";
	public final String URM_BUTTONS = "resources/urm_buttons.png";
	public final String VOLUME_BUTTONS = "resources/volume_buttons.png";
	public final String MENU_BACKGROUND_IMG = "resources/background_menu.png";
	public final String CRABMEAT_SPRITE = "resources/crabmeat-sprite.png";
	public final String STATUS_BAR = "resources/health_power_bar.png";
	public final String COMPLETED_IMG = "resources/completed_sprite.png";

	public BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		try {
			InputStream is = LoadSave.class.getClassLoader().getResourceAsStream(fileName);
			if (is != null) {
				img = ImageIO.read(is);
				is.close();
			} else {
				throw new IllegalArgumentException("Resource not found: " + fileName);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	public BufferedImage[] GetAllLevels() {
		URL url = LoadSave.class.getResource("/resources/lvls");
		File file = null;

		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		File[] files = file.listFiles();
		File[] filesSorted = new File[files.length];

		for (int i = 0; i < filesSorted.length; i++)
			for (int j = 0; j < filesSorted.length; j++) {
				if (files[j].getName().equals((i + 1) + ".png"))
					filesSorted[j] = files[i];
			}

		BufferedImage[] imgs = new BufferedImage[filesSorted.length];
		for (int i = 0; i < imgs.length; i++)
			try {
				imgs[i] = ImageIO.read(filesSorted[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}

		return imgs;

	}

}
