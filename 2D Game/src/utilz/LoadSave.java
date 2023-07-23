package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.Game;

public class LoadSave {

	public static final String PLAYER_ATLAS = "resources/Characters-Sprites.png";
	public static final String LEVEL_ATLAS = "resources/OutsideSpritesSonic2.png";
	public static final String LEVEL_ONE_DATA = "resources/level_one_data.png";
	public static final String MENU_BUTTONS = "resources/button_atlas.png";
	public static final String MENU_BACKGROUND = "resources/menu_background.png";

	public static BufferedImage GetSpriteAtlas(String fileName) {
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

	public static int[][] GetLevelData() {
		int[][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
		BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);

		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				if (value >= 48)
					value = 0;
				lvlData[j][i] = value;
			}
		return lvlData;

	}
}