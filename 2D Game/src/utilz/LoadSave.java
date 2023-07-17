package utilz;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LoadSave {
    private static final String LEVEL_1_SPRITE_PATH = "src/resources/Levels/Level-1-Sprite.png";

    public static BufferedImage getLevel1SpriteImage() {
        return loadImage(LEVEL_1_SPRITE_PATH);
    }

    private static BufferedImage loadImage(String imagePath) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
